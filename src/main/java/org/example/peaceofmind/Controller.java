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

    // Create a random user with a unique token
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    // modelAttribute helps properly map the inputs in the form to Java objects
    public User createUser(@ModelAttribute User user) {
        String token = RandomStringUtils.randomAlphanumeric(10);
        user.setToken(user.getToken());
        user.setUsername(user.getUsername());
        user.setToken(token);
        userDAO.create(user);
        return user;
    }
    @Scheduled(cron = "*/3 * * * * *")
    @RequestMapping(value = "/createRandomUser", method = RequestMethod.POST)
    public void createRandomUser() {
        String token = RandomStringUtils.randomAlphanumeric(10);
        String username = RandomStringUtils.randomAlphanumeric(10);
        String email = RandomStringUtils.randomAlphanumeric(10);
        User user = new User();
        user.setToken(token);
        user.setUsername(username);
        user.setEmail(email + "@ggc.edu");
        userDAO.create(user);
    }

//    @Scheduled(cron = "*/3 * * * * *")

    // creating random quotes in the database
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
    //Create Random music for the database
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
            user.setEmail(updatedUser.getEmail());
            userDAO.update(user);
        }
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id) {
        userDAO.delete(id);
    }

    @RequestMapping(value = "/getfavQuotes/{token}/{feeling}/{id}", method = RequestMethod.GET)
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

//    @RequestMapping(value = "/byToken/{token}", method = RequestMethod.GET)
//    public User byToken(@PathVariable String token) {
//        userDAO.getByToken(token).addFavQuotes().add("Hello");
//        userDAO.getByToken(token).addFavQuotes().add("Hey");
//        userDAO.getByToken(token).addFavQuotes().add("Goodbye");
//        for (String str : userDAO.getByToken(token).addFavQuotes()) {
//            System.out.println(str);
//        }
//        return userDAO.getByToken(token);
//    }

    // This displays a random quote based on how you're feeling
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

//    @RequestMapping(value = "/emailAlerts", method = RequestMethod.GET)
//    public List<String> emailAlerts() {
//        Random r = new Random();
//        int randQuoteNum = r.nextInt(quotesDAO.getAll().size());
//        int randMusicNum = r.nextInt(musicDAO.getAll().size());
//        Quotes q = quotesDAO.get(randQuoteNum);
//        Music m = musicDAO.get(randMusicNum);
//        int size = userDAO.getAll().size();
//        User user = userDAO.get(r.nextInt(size) + 1);
//        if (user.getEmail() != null) {
//            user.myAlert().add("Artist: " + m.getArtist() +
//                    "\\nTitle: " + m.getTitle() +
//                    "\\nMotivation: " + q.getMotivation());
//            userDAO.update(user);
//            return user.myAlert();
//        } else {
//            System.out.println(user.getUsername() + "This user does not have an email");
//        }
//        return null;
//    }

    @RequestMapping(value = "/topQuotes/{feeling}", method = RequestMethod.GET)
    public List<String> topQuotes() {
        return new ArrayList<>();

    }
@Scheduled(cron = "0 0 0 * * 0")
    @RequestMapping(value = "/sendEmailAlert", method = RequestMethod.GET)
    public void sendEmailAlert() {
        Random r = new Random();
        int randQuoteNum = r.nextInt(quotesDAO.getAll().size());
        int randMusicNum = r.nextInt(musicDAO.getAll().size());
        Quotes q = quotesDAO.get(randQuoteNum);
        Music m = musicDAO.get(randMusicNum);
        List<Integer> users = userDAO.getAll();
       for(int i = 0; i < users.size(); i++) {
           User user = userDAO.get(users.get(i));
           if(user.getEmail() != null) {
               user.getEmailAlerts().add("Artist: " + m.getArtist() +
                    "\\nTitle: " + m.getTitle() +
                    "\\nMotivation: " + q.getMotivation()
               );
               userDAO.update(user);
               System.out.println("Email for: " +  user.getUsername() + "\n" + user.getEmailAlerts());
           }
       }
    }




}
