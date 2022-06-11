package com.sunRiseBekary.pos.controller;

import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.CashierBO;
import com.sunRiseBekary.pos.dto.CashierDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.view.tm.CashierTM;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CashierDetailFormController implements Initializable {

    private final CashierBO cashierBO = (CashierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CASHIER);

    public TableView<CashierTM> tblCashierView;
    public TextField txtCashierID;


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
        //-------------------------------Table Load---------------------------------//

        tblCashierView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("CashierID"));
        tblCashierView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("CashierName"));
        tblCashierView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("CashierPsw"));
        tblCashierView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("CashierAddress"));
        tblCashierView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("CashierCNumber"));
        tblCashierView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("CashierNIC"));
        tblCashierView.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("CashierSalary"));
        tblCashierView.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("CashierDescription"));
        tblCashierView.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("CashierAddDate"));
        tblCashierView.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("CashierAddTime"));


        try {
            loadAllCashier();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    //--------------------------------Load Cashier-------------------------------//
    private void loadAllCashier() throws SQLException, ClassNotFoundException {
        tblCashierView.getItems().clear();
        /*Get all item*/
        try {
            ArrayList<CashierDTO> allCashier = cashierBO.getAllCashier();
            for (CashierDTO cashierDTO : allCashier) {
                tblCashierView.getItems().add(new CashierTM(cashierDTO.getCashierID(), cashierDTO.getCashierName(), cashierDTO.getCashierPsw(), cashierDTO.getCashierAddress(), cashierDTO.getCashierCNumber(), cashierDTO.getCashierNIC(), cashierDTO.getCashierSalary(), cashierDTO.getCashierDescription(), cashierDTO.getCashierAddDate(), cashierDTO.getCashierAddTime()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    //------------------------Order Exit----------------------//
    private boolean cashierExit(String id) throws SQLException, ClassNotFoundException {
        return cashierBO.cashierExist(id);
    }

    //--------------------------------Search Cashier-------------------------------//
    public void CashierSearchOnAction() throws SQLException, ClassNotFoundException {
        if (txtCashierID.getText().trim().isEmpty()) {
            tblCashierView.getItems().clear();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllCashier();
        } else {
            if (cashierExit(txtCashierID.getText())) {
                tblCashierView.getItems().clear();
                ArrayList<CashierDTO> arrayList = cashierBO.cashierSearchId(txtCashierID.getText());
                if (arrayList != null) {
                    for (CashierDTO cashierDTO : arrayList) {
                        tblCashierView.getItems().add(new CashierTM(cashierDTO.getCashierID(), cashierDTO.getCashierName(), cashierDTO.getCashierPsw(), cashierDTO.getCashierAddress(), cashierDTO.getCashierCNumber(), cashierDTO.getCashierNIC(), cashierDTO.getCashierSalary(), cashierDTO.getCashierDescription(), cashierDTO.getCashierAddDate(), cashierDTO.getCashierAddTime()));
                    }
                }
            } else {
                tblCashierView.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }
}

