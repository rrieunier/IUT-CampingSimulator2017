package fr.iut.persistence.dao;

/**
 * Created by Sydpy on 3/13/17.
 */
public class DAOTestUtils {

    public static String randomString(){
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }

}
