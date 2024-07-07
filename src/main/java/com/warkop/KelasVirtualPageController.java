package com.warkop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class KelasVirtualPageController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Rectangle backgroundRect1;

    @FXML
    private Rectangle backgroundRect2;

    @FXML
    private Rectangle mainRect;

    @FXML
    private Label gabungKelasLabel;

    @FXML
    private Rectangle topBarRect;

    @FXML
    private Rectangle bottomBarRect;

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    @FXML
    private ImageView micImageView;

    @FXML
    private ImageView videoCamImageView;

    @FXML
    private ImageView hangUpImageView;

    @FXML
    private Button kelasDasarButton;

    @FXML
    private Button kelasMenengahButton;

    @FXML
    private Button kelasLanjutButton;

    public void initialize() {
        // Initialization code if needed
    }

    @FXML
    private void handleKelasDasarButtonClick(ActionEvent event) {
        
    }

    @FXML
    private void handleKelasMenengahButtonClick(ActionEvent event) {
        // Handle Kelas Menengah button click
    }

    @FXML
    private void handleKelasLanjutButtonClick(ActionEvent event) {
        // Handle Kelas Lanjut button click
    }
}
