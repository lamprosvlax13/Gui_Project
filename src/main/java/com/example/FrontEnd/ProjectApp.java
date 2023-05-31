package com.example.FrontEnd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader =new FXMLLoader(ProjectApp.class.getResource("app.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ΠΡΟΧ. ΘΕΜΑΤΑ ΤΕΧΝΟΛΟΓΙΑΣ & ΕΦΑΡΜΟΓΩΝ ΒΑΣΕΩΝ ΔΕΔΟΜΕΝΩΝ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}