package fr.iut.model;

import javax.persistence.*;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "User_has_Authorization", schema = "CampingSimulator", catalog = "")
@IdClass(UserHasAuthorizationEntityPK.class)
public class UserHasAuthorizationEntity {
    private String login;
    private int idAuthorization;
    private UserEntity userByLogin;
    private AuthorizationEntity authorizationByIdAuthorization;

    @Id
    @Column(name = "login", nullable = false, length = 20)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Id
    @Column(name = "idAuthorization", nullable = false)
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

        UserHasAuthorizationEntity that = (UserHasAuthorizationEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "login", referencedColumnName = "login", nullable = false)
    public UserEntity getUserByLogin() {
        return userByLogin;
    }

    public void setUserByLogin(UserEntity userByLogin) {
        this.userByLogin = userByLogin;
    }

    @ManyToOne
    @JoinColumn(name = "idAuthorization", referencedColumnName = "idAuthorization", nullable = false)
    public AuthorizationEntity getAuthorizationByIdAuthorization() {
        return authorizationByIdAuthorization;
    }

    public void setAuthorizationByIdAuthorization(AuthorizationEntity authorizationByIdAuthorization) {
        this.authorizationByIdAuthorization = authorizationByIdAuthorization;
    }
}
