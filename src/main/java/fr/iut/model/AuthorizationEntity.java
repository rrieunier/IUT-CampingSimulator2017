package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "Authorization", schema = "CampingSimulator", catalog = "")
public class AuthorizationEntity {
    private String label;
    private int id;
    private Collection<UserHasAuthorizationEntity> userHasAuthorizations;

    @Basic
    @Column(name = "label", nullable = true, length = 45)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorizationEntity that = (AuthorizationEntity) o;

        if (id != that.id) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    @OneToMany(mappedBy = "authorization")
    public Collection<UserHasAuthorizationEntity> getUserHasAuthorizations() {
        return userHasAuthorizations;
    }

    public void setUserHasAuthorizations(Collection<UserHasAuthorizationEntity> userHasAuthorizations) {
        this.userHasAuthorizations = userHasAuthorizations;
    }
}
