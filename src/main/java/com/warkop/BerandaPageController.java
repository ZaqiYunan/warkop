package com.warkop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

public class BerandaPageController implements Initializable {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Rectangle rectangleSertifikasi;
    @FXML
    private Button kelasVirtualButton;
    @FXML
    private Button tombolSertifikasi;
    @FXML
    private Button tombolMateriPelatihan;
    @FXML
    private Button tombolKalenderInteraktif;
    @FXML
    private Button tombolChatBox;
    @FXML
    private Button tombolMulaiDariSini;
    @FXML
    private Rectangle rectangleKeluar;
    @FXML
    private Button tombolKeluar;
    @FXML
    private Label labelSertifikasi;
    @FXML
    private Label labelMateriPelatihan;
    @FXML
    private Label labelKalenderInteraktif;
    @FXML
    private Label labelNamaAkun;
    @FXML
    private Label labelChatBox;
    @FXML
    private Label labelProgresAnda;
    @FXML
    private Label labelDasar;
    @FXML
    private Label labelMenengah;
    @FXML
    private Label labelLanjut;
    @FXML
    private ProgressBar progressBarDasar;
    @FXML
    private ProgressBar progressBarMenengah;
    @FXML
    private ProgressBar progressBarLanjut;
    @FXML
    private Button tombolMateriDasar;
    @FXML
    private Button tombolBeliPaket;
    @FXML
    private Shape rectangleMateriDasar;

