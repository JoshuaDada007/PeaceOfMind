package org.example.peaceofmind;

import jakarta.persistence.*;

@Entity
@Table(name = "Quotes")
public class Quotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String happy;
    private String sad;
    private String motivation;

    public Quotes() {
    }



    public String getHappy() {
        return happy;
    }

    public void setHappy(String happy) {
        this.happy = happy;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getSad() {
        return sad;
    }

    public void setSad(String sad) {
        this.sad = sad;
    }
    @Override
    public String toString() {
        return "Quotes{" +
                "id=" + id +
                ", happy='" + happy + '\'' +
                ", sad='" + sad + '\'' +
                ", motivation='" + motivation + '\'' +
                '}';
    }


}
