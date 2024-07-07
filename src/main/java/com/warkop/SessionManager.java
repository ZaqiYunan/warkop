package com.warkop;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SessionManager {

    private final StringProperty selectedScene;
    

    public SessionManager() {
        this.selectedScene = new SimpleStringProperty();
    }

    public StringProperty getSelectedScene() {
        return selectedScene;
    }

    private void createStage(FXMLLoader loads) {
        Scene scene = null;
        try {
            scene = new Scene(loads.load());
        } catch (Exception e) {
            e.printStackTrace();

        }
        Stage stage = new Stage();
        stage.setTitle("Warkop");
        stage.setScene(scene);
        stage.show();
    }

    public void removeStage(Stage stage) {
        stage.close();
    }

    public void showLogin() throws IOException {
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/LoginPage.fxml"));
        createStage(loads);
    }
    public void showKalender(){
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/KalenderPage.fxml"));
        createStage(loads);
    }
    public void showKelasVirtual(){
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/KelasVirtualPage.fxml"));
        createStage(loads);
    }
    public void showChatBox(){
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/ChatBoxPage.fxml"));
        createStage(loads);
    }
    public void showMain() throws IOException{
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/MainPage.fxml"));
        createStage(loads);
    }
    public void showRegistrasi(){
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/RegistrasiPage.fxml"));
        createStage(loads);
    }

    public void getBerandaView()  {
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/BerandaPage.fxml"));
        createStage(loads);
    }

    public void getSertifikasiView()  {
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/SertifikasiPage.fxml"));
        createStage(loads);
    }

    public void getMateriPelatihanView() {
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/MateriPelatihanPage.fxml"));
        createStage(loads);

    }
    public void getAdminPageView(){
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/AdminPage.fxml"));
        createStage(loads);
    }
    public void getMateriView(){
        FXMLLoader loads = new FXMLLoader(getClass().getResource("/FXML/Materi.fxml"));
        createStage(loads);
    }
    


}
