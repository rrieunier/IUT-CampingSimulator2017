package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by roman on 10/02/17.
 */
public class UserHasAuthorizationEntityPK implements Serializable {
    private String login;
    private int idAuthorization;

    @Column(name = "login", nullable = false, length = 20)
    @Id
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "idAuthorization", nullable = false)
    @Id
    public int getIdAuthorization() {
        return idAuthorization;
    }

    public void setIdAuthorization(int idAuthorization) {
        this.idAuthorization = idAuthorization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHasAuthorizationEntityPK that = (UserHasAuthorizationEntityPK) o;

        if (idAuthorization != that.idAuthorization) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + idAuthorization;
        return result;
    }
}