    private static final String DATA_USER_FILE_PATH = "src/main/resources/DATABASE/DataUser.json";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelNamaAkun.setText(AccountModel.getInstance().getAccount().getCurrentUser().getNamaLengkap());
        tombolMulaiDariSini.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleMulaiDariSiniAction());
        tombolMateriDasar.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleBeliMateriDasar());
        tombolMateriDasar.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> rectangleMateriDasar.setStyle("-fx-fill: #16aba6"));
        tombolMateriDasar.addEventHandler(MouseEvent.MOUSE_EXITED, event -> rectangleMateriDasar.setStyle("-fx-fill: #2cfffb"));
        tombolSertifikasi.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelSertifikasi.setStyle("-fx-underline: true"));
        tombolSertifikasi.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelSertifikasi.setStyle("-fx-underline: false"));
        tombolMateriPelatihan.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelMateriPelatihan.setStyle("-fx-underline: true"));
        tombolMateriPelatihan.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelMateriPelatihan.setStyle("-fx-underline: false"));
        tombolKalenderInteraktif.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelKalenderInteraktif.setStyle("-fx-underline: true"));
        tombolKalenderInteraktif.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelKalenderInteraktif.setStyle("-fx-underline: false"));
        kelasVirtualButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> kelasVirtualButton.setStyle("-fx-background-color: #527aff"));
        kelasVirtualButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> kelasVirtualButton.setStyle("-fx-background-color: #3362ff"));
        tombolChatBox.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> labelChatBox.setStyle("-fx-underline: true"));
        tombolChatBox.addEventHandler(MouseEvent.MOUSE_EXITED, event -> labelChatBox.setStyle("-fx-underline: false"));

        // Check user role on initialization
        checkUserRole();
    }

    private void checkUserRole() {
        String currentUserRole = getCurrentUserRole();
        if ("Guest".equals(currentUserRole)) {
            // For guest users, disable buttons or show access denied alerts as needed
            disableGuestAccess();
        } else if ("Anggota".equals(currentUserRole)) {
            // For anggota users, enable appropriate access
            enableAnggotaAccess();
        }
    }

    private String getCurrentUserRole() {
        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail == null) {
            return "guest"; // Default to guest if user is not logged in
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(DATA_USER_FILE_PATH))) {
                Gson gson = new Gson();
                JsonArray usersArray = gson.fromJson(reader, JsonArray.class);

                for (int i = 0; i < usersArray.size(); i++) {
                    JsonObject userObject = usersArray.get(i).getAsJsonObject();
                    String email = userObject.get("email").getAsString();
                    if (email.equals(currentUserEmail)) {
                        return userObject.get("role").getAsString(); // Return user's role if email matches
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "guest"; // Default to guest if user not found in JSON file
        }
    }

    public String getCurrentUserEmail() {
        // Example: Assuming AccountModel.getInstance() returns the singleton instance
        // and getCurrentUser() returns an object with user details.
        UserAcount currentUser = AccountModel.getInstance().getAccount().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail(); // Assuming getEmail() returns the email address
        } else {
            return null; // Handle case where no user is logged in
        }
    }

    private void disableGuestAccess() {
        // Disable buttons or show access denied alerts for guest users
        tombolSertifikasi.setDisable(true);
        tombolMateriPelatihan.setDisable(true);
        tombolKalenderInteraktif.setDisable(true);
        tombolChatBox.setDisable(true);
        kelasVirtualButton.setDisable(true);
    }

    private void enableAnggotaAccess() {
        // Enable buttons for anggota users
        tombolSertifikasi.setDisable(false);
        tombolMateriPelatihan.setDisable(false);
        tombolKalenderInteraktif.setDisable(false);
        tombolChatBox.setDisable(false);
        kelasVirtualButton.setDisable(false);
    }

    public void handleMulaiDariSiniAction() {
        scrollPane.setVvalue(scrollPane.getVvalue() + 0.8);
    }

    public void handleSertifikasiAction() {
        if (getCurrentUserRole().equals("Guest")) {
            showAlert("Error", "Access Denied", "Please log in to access this feature.");
        } else {
            AccountModel.getInstance().getSessionManager().removeStage((Stage) tombolSertifikasi.getScene().getWindow());
            AccountModel.getInstance().getSessionManager().getSertifikasiView();
        }
    }

    public void handleMateriPelatihanAction() {
        AccountModel.getInstance().getSessionManager().removeStage((Stage) tombolMateriPelatihan.getScene().getWindow());
        AccountModel.getInstance().getSessionManager().getMateriPelatihanView();
    }

    public void handleKalenderInteraktifAction() {
        AccountModel.getInstance().getSessionManager().showKalender();
    }

    public void handleChatboxAction() {
        AccountModel.getInstance().getSessionManager().showChatBox();
    }

    public void handleKelasVirtualAction() {
        AccountModel.getInstance().getSessionManager().showKelasVirtual();
    }

    public void handleKeluarAction() throws IOException {
        Stage stage = (Stage) tombolKeluar.getScene().getWindow();
        AccountModel.getInstance().getSessionManager().removeStage(stage);
        AccountModel.getInstance().getAccount().removeUserAccount();
        AccountModel.getInstance().getSessionManager().showLogin();
    }

    public void handleKeluarActionHover() {
        rectangleKeluar.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> rectangleKeluar.setStyle("-fx-background-color: #ff8d7b"));
        rectangleKeluar.addEventHandler(MouseEvent.MOUSE_EXITED, event -> rectangleKeluar.setStyle("-fx-background-color: #ff2200"));
        tombolKeluar.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> tombolKeluar.setStyle("-fx-background-color:#ff8d7b"));
        tombolKeluar.addEventHandler(MouseEvent.MOUSE_EXITED, event -> tombolKeluar.setStyle("-fx-background-color: #ff2200"));
    }

    public void handleMateriDasar1Action() {
        // Implement action for starting from here
        System.out.println("Mulai dari sini button clicked!");
    }

    // Method to handle purchase action
    public void handleBeliMateriDasar() {
        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail == null) {
            showAlert("Error", "User not logged in", "Please log in to make a purchase.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_USER_FILE_PATH))) {
            Gson gson = new Gson();
            JsonArray usersArray = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < usersArray.size(); i++) {
                JsonObject userObject = usersArray.get(i).getAsJsonObject();
                String email = userObject.get("email").getAsString();
                if (email.equals(currentUserEmail)) {
                    userObject.addProperty("role", "Anggota"); // Update role to anggota
                    break;
                }
            }

            try (FileWriter writer = new FileWriter(DATA_USER_FILE_PATH)) {
                gson.toJson(usersArray, writer);
            }

            showAlert("Success", "Purchase Successful", "You have successfully purchased the Materi Dasar. Your role has been updated to Anggota.");
            checkUserRole(); // Refresh user role

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "File Error", "An error occurred while processing the user data.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
