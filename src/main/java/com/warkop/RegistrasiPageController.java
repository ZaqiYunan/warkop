package com.warkop;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.net.URI;

public class RegistrasiPageController {

    @FXML
    private TextField inputNamaLengkap;

    @FXML
    private TextField inputEmail;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Button tombolRegistrasi1;

    @FXML
    private Button tombolMasuk;

    @FXML
    private Label errorLabel;

    public void initialize() {
        createJsonFileIfNotExists();
    }
    private static final String DATA_USER_FILE_PATH = "src/main/resources/DATABASE/DataUser.json"; 

    private void createJsonFileIfNotExists() {
        try {
            File jsonFile = new File(DATA_USER_FILE_PATH);

            if (!jsonFile.exists()) {
                Files.createDirectories(Paths.get("DATABASE")); // Ensure directory exists
                jsonFile.createNewFile();
                initializeJsonFile(jsonFile);
            } else if (jsonFile.length() == 0) {
                initializeJsonFile(jsonFile);
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to create JSON file");
        }
    }

    private void initializeJsonFile(File jsonFile) {
        try (FileWriter writer = new FileWriter(jsonFile)) {
            JsonArray jsonArray = new JsonArray();
            writer.write(jsonArray.toString());
        } catch (IOException e) {
            showAlert("Error", "Failed to initialize JSON file");
        }
    }

    public void handleRegisterAction() {
        String namaLengkap = inputNamaLengkap.getText();
        String email = inputEmail.getText();
        String password = inputPassword.getText();

        if (namaLengkap.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert("Error", "Invalid email format.");
            return;
        }

        try {
            File jsonFile = new File(DATA_USER_FILE_PATH);
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            JsonElement jsonElement = JsonParser.parseReader(reader);
            reader.close();

            if (!jsonElement.isJsonArray()) {
                showAlert("Error", "Invalid JSON format.");
                return;
            }

            JsonArray usersArray = jsonElement.getAsJsonArray();

            for (JsonElement userElement : usersArray) {
                if (!userElement.isJsonObject()) {
                    showAlert("Error", "Invalid user data format.");
                    return;
                }
            }

            // Create a new user account
            UserAcount newUser = new UserAcount();
            newUser.setUserAccount(UUID.randomUUID().toString(),"Guest", namaLengkap, email, password);

            // Create JSON object for new user
            JsonObject newUserObject = new JsonObject();
            newUserObject.addProperty("id", newUser.getUserID());
            newUserObject.addProperty("role", newUser.getRole());
            newUserObject.addProperty("namaLengkap", newUser.getNamaLengkap());
            newUserObject.addProperty("email", newUser.getEmail());
            newUserObject.addProperty("password", newUser.getPassword());

            // Add new user object to usersArray
            usersArray.add(newUserObject);

            // Write updated JSON array back to file
            try (FileWriter writer = new FileWriter(jsonFile)) {
                writer.write(usersArray.toString());
                writer.flush();
                writer.close();
            }

            // Set user account in AccountModel
            AccountModel.getInstance().getAccount().setUserAccount(
                    newUser.getUserID(), newUser.getRole(), newUser.getNamaLengkap(), newUser.getEmail(), newUser.getPassword()
            );

            // Close registration window and show login window
            Stage stage = (Stage) tombolRegistrasi1.getScene().getWindow();
            AccountModel.getInstance().getSessionManager().removeStage(stage);
            AccountModel.getInstance().getSessionManager().showLogin();

            // Show success message and clear input fields
            showAlert("Success", "Registration successful.");
            clearFields();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while registering. Please try again.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        inputNamaLengkap.clear();
        inputEmail.clear();
        inputPassword.clear();
        errorLabel.setText("");
    }

    public void handleMasukButtonAction() throws IOException{
        Stage stage = (Stage) tombolMasuk.getScene().getWindow();
        AccountModel.getInstance().getSessionManager().removeStage(stage);
        AccountModel.getInstance().getSessionManager().showLogin();
    }
}
