package fr.iut.persistence.dao;

import fr.iut.persistence.entities.Reservation;

/**
 * Created by shellcode on 3/21/17.
 */
public class ReservationsDAO extends GenericDAO<Reservation, Integer> {

    public ReservationsDAO() {
        super(Reservation.class);
    }
}
