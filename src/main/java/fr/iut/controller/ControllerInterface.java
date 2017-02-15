package fr.iut.controller;

import javafx.scene.Scene;

/**
 * Created by shellcode on 2/13/17.
 */
public interface ControllerInterface<T> {
    T getView();
    void finish();
}
