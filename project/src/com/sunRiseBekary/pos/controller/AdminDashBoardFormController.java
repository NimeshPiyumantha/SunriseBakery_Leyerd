package com.sunRiseBekary.pos.controller;

import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.QueryBO;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.sunRiseBekary.pos.util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminDashBoardFormController implements Initializable {
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Custom);

    public AnchorPane AdminDashBoardContext;
    public AnchorPane HomeAnchorPane;
    public Label LblDate;
    public AnchorPane AnchorPaneOptionSelect1;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView imgCashier;
    public ImageView imgItems;
    public ImageView imgReport;
    public ImageView imgDetails;
    public Label lblItem;
    public Label lblCustomer;
    public Label lblOrders;
    public Label lblSoldItems;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), AdminDashBoardContext);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();


        //--------------------------------Label-------------------------------//
        try {
            lblItem.setText(String.valueOf(queryBO.getItem()));
            lblCustomer.setText(String.valueOf(queryBO.getCustomer()));
            lblOrders.setText(String.valueOf(queryBO.getSumSales()));
            lblSoldItems.setText(String.valueOf(queryBO.SoldItems()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //--------------------------------SET CLOCK NEW THREAD & DATALOADER ----------------------------//
        Thread clock = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd \t HH:mm:ss a");
                        Date date = new Date();
                        String myString = formatter.format(date);
                        Platform.runLater(() -> {
                            LblDate.setText(myString);
                        });
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        clock.start();
    }

    //--------------------------------UI Loader-------------------------------//
    public void HomeOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.loadUiDashBoard(HomeAnchorPane, "AdminHomeForm");
    }

    public void btnAddCashierOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(HomeAnchorPane, "ManageCashierForm");
    }

    public void btnAddItemOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(HomeAnchorPane, "ManageItemForm");
    }

    public void btnSystemReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(HomeAnchorPane, "SystemReportForm");
    }

    public void btnViewDetailsOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(HomeAnchorPane, "ViewDetailForm");
    }

    public void BtnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.BtnLogOut(AdminDashBoardContext, "LoginForm");
    }

    //--------------------------------UI Controller------------------//
    public void BtnMinimizeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void BtnCloseOnAction(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void BtnRestoreDownOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint("");
        stage.setResizable(true);
    }

    public void aboutOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.aboutOnAction("AboutForm");
    }

    //--------------------------------UI Animation-------------------------------//
    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        AdminHomeFormController.AdminMousePlayEnterAnimation(mouseEvent, lblMenu, lblDescription);
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome To Admin Dashboard");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
}
