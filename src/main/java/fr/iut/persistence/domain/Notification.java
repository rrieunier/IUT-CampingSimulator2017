package fr.iut.persistence.domain;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "NOTIFICATION")
public class Notification {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(nullable = false)
    private String content;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
