package com.sunRiseBekary.pos.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UILoader {

    public static void loadUiDashBoard(AnchorPane anchorPane, String location) throws IOException {

        /**----------------------------------FULL CODE------------------------------------------
         URL resource = getClass().getResource("../view/" + location + ".fxml");
         Parent load = FXMLLoader.load(resource);
         anchorPane.getChildren().clear();
         anchorPane.getChildren().add(load);
         */

        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(FXMLLoader.load(UILoader.class.getResource("../view/" + location + ".fxml")));
    }

    public static void loadUiHalfDashBoard(AnchorPane anchorPane, String location) throws IOException {

        /**----------------------------------FULL CODE------------------------------------------
         URL resource = getClass().getResource("../view/" + location + ".fxml");
         Parent load = FXMLLoader.load(resource);
         anchorPane.getChildren().clear();
         anchorPane.getChildren().add(load);
         */

        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(FXMLLoader.load(UILoader.class.getResource("../view/" + location + ".fxml")));
    }

    public static void CloseStage(AnchorPane anchorPane) throws IOException {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    public static void BtnLogOut(AnchorPane anchorPane, String location) throws IOException {

        /**----------------------------------FULL CODE------------------------------------------
         URL resource = UILoader.class.getResource("/view/" + location + ".fxml");
         Parent load = FXMLLoader.load(resource);
         Stage window = (Stage) anchorPane.getScene().getWindow();
         window.setScene(new Scene(load));
         */

        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(UILoader.class.getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public static void aboutOnAction(String location) throws IOException {

        /**----------------------------------FULL CODE------------------------------------------
         URL resource = getClass().getResource("../view/" + location + ".fxml");
         Parent load = FXMLLoader.load(resource);
         Stage stage = new Stage();
         stage.setScene(new Scene(load));
         stage.centerOnScreen();
         stage.setResizable(true);
         stage.show();
         */

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(UILoader.class.getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.show();
    }

    public static void LoginOnAction(AnchorPane anchorPane, String location) throws IOException, SQLException {
        /**----------------------------------FULL CODE------------------------------------------
         URL resource = UILoader.class.getResource("/view/" + location + ".fxml");
         Parent load = FXMLLoader.load(resource);
         Stage window = (Stage) anchorPane.getScene().getWindow();
         window.setScene(new Scene(load));
         */

        Stage window = (Stage) anchorPane.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(UILoader.class.getResource("/com/sunRiseBekary/pos/view/" + location + ".fxml"))));
    }
}