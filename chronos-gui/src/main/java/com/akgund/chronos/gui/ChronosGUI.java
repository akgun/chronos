package com.akgund.chronos.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChronosGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Chronos Time Tracker");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/mainScene.fxml"));
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
