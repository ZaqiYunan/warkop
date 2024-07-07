package com.warkop;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.FileReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import java.io.File;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminPageController implements Initializable {

    @FXML
    private Label labelNamaAkun;

    @FXML
    private TableView<UserTable> tableView;

    @FXML
    private TableColumn<UserTable, String> idColumn;

    @FXML
    private TableColumn<UserTable, String> roleColumn;

    @FXML
    private TableColumn<UserTable, String> namaColumn;

    @FXML
    private TableColumn<UserTable, String> emailColumn;

    
    @FXML
    private Button tombolKeluar;

    private static final String DATA_USER_FILE_PATH = "src/main/resources/DATABASE/DataUser.json";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelNamaAkun.setText(AccountModel.getInstance().getAccount().getNamaLengkap());
        try {
            loadUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupTableCollumn();
    }

    private void loadUserData() throws Exception {
        File jsonFile = new File(DATA_USER_FILE_PATH);
            
        if (jsonFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    String jsonNamaLengkap = jsonObject.get("namaLengkap").getAsString();
                    String jsonUserID = jsonObject.get("id").getAsString();
                    String jsonRole = jsonObject.get("role").getAsString();
                    String jsonEmail = jsonObject.get("email").getAsString();
                    UserTable user = new UserTable(jsonUserID, jsonRole, jsonNamaLengkap, jsonEmail);
                    tableView.getItems().add(user);
                }
            }
        }
    }

    public void setupTableCollumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("namaLengkap"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    

    

    public void handleKeluarAction() throws IOException {
        Stage stage = (Stage) tombolKeluar.getScene().getWindow();
        AccountModel.getInstance().getSessionManager().removeStage(stage);
        AccountModel.getInstance().getAccount().removeUserAccount();
        AccountModel.getInstance().getSessionManager().showLogin();
    }

   

   

    
}