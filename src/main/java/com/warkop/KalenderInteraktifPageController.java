package com.warkop;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KalenderInteraktifPageController {

    @FXML
    private Rectangle ktk1;
    @FXML
    private Rectangle ktk2;
    @FXML
    private Rectangle ktk3;
    @FXML
    private Rectangle ktk4;
    @FXML
    private Rectangle ktk5;
    @FXML
    private Rectangle ktk6;
    @FXML
    private Rectangle ktk7;

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;
    @FXML
    private Button b7;

    @FXML
    private ImageView r1;
    @FXML
    private ImageView r2;
    @FXML
    private ImageView r3;
    @FXML
    private ImageView r4;
    @FXML
    private ImageView r5;
    @FXML
    private ImageView r6;
    @FXML
    private ImageView r7;

    @FXML
    private ImageView rrr1;
    @FXML
    private ImageView rrr2;
    @FXML
    private ImageView rrr3;
    @FXML
    private ImageView rrr4;
    @FXML
    private ImageView rrr5;
    @FXML
    private ImageView rrr6;
    @FXML
    private ImageView rrr7;

    @FXML
    private ImageView rr1;
    @FXML
    private ImageView rr2;
    @FXML
    private ImageView rr3;
    @FXML
    private ImageView rr4;
    @FXML
    private ImageView rr5;
    @FXML
    private ImageView rr6;
    @FXML
    private ImageView rr7;

    private ImageView[] r;
    private ImageView[] rr;
    private ImageView[] rrr;
    private Button[] buttons;
    private int[] counters;

    private static final String DATA_USER_JSON_PATH = "src/main/resources/DATABASE/DataUser.json";
    private String userId;

    public void initialize() {
        b1.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> ktk1.setStyle("-fx-fill: #6ae7df"));
        b1.addEventHandler(MouseEvent.MOUSE_EXITED, event -> ktk1.setStyle("-fx-fill: #85fff7"));
        b2.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> ktk2.setStyle("-fx-fill: #6ae7df"));
        b2.addEventHandler(MouseEvent.MOUSE_EXITED, event -> ktk2.setStyle("-fx-fill: #85fff7"));
        b3.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> ktk3.setStyle("-fx-fill: #6ae7df"));
        b3.addEventHandler(MouseEvent.MOUSE_EXITED, event -> ktk3.setStyle("-fx-fill: #85fff7"));
        b4.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> ktk4.setStyle("-fx-fill: #6ae7df"));
        b4.addEventHandler(MouseEvent.MOUSE_EXITED, event -> ktk4.setStyle("-fx-fill: #85fff7"));
        b5.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> ktk5.setStyle("-fx-fill: #6ae7df"));
        b5.addEventHandler(MouseEvent.MOUSE_EXITED, event -> ktk5.setStyle("-fx-fill: #85fff7"));
        b6.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> ktk6.setStyle("-fx-fill: #6ae7df"));
        b6.addEventHandler(MouseEvent.MOUSE_EXITED, event -> ktk6.setStyle("-fx-fill: #85fff7"));
        b7.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> ktk7.setStyle("-fx-fill: #6ae7df"));
        b7.addEventHandler(MouseEvent.MOUSE_EXITED, event -> ktk7.setStyle("-fx-fill: #85fff7"));

        r = new ImageView[]{r1, r2, r3, r4, r5, r6, r7};
        rr = new ImageView[]{rr1, rr2, rr3, rr4, rr5, rr6, rr7};
        rrr = new ImageView[]{rrr1, rrr2, rrr3, rrr4, rrr5, rrr6, rrr7};
        buttons = new Button[]{b1, b2, b3, b4, b5, b6, b7};
        counters = new int[7];
    }

    public void setUserId(String userId) {
        this.userId = userId;
        loadCountersFromJson(); // Load counters when userId is set
    }

    public void handleButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        int index = Integer.parseInt(button.getId().substring(1)) - 1;
        counters[index]++;
        if (counters[index] > 3) {
            counters[index] = 0; // reset the counter
            r[index].setVisible(false);
            rr[index].setVisible(false);
            rrr[index].setVisible(false);
        } else if (counters[index] == 1) {
            r[index].setVisible(true);
            rr[index].setVisible(false);
            rrr[index].setVisible(false);
        } else if (counters[index] == 2) {
            r[index].setVisible(false);
            rr[index].setVisible(true);
            rrr[index].setVisible(false);
        } else if (counters[index] == 3) {
            r[index].setVisible(false);
            rr[index].setVisible(false);
            rrr[index].setVisible(true);
        }

        saveCountersToJson(); // Save counters after each button click
    }

    public void loadCountersFromJson() {
        try (FileReader reader = new FileReader(DATA_USER_JSON_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String jsonUserId = jsonObject.get("id").getAsString();
                if (jsonUserId.equals(userId)) {
                    JsonArray jsonCounters = jsonObject.getAsJsonArray("counters");
                    for (int j = 0; j < counters.length && j < jsonCounters.size(); j++) {
                        counters[j] = jsonCounters.get(j).getAsInt();
                        updateImageViewVisibility(j, counters[j]);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCountersToJson() {
    try {
        String jsonContent = new String(Files.readAllBytes(Paths.get(DATA_USER_JSON_PATH)));
        JsonArray jsonArray = JsonParser.parseString(jsonContent).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            JsonElement jsonIdElement = jsonObject.get("id");
            if (jsonIdElement != null && !jsonIdElement.isJsonNull()) {
                String jsonUserId = jsonIdElement.getAsString();
                if (jsonUserId.equals(userId)) {
                    JsonArray jsonCounters = new JsonArray();
                    for (int counter : counters) {
                        jsonCounters.add(counter);
                    }
                    jsonObject.add("counters", jsonCounters);

                    Gson gson = new Gson();
                    String updatedJson = gson.toJson(jsonArray);
                    FileWriter writer = new FileWriter(DATA_USER_JSON_PATH);
                    writer.write(updatedJson);
                    writer.close();
                    return; // Exit method once update is complete
                }
            } else {
                System.err.println("JSON object at index " + i + " does not have an 'id' field.");
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    public void updateImageViewVisibility(int index, int counter) {
        if (counter == 0) {
            r[index].setVisible(false);
            rr[index].setVisible(false);
            rrr[index].setVisible(false);
        } else if (counter == 1) {
            r[index].setVisible(true);
            rr[index].setVisible(false);
            rrr[index].setVisible(false);
        } else if (counter == 2) {
            r[index].setVisible(false);
            rr[index].setVisible(true);
            rrr[index].setVisible(false);
        } else if (counter == 3) {
            r[index].setVisible(false);
            rr[index].setVisible(false);
            rrr[index].setVisible(true);
        }
    }
}

