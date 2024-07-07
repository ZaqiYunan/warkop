package com.warkop;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoginPageController {
    @FXML
    private TextField inputEmail;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private CheckBox tombolCaptcha;
    @FXML
    private Button tombolMasuk;
    @FXML
    private Label errorLabel;
    @FXML
    private Label labelCaptcha;
    @FXML
    private Button tombolRegistrasi;

    private static final String DATA_USER_FILE_PATH = "src/main/resources/DATABASE/DataUser.json";

    public void handlelabelCaptchaAction() {
        if (tombolCaptcha.isSelected()) {
            labelCaptcha.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        } else {
            labelCaptcha.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        }
    }

    public void handleSignInButtonAction() {
        String email = inputEmail.getText();
        String password = inputPassword.getText();
        boolean captchaChecked = tombolCaptcha.isSelected();
    
        if (email.isEmpty() || password.isEmpty() || !captchaChecked) {
            errorLabel.setText("Harap isi semua kolom dan centang captcha!");
            return;
        }
    
        try {
            File jsonFile = new File(DATA_USER_FILE_PATH);
    
            if (jsonFile.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
                    JsonArray jsonArray = JsonParser.parseReader(br).getAsJsonArray();
    
                    // Check for admin login
                    if (email.equals("Admin") && password.equals("Admin123")) {
                        Stage stage = (Stage) tombolMasuk.getScene().getWindow();
                        AccountModel.getInstance().getSessionManager().removeStage(stage);
                        AccountModel.getInstance().getSessionManager().getAdminPageView();
                        return;
                    }
    
                    // Check for regular user login
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
    
                        // Check if all necessary fields exist before accessing them
                        if (jsonObject.has("email") && jsonObject.has("password")
                                && jsonObject.has("role") && jsonObject.has("namaLengkap")
                                && jsonObject.has("id")) {
    
                            String jsonEmail = jsonObject.get("email").getAsString();
                            String jsonPassword = jsonObject.get("password").getAsString();
                            String jsonRole = jsonObject.get("role").getAsString();
                            String jsonNamaLengkap = jsonObject.get("namaLengkap").getAsString();
                            String jsonUserID = jsonObject.get("id").getAsString();
    
                            if (email.equals(jsonEmail) && password.equals(jsonPassword)) {
                                AccountModel.getInstance().getAccount().setUserAccount(jsonUserID, jsonRole,
                                        jsonNamaLengkap, jsonEmail, jsonPassword);
                                Stage stage = (Stage) tombolMasuk.getScene().getWindow();
                                AccountModel.getInstance().getSessionManager().removeStage(stage);
                                AccountModel.getInstance().getSessionManager().getBerandaView();
                                return;
                            }
                        } else {
                            // Handle case where JSON object is missing required fields
                            System.err.println("JSON object at index " + i + " is missing required fields.");
                        }
                    }
    
                    // If no matching user found
                    errorLabel.setText("Email atau password salah.");
                }
            } else {
                errorLabel.setText("DataUser.json file not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("An error occurred while signing in. Please try again.");
        }
    }

    public void handleRegistrasiButtonAction() {
        Stage stage = (Stage) tombolRegistrasi.getScene().getWindow();
        AccountModel.getInstance().getSessionManager().removeStage(stage);
        AccountModel.getInstance().getSessionManager().showRegistrasi();

    }
}
