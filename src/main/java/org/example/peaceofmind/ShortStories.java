package org.example.peaceofmind;

import jakarta.persistence.*;

@Entity
@Table(name = "Stories")
public class ShortStories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sad;
    private String happy;
    private String depressed;
    private String thankful;
    private String angry;

    public ShortStories() {
    }

    public ShortStories(String sad, String happy, String depressed, String thankful, String angry){
        this.sad = sad;
        this.happy = happy;
        this.depressed = depressed;
        this.thankful = thankful;
        this.angry = angry;

    }
    public String getSad() {
        return sad;
    }

    public void setSad(String sad) {
        this.sad = sad;
    }

    public String getHappy() {
        return happy;
    }

    public void setHappy(String happy) {
        this.happy = happy;
    }

    public String getDepressed() {
        return depressed;
    }

    public void setDepressed(String depressed) {
        this.depressed = depressed;
    }

    public String getThankful() {
        return thankful;
    }

    public void setThankful(String thankful) {
        this.thankful = thankful;
    }

    public String getAngry() {
        return angry;
    }

    public void setAngry(String angry) {
        this.angry = angry;
    }
}
