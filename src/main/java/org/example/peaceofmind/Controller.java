package org.example.peaceofmind;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@Component
public class Controller {
    @Autowired
    private ShortStoriesDAO shortStoriesDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private QuotesDAO quotesDAO;
    @Autowired
    private MusicDAO musicDAO;


//    @Scheduled(cron = "*/3 * * * * *")
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public void createUser() {
        String token = RandomStringUtils.randomAlphanumeric(10);
        String username = RandomStringUtils.randomAlphabetic(6);


        User user = new User();

        user.setToken(token);
        user.setUsername(username);
        userDAO.create(user);
        if (user.getId() % 3 != 0 || user.getId() % 5 == 0) {
            user.setEmail(username + "@ggc.edu");
            userDAO.update(user);
        } else {
            user.setEmail(null);
            System.out.println("***************** \n User created");
            userDAO.update(user);
        }

    }

//    @Scheduled(cron = "*/3 * * * * *")
    @RequestMapping(value = "/createQuotes", method = RequestMethod.POST)
    public void createQuotes() {
        Random r = new Random();
        int index = r.nextInt(20) + 3;
        String random = RandomStringUtils.randomAlphabetic(index);
        Quotes q = new Quotes();
        q.setHappy(random + " " + random + " " + random + " " + random);
        q.setSad(random + " " + random + " " + random + " " + random);
        q.setMotivation(random + " " + random + " " + random + " " + random);
        System.out.println("***************** \n Quotes created");

        quotesDAO.create(q);
    }

//    @Scheduled(cron = "*/3 * * * * *")
    @RequestMapping(value = "/createMusic", method = RequestMethod.POST)
    public void createMusic() {
        Random r = new Random();
        int index = r.nextInt(7) + 3;
        String random = RandomStringUtils.randomAlphabetic(index);
        Music m = new Music();
        m.setArtist(random);
        m.setTitle(random);
        m.setGenre(random);
        System.out.println("***************** \n Music created");

        musicDAO.create(m);
    }

    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id) {
        return userDAO.get(id);
    }

    @RequestMapping(value = "/updateUser/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        User user = userDAO.get(id);
        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            userDAO.update(user);
        }
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id) {
        userDAO.delete(id);
    }

    @RequestMapping(value = "/addfavQuotes/{feeling}/{token}/{id}", method = RequestMethod.GET)
    public List<String> addFavQuotes(@PathVariable String feeling, @PathVariable String token, @PathVariable int id) {
        User user = userDAO.getByToken(token);
        Quotes q = quotesDAO.get(id);
        if (user.getToken().equals(token)) {
            switch (feeling) {
                case "sad":
                    user.addFavQuotes().add("When i'm sad: " + q.getSad());
                    break;

                case "happy":
                    user.addFavQuotes().add("When i'm happy: " + q.getHappy());
                    break;
                case "motivation":
                    user.addFavQuotes().add("When i'm motivated: " + q.getMotivation());
                    break;

            }

        }
        userDAO.update(user);
        // so you can save the changes of the list you're making

        return user.addFavQuotes();
    }

    @RequestMapping(value = "/getFavQuotes/{token}", method = RequestMethod.GET)
    public List<String> getFavQuotes(@PathVariable String token) {
        User user = userDAO.getByToken(token);
        return user.addFavQuotes();

    }

    @RequestMapping(value = "/byToken/{token}", method = RequestMethod.GET)
    public User byToken(@PathVariable String token) {
        userDAO.getByToken(token).addFavQuotes().add("Hello");
        userDAO.getByToken(token).addFavQuotes().add("Hey");
        userDAO.getByToken(token).addFavQuotes().add("Goodbye");
        for (String str : userDAO.getByToken(token).addFavQuotes()) {
            System.out.println(str);
        }
        return userDAO.getByToken(token);
    }

    @RequestMapping(value = "getRandomQuote/{feeling}", method = RequestMethod.GET)
    public String getRandomQuote(@PathVariable String feeling) {
        Random r = new Random();
        int random = r.nextInt(quotesDAO.getAll().size()) + 1;
        Quotes q = quotesDAO.get(random);
        switch (feeling) {
            case "sad":
                System.out.println("random quote " + q.getSad());
                return "SAD: " + q.getSad();
            case "happy":
                System.out.println("random quote " + q.getHappy());
                return "HAPPY: " + q.getHappy();
            case "motivation":
                System.out.println("random quote " + q.getMotivation());
                return "MOTIVATION: " + q.getMotivation();

        }
        return null;
    }

//        @Scheduled(cron = "*/3 * * * * *")
    @RequestMapping(value = "/emailAlerts", method = RequestMethod.GET)
    public List<String> emailAlerts() {
        Random r = new Random();
        int randQuoteNum = r.nextInt(quotesDAO.getAll().size());
        int randMusicNum = r.nextInt(musicDAO.getAll().size());
        Quotes q = quotesDAO.get(randQuoteNum);
        Music m = musicDAO.get(randMusicNum);
        int size = userDAO.getAll().size();
            User user = userDAO.get(r.nextInt(size) + 1);
            if (user.getEmail() != null) {
                user.myAlert().add("Artist: " + m.getArtist() +
                        "\\nTitle: " + m.getTitle() +
                        "\\nMotivation: " + q.getMotivation());
                userDAO.update(user);
                return user.myAlert();
            } else{
                System.out.println(user.getUsername() + "This user does not have an email");
            }
        return null;
    }

//    @RequestMapping(value = "/topQuotes/{feeling}", method = RequestMethod.GET)
//    public List<String> topQuotes() {
//
//    }


}
