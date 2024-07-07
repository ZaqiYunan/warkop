package com.warkop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private TableColumn<UserTable, Void> deleteColumn;

    @FXML
    private Button tombolKeluar;
    @FXML
    private Label labelKeluar;

    private static final String DATA_USER_FILE_PATH = "src/main/resources/DATABASE/DataUser.json";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tombolKeluar.setStyle("-fx-background-color: #ff2200");
        tombolKeluar.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> tombolKeluar.setStyle("-fx-background-color: #ff8d7b"));
        tombolKeluar.addEventHandler(MouseEvent.MOUSE_EXITED, event -> tombolKeluar.setStyle("-fx-background-color: #ff2200"));
        labelNamaAkun.setText("Admin");
        try {
            loadUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupTableColumns();
        addDeleteButtonToTable();
    }

    private void loadUserData() throws Exception {
        File jsonFile = new File(DATA_USER_FILE_PATH);

        if (jsonFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    String jsonNamaLengkap = jsonObject.get("namaLengkap").getAsString();
                    String jsonId = jsonObject.get("id").getAsString();
                    String jsonRole = jsonObject.get("role").getAsString();
                    String jsonEmail = jsonObject.get("email").getAsString();
                    UserTable user = new UserTable(jsonId, jsonRole, jsonNamaLengkap, jsonEmail);
                    tableView.getItems().add(user);
                }
            }
        }
    }

    public void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("namaLengkap"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void addDeleteButtonToTable() {
        Callback<TableColumn<UserTable, Void>, TableCell<UserTable, Void>> cellFactory = new Callback<TableColumn<UserTable, Void>, TableCell<UserTable, Void>>() {
            @Override
            public TableCell<UserTable, Void> call(final TableColumn<UserTable, Void> param) {
                final TableCell<UserTable, Void> cell = new TableCell<UserTable, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            UserTable data = getTableView().getItems().get(getIndex());
                            deleteUser(data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        deleteColumn.setCellFactory(cellFactory);
    }

    private void deleteUser(UserTable user) {
        tableView.getItems().remove(user);
        try {
            deleteUserFromJson(user.getUserID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserFromJson(String userId) throws IOException {
        File jsonFile = new File(DATA_USER_FILE_PATH);

        if (jsonFile.exists()) {
            JsonArray jsonArray;
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
                jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                if (jsonObject.get("id").getAsString().equals(userId)) {
                    jsonArray.remove(i);
                    break;
                }
            }

            try (FileWriter fileWriter = new FileWriter(DATA_USER_FILE_PATH)) {
                fileWriter.write(jsonArray.toString());
            }
        }
    }

    public void handleKeluarAction() throws IOException {
        Stage stage = (Stage) tombolKeluar.getScene().getWindow();
        AccountModel.getInstance().getSessionManager().removeStage(stage);
        AccountModel.getInstance().getSessionManager().showLogin();
    }
}
    