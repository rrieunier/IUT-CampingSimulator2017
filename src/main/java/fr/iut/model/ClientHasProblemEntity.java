package fr.iut.model;

import javax.persistence.*;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Client_has_Problem", schema = "CampingSimulator", catalog = "")
@IdClass(ClientHasProblemEntityPK.class)
public class ClientHasProblemEntity {
    private int idClient;
    private int idProblem;
    private double discount;
    private ClientEntity clientByIdClient;
    private ProblemEntity problemByIdProblem;

    @Id
    @Column(name = "idClient", nullable = false)
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Id
    @Column(name = "idProblem", nullable = false)
    public int getIdProblem() {
        return idProblem;
    }

    public void setIdProblem(int idProblem) {
        this.idProblem = idProblem;
    }

    @Basic
    @Column(name = "discount", nullable = false, precision = 0)
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientHasProblemEntity that = (ClientHasProblemEntity) o;

        if (idClient != that.idClient) return false;
        if (idProblem != that.idProblem) return false;
        if (Double.compare(that.discount, discount) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idClient;
        result = 31 * result + idProblem;
        temp = Double.doubleToLongBits(discount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idClient", referencedColumnName = "idClient", nullable = false)
    public ClientEntity getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientEntity clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }

    @ManyToOne
    @JoinColumn(name = "idProblem", referencedColumnName = "idProblem", nullable = false)
    public ProblemEntity getProblemByIdProblem() {
        return problemByIdProblem;
    }

    public void setProblemByIdProblem(ProblemEntity problemByIdProblem) {
        this.problemByIdProblem = problemByIdProblem;
    }
}
