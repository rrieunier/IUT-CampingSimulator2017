package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
public class Client {

    @Id @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "lastname", nullable = false, length = 45)
    private String lastname;

    @Column(name = "firstname", nullable = false, length = 45)
    private String firstname;

    @Column(name = "phone", nullable = true, length = 11)
    private String phone;

    @Column(name = "email", nullable = true, length = 45)
    private String email;


    private Collection<Purchase> purchasesById;


    private Collection<Reservation> reservationsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "clientByClientId")
    public Collection<Purchase> getPurchasesById() {
        return purchasesById;
    }

    public void setPurchasesById(Collection<Purchase> purchasesById) {
        this.purchasesById = purchasesById;
    }

    @OneToMany(mappedBy = "clientByClientId")
    public Collection<Reservation> getReservationsById() {
        return reservationsById;
    }

    public void setReservationsById(Collection<Reservation> reservationsById) {
        this.reservationsById = reservationsById;
    }
}
