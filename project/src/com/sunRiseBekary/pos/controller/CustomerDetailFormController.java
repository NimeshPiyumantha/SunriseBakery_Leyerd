package com.sunRiseBekary.pos.controller;

import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.CustomerBO;
import com.sunRiseBekary.pos.dto.CustomerDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.view.tm.CustomerTM;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerDetailFormController implements Initializable {
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public TableView<CustomerTM> tblCustomerDetail;
    public TextField txtCustomerID;


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

        tblCustomerDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        tblCustomerDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("CustomerTitle"));
        tblCustomerDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        tblCustomerDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("CustomerAddress"));
        tblCustomerDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("CustomerCity"));
        tblCustomerDetail.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("CustomerProvince"));
        tblCustomerDetail.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("CustomerPostCode"));
        tblCustomerDetail.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("CustomerAddDate"));
        tblCustomerDetail.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("CustomerAddTime"));

        try {
            loadAllCustomers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        tblCustomerDetail.getItems().clear();
        /*Get all Customer*/
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            for (CustomerDTO customerDTO : allCustomers) {
                tblCustomerDetail.getItems().add(new CustomerTM(customerDTO.getCustomerID(), customerDTO.getCustomerTitle(), customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerCity(), customerDTO.getCustomerProvince(), customerDTO.getCustomerPostCode(), customerDTO.getCustomerAddDate(), customerDTO.getCustomerAddTime()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean customerExit(String id) throws SQLException, ClassNotFoundException {
        return customerBO.customerExist(id);
    }

    //-------------------------------Customer Search--------------------------------//
    public void CustomerSearchOnAction() throws SQLException, ClassNotFoundException {
        if (txtCustomerID.getText().trim().isEmpty()) {
            tblCustomerDetail.getItems().clear();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllCustomers();
        } else {
            if (customerExit(txtCustomerID.getText())) {
                tblCustomerDetail.getItems().clear();
                ArrayList<CustomerDTO> arrayList = customerBO.customerSearchId(txtCustomerID.getText());
                if (arrayList != null) {
                    for (CustomerDTO customerDTO : arrayList) {
                        tblCustomerDetail.getItems().add(new CustomerTM(customerDTO.getCustomerID(), customerDTO.getCustomerTitle(), customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerCity(), customerDTO.getCustomerProvince(), customerDTO.getCustomerPostCode(), customerDTO.getCustomerAddDate(), customerDTO.getCustomerAddTime()));
                    }
                }
            } else {
                tblCustomerDetail.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }
}
