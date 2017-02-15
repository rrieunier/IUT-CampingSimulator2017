package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Sydpy on 2/15/17.
 */
public class ClientHasProblemPK implements Serializable {
    private int clientId;
    private int problemId;

    @Column(name = "Client_id", nullable = false)
    @Id
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Column(name = "Problem_id", nullable = false)
    @Id
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

        ClientHasProblemPK that = (ClientHasProblemPK) o;

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
