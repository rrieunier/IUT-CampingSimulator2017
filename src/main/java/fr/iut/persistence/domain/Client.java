package fr.iut.persistence.domain;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "CLIENT")
public class Client {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false, length = 45)
    private String lastname;

    @Column(nullable = false, length = 45)
    private String firstname;

    @Column(length = 11)
    private String phone;

    @Column(length = 45)
    private String email;

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
