package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Authorization", schema = "CampingSimulator", catalog = "")
public class AuthorizationEntity {
    private int idAuthorization;
    private String label;
    private Collection<UserHasAuthorizationEntity> userHasAuthorizationsByIdAuthorization;

    @Id
    @Column(name = "idAuthorization", nullable = false)
    public int getIdAuthorization() {
        return idAuthorization;
    }

    public void setIdAuthorization(int idAuthorization) {
        this.idAuthorization = idAuthorization;
    }

    @Basic
    @Column(name = "label", nullable = true, length = 45)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorizationEntity that = (AuthorizationEntity) o;

        if (idAuthorization != that.idAuthorization) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAuthorization;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "authorizationByIdAuthorization")
    public Collection<UserHasAuthorizationEntity> getUserHasAuthorizationsByIdAuthorization() {
        return userHasAuthorizationsByIdAuthorization;
    }

    public void setUserHasAuthorizationsByIdAuthorization(Collection<UserHasAuthorizationEntity> userHasAuthorizationsByIdAuthorization) {
        this.userHasAuthorizationsByIdAuthorization = userHasAuthorizationsByIdAuthorization;
    }
}
