package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Sydpy on 2/15/17.
 */
public class UserHasAuthorizationPK implements Serializable {
    private int userId;
    private String authorizationLabel;

    @Column(name = "User_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "Authorization_label", nullable = false, length = 45)
    @Id
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

        UserHasAuthorizationPK that = (UserHasAuthorizationPK) o;

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
