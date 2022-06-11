package com.sunRiseBekary.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.UserBO;
import com.sunRiseBekary.pos.dto.LoginDTO;
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

public class AdminFormController implements Initializable {
    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.User);

    public AnchorPane AdminContext;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Label lblHide;
    public JFXButton btnLogin;
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

    public void LoginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //--------------------------------UI Login Admin form in Database-------------------------------//

        ArrayList<LoginDTO> loginDTOS = userBO.getAllUsers();
        attempts++;
        loginDTOS.forEach(e -> {
            if (attempts <= 3) {
                if (e.getUserName().equals(txtUserName.getText()) && e.getPassword().equals(txtPassword.getText())) {
                    try {
                        UILoader.LoginOnAction(AdminContext, "AdminDashBoardForm");
                    } catch (IOException | SQLException ex) {
                        ex.printStackTrace();
                    }
                    NotificationController.LoginSuccessfulNotification("Admin");
                } else {
                    //try again
                    NotificationController.LoginUnSuccessfulNotification("Admin", attempts);
                }
            } else {
                txtUserName.setEditable(false);
                txtPassword.setEditable(false);
                imgLock.setVisible(true);
                NotificationController.EmptyDataNotification("Account is Temporarily Disabled or You Did not Sign in Correctly.");
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
