package fr.iut.persistence;

/**
 * Created by Sydpy on 3/13/17.
 */
public class TestUtils {

    public static String randomString(){
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }

}
