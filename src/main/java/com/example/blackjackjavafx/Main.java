package com.example.blackjackjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pealava) throws IOException {
        Scene stseen = new Scene(new Pane());
        VaateVahetaja.setStseen(stseen);
        VaateVahetaja.vaheta(Vaade.MÄNGIJAD);
        pealava.setScene(stseen);
        pealava.show();
    }
}
