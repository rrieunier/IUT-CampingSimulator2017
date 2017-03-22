package fr.iut;

/**
 * Structure of different states between UIs
 */
public enum State {

    /**
     * First launch UI, choose your login/password, firstname/lastname, ...
     */
    FIRST_LAUNCH,

    /**
     * Connection UI, enables the user to login into the software
     */
    CONNECTION,

    /**
     * Main software window where the user can manage his camping
     */
    HOME
}
