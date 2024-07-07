package com.warkop;


import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ScrollPane;
import java.net.URL;
import javafx.scene.shape.Shape;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MateriPelatihanPageController implements Initializable {
    @FXML
    private Button tombolSertifikasi;
    @FXML
    private Button tombolHome;
    @FXML
    private ScrollPane scrollPane;
    @FXML 
    private Button tombolKalenderInteraktif;

    @FXML
    private Button tombolChatBox;

    @FXML 
    private Button tombolKeluar;
    @FXML
    private Label labelSertifikasi;
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
    @FXML
    private Button tombolFundamenBisnis;
    @FXML
    private Button tombolDigitalisasiPasar;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        labelNamaAkun.setText(AccountModel.getInstance().getAccount().getNamaLengkap());
        tombolChatBox.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelChatBox.setStyle("-fx-underline: true"));
        tombolChatBox.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelChatBox.setStyle("-fx-underline: false"));
        tombolKalenderInteraktif.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelKalenderInteraktif.setStyle("-fx-underline: true"));
        tombolKalenderInteraktif.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelKalenderInteraktif.setStyle("-fx-underline: false"));
        tombolHome.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelHome.setStyle("-fx-underline: true"));
        tombolHome.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelHome.setStyle("-fx-underline: false"));
        tombolKeluar.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> tombolKeluar.setStyle("-fx-background-color:#ff8d7b "));
        tombolKeluar.addEventHandler(MouseEvent.MOUSE_EXITED, event -> tombolKeluar.setStyle("-fx-background-color: #ff2200"));
    }

    public void handleHomeAction(){
        AccountModel.getInstance().getSessionManager().removeStage(Stage.class.cast(tombolHome.getScene().getWindow()));
        AccountModel.getInstance().getSessionManager().getBerandaView();
    }
    public void handleKeluarAction() throws IOException{
        Stage stage = (Stage) tombolKeluar.getScene().getWindow();
        AccountModel.getInstance().getSessionManager().removeStage(stage);
        AccountModel.getInstance().getSessionManager().showLogin();
    }
    public void handleKalenderInteraktifAction(){
        AccountModel.getInstance().getSessionManager().showKalender();
    }
    public void handleChatboxAction(){
    
    }
    public void handleSertifikasiAction(){
        AccountModel.getInstance().getSessionManager().removeStage(Stage.class.cast(tombolSertifikasi.getScene().getWindow()));
        AccountModel.getInstance().getSessionManager().getSertifikasiView();
        
    }
    public void handleFundamenBisnisAction(){   
        AccountModel.getInstance().getSessionManager().getMateriView();
    }
    public void handleDigitalisasiPasarAction(){
        AccountModel.getInstance().getSessionManager().getMateriView();
    }
    public void tambahProgres(){
        
    }
}
