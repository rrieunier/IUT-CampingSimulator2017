package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "User_has_Authorization", schema = "CampingSimulator", catalog = "")
@IdClass(UserHasAuthorizationPK.class)
public class UserHasAuthorization {
    private int userId;
    private String authorizationLabel;

    @Id
    @Column(name = "User_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "Authorization_label", nullable = false, length = 45)
    public String getAuthorizationLabel() {
        return authorizationLabel;
    }

    public void setAuthorizationLabel(String authorizationLabel) {
        this.authorizationLabel = authorizationLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHasAuthorization that = (UserHasAuthorization) o;

        if (userId != that.userId) return false;
        if (authorizationLabel != null ? !authorizationLabel.equals(that.authorizationLabel) : that.authorizationLabel != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (authorizationLabel != null ? authorizationLabel.hashCode() : 0);
        return result;
    }
}
