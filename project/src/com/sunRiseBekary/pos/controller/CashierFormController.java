package com.sunRiseBekary.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.CashierBO;
import com.sunRiseBekary.pos.dto.CashierDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.util.UILoader;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CashierFormController implements Initializable {
    private final CashierBO cashierBO = (CashierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CASHIER);

    public AnchorPane CashierContext;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public JFXButton btnLogin;
    public Label lblHide;
    public ImageView imgLock;
    int attempts = 0;

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
        imgLock.setVisible(false);
    }

    public void LoginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        //--------------------------------UI Login Cashier form in Database-------------------------------//
        ArrayList<CashierDTO> loginDTOS = cashierBO.getAllCashier();
        attempts++;
        loginDTOS.forEach(e -> {
            if (attempts <= 3) {
                if (e.getCashierName().equals(txtUserName.getText()) && e.getCashierPsw().equals(txtPassword.getText())) {
                    try {
                        UILoader.LoginOnAction(CashierContext, "CashierDashBoardForm");
                    } catch (IOException | SQLException ex) {
                        ex.printStackTrace();
                    }
                    NotificationController.LoginSuccessfulNotification("Cashier");
                } else {
                    //try again
                    NotificationController.LoginUnSuccessfulNotification("Cashier", attempts);
                }
            } else {
                txtUserName.setEditable(false);
                txtPassword.setEditable(false);
                imgLock.setVisible(true);
                NotificationController.EmptyDataNotification("Account is Temporarily Disabled or You Did not Sign in Correctly");
            }
        });
    }

    //------Show Password-----
    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("com/sunRiseBekary/pos/view/assets/icons/show.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPassword.setPromptText(txtPassword.getText());
        txtPassword.setText("");
        txtPassword.setDisable(true);
        txtPassword.requestFocus();
    }

    //------Hide Password-----
    public void hidePasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("com/sunRiseBekary/pos/view/assets/icons/hide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPassword.setText(txtPassword.getPromptText());
        txtPassword.setPromptText("");
        txtPassword.setDisable(false);
    }
}
