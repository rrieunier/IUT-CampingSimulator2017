package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by roman on 10/02/17.
 */
public class ClientHasProblemEntityPK implements Serializable {
    private int idClient;
    private int idProblem;

    @Column(name = "idClient", nullable = false)
    @Id
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Column(name = "idProblem", nullable = false)
    @Id
    public int getIdProblem() {
        return idProblem;
    }

    public void setIdProblem(int idProblem) {
        this.idProblem = idProblem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientHasProblemEntityPK that = (ClientHasProblemEntityPK) o;

        if (idClient != that.idClient) return false;
        if (idProblem != that.idProblem) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idClient;
        result = 31 * result + idProblem;
        return result;
    }
}
