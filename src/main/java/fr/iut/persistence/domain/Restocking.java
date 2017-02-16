package fr.iut.persistence.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "RESTOCKING")
public class Restocking {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());
}
