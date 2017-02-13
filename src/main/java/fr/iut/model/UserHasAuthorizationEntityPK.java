package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Sydpy on 2/13/17.
 */
public class UserHasAuthorizationEntityPK implements Serializable {
    private int authorizationId;
    private int userId;

    @Column(name = "Authorization_id", nullable = false)
    @Id
    public int getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(int authorizationId) {
        this.authorizationId = authorizationId;
    }

    @Column(name = "User_id", nullable = false)
    @Id
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

        UserHasAuthorizationEntityPK that = (UserHasAuthorizationEntityPK) o;

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
