package com.warkop;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AccountModel.getInstance().getSessionManager().showLogin();
    } 

    public static void main(String[] args) {
        launch();
    }
}
