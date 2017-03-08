package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "CLIENT")
public class Client extends EntityModel<Integer> {

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
            targetEntity=Problem.class,
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            joinColumns=@JoinColumn(name="client_id"),
            inverseJoinColumns=@JoinColumn(name="problem_id")
    )
    private Set<Problem> problems = new HashSet<>();

    /**
     * Reservations made by the client.
     */
    @OneToMany(mappedBy = "client")
    private Set<Reservation> reservations = new HashSet<>();

    /**
     * Purchases made by the client.
     */
    @OneToMany(mappedBy = "client")
    private Set<Purchase> purchases = new HashSet<>();

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
}
