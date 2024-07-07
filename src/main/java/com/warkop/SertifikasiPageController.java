package com.warkop;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class SertifikasiPageController {
    @FXML
    private Button tombolMateriPelatihan;

    @FXML
    private Button tombolHome;

    @FXML
    private Button tombolKalenderInteraktif;

    @FXML
    private Button tombolChatBox;

    @FXML
    private Button tombolKeluar;
    @FXML
    private Label labelMateriPelatihan;
    @FXML
    private Label labelChatBox;
    @FXML
    private Label labelKalenderInteraktif;
    @FXML
    private Label labelHome;
    @FXML
    private Label labelNamaAkun;
    

    public void initialize() {
        labelNamaAkun.setText(AccountModel.getInstance().getAccount().getNamaLengkap());
        tombolChatBox.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelChatBox.setStyle("-fx-underline: true"));
        tombolChatBox.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelChatBox.setStyle("-fx-underline: false"));
        tombolKalenderInteraktif.addEventHandler(MouseEvent.MOUSE_ENTERED,
                event -> labelKalenderInteraktif.setStyle("-fx-underline: true"));
        tombolKalenderInteraktif.addEventHandler(MouseEvent.MOUSE_EXITED,
                event -> labelKalenderInteraktif.setStyle("-fx-underline: false"));
        tombolMateriPelatihan.addEventHandler(MouseEvent.MOUSE_ENTERED,
                event -> labelMateriPelatihan.setStyle("-fx-underline: true"));
        tombolMateriPelatihan.addEventHandler(MouseEvent.MOUSE_EXITED,
                event -> labelMateriPelatihan.setStyle("-fx-underline: false"));
        tombolHome.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelHome.setStyle("-fx-underline: true"));
        tombolHome.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelHome.setStyle("-fx-underline: false"));
        tombolKeluar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                event -> tombolKeluar.setStyle("-fx-background-color:#ff8d7b "));
        tombolKeluar.addEventHandler(MouseEvent.MOUSE_EXITED,
                event -> tombolKeluar.setStyle("-fx-background-color: #ff2200"));
    }

    public void handleHomeAction() {
        AccountModel.getInstance().getSessionManager().removeStage(Stage.class.cast(tombolHome.getScene().getWindow()));
        AccountModel.getInstance().getSessionManager().getBerandaView();

    }

    public void handleKeluarAction() throws IOException {
        AccountModel.getInstance().getAccount().removeUserAccount();
        AccountModel.getInstance().getSessionManager().showLogin();
    }

    public void handleKalenderInteraktifAction() {
        AccountModel.getInstance().getSessionManager().showKalender();
    }

    public void handleChatboxAction() {
        AccountModel.getInstance().getSessionManager().showChatBox();
    }

    public void handleMateriPelatihanAction() {
        AccountModel.getInstance().getSessionManager().removeStage(Stage.class.cast(tombolMateriPelatihan.getScene().getWindow()));
        AccountModel.getInstance().getSessionManager().getMateriPelatihanView();
    }
}
