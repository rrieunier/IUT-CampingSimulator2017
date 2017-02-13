package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "User_has_Authorization", schema = "CampingSimulator", catalog = "")
@IdClass(UserHasAuthorizationPK.class)
public class UserHasAuthorization {
    private Authorization authorizationByAuthorizationId;
    private User userByUserId;
    private int authorizationId;
    private int userId;

    @ManyToOne
    @JoinColumn(name = "Authorization_id", referencedColumnName = "id", nullable = false)
    public Authorization getAuthorizationByAuthorizationId() {
        return authorizationByAuthorizationId;
    }

    public void setAuthorizationByAuthorizationId(Authorization authorizationByAuthorizationId) {
        this.authorizationByAuthorizationId = authorizationByAuthorizationId;
    }

    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

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

        UserHasAuthorization that = (UserHasAuthorization) o;

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
