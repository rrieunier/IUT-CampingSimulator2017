package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/12/17.
 */
@Entity
@Table(name = "User_has_Authorization", schema = "CampingSimulator", catalog = "")
@IdClass(UserHasAuthorizationEntityPK.class)
public class UserHasAuthorizationEntity {
    private int authorizationId;
    private int userId;

    @Id
    @Column(name = "Authorization_id", nullable = false)
    public int getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(int authorizationId) {
        this.authorizationId = authorizationId;
    }

    @Id
    @Column(name = "User_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHasAuthorizationEntity that = (UserHasAuthorizationEntity) o;

        if (authorizationId != that.authorizationId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = authorizationId;
        result = 31 * result + userId;
        return result;
    }
}
