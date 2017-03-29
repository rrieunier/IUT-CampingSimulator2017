package fr.iut.persistence.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Client implements EntityModel<Integer> {

    /**
     * Client's id.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Client's lastname.
     */
    @Column(nullable = false, length = 45)
    private String lastname;

    /**
     * Client's firstname.
     */
    @Column(nullable = false, length = 45)
    private String firstname;

    /**
     * Client's phone number.
     */
    @Column(length = 11)
    private String phone;

    /**
     * Client's email.
     */
    @Column(length = 45)
    private String email;

    /**
     * Problems encountered by the client.
     */
    @ManyToMany(
            targetEntity=Problem.class
    )
    @JoinTable(
            joinColumns=@JoinColumn(name="client_id"),
            inverseJoinColumns=@JoinColumn(name="problem_id")
    )
    private Set<Problem> problems;

    /**
     * Reservations made by the client.
     */
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Reservation> reservations;

    /**
     * Purchases made by the client.
     */
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    private Set<Purchase> purchases;

    public Integer getId() {
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

    public Set<Problem> getProblems() {
        return problems;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    public String toString() { return getLastname() + " " + getFirstname(); }
}
