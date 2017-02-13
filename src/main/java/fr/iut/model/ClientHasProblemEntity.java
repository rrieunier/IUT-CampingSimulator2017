package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "Client_has_Problem", schema = "CampingSimulator", catalog = "")
@IdClass(ClientHasProblemEntityPK.class)
public class ClientHasProblemEntity {
    private int clientId;
    private int problemId;
    private ClientEntity client;
    private ProblemEntity problem;

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

        ClientHasProblemEntity that = (ClientHasProblemEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "Client_id", referencedColumnName = "id", nullable = false)
    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    @ManyToOne
    @JoinColumn(name = "Problem_id", referencedColumnName = "id", nullable = false)
    public ProblemEntity getProblem() {
        return problem;
    }

    public void setProblem(ProblemEntity problem) {
        this.problem = problem;
    }
}
