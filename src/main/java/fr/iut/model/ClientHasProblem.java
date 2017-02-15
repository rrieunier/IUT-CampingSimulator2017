package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "Client_has_Problem", schema = "CampingSimulator", catalog = "")
@IdClass(ClientHasProblemPK.class)
public class ClientHasProblem {
    private int clientId;
    private int problemId;

    @Id
    @Column(name = "Client_id", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Id
    @Column(name = "Problem_id", nullable = false)
    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientHasProblem that = (ClientHasProblem) o;

        if (clientId != that.clientId) return false;
        if (problemId != that.problemId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clientId;
        result = 31 * result + problemId;
        return result;
    }

}
