package com.sunRiseBekary.pos.controller;

import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.OrderBO;
import com.sunRiseBekary.pos.dto.OrderDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.view.tm.OrderTM;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersViewController implements Initializable {
    private final OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    public AnchorPane OrdersViewContext;
    public TableView<OrderTM> tblOrder;
    public TextField txtOrderId;
    public TextField txtCustomer;
    public TextField txtDate;


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

        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("OId"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ODate"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("OTime"));


        try {
            loadAllOrder();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllOrder() throws SQLException, ClassNotFoundException {
        tblOrder.getItems().clear();
        /*Get all customers*/
        try {
            ArrayList<OrderDTO> allOrder = orderBO.getAllOrder();
            for (OrderDTO orderDTO : allOrder) {
                tblOrder.getItems().add(new OrderTM(orderDTO.getOId(), orderDTO.getCustomerId(), orderDTO.getODate(), orderDTO.getOTime()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean OrdersExit(String id) throws SQLException, ClassNotFoundException {
        return orderBO.orderExit(id);
    }

    //---------------------Order Id Search------------------------//
    public void OrderIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtOrderId.getText().trim().isEmpty()) {
            tblOrder.getItems().clear();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllOrder();
        } else {
            if (OrdersExit(txtOrderId.getText())) {
                tblOrder.getItems().clear();
                ArrayList<OrderDTO> arrayList = orderBO.OrderSearchId(txtOrderId.getText());
                if (arrayList != null) {
                    for (OrderDTO orderDTO : arrayList) {
                        tblOrder.getItems().add(new OrderTM(orderDTO.getOId(), orderDTO.getCustomerId(), orderDTO.getODate(), orderDTO.getOTime()));
                    }
                }
            } else {
                tblOrder.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    //---------------------Customer Id Search------------------------//
    public void CustomerIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtOrderId.getText().trim().isEmpty()) {
            tblOrder.getItems().clear();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllOrder();
        } else {
            if (OrdersExit(txtOrderId.getText())) {
                tblOrder.getItems().clear();
                ArrayList<OrderDTO> cArrayList = orderBO.searchCustomerId(txtCustomer.getText());
                if (cArrayList != null) {
                    for (OrderDTO orderDTO : cArrayList) {
                        tblOrder.getItems().add(new OrderTM(orderDTO.getOId(), orderDTO.getCustomerId(), orderDTO.getODate(), orderDTO.getOTime()));
                    }
                }
            } else {
                tblOrder.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    //---------------------Date Id Search------------------------//
    public void DateSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtOrderId.getText().trim().isEmpty()) {
            tblOrder.getItems().clear();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllOrder();
        } else {
            if (OrdersExit(txtOrderId.getText())) {
                tblOrder.getItems().clear();
                ArrayList<OrderDTO> dArrayList = orderBO.searchDateId(txtDate.getText());
                if (dArrayList != null) {
                    for (OrderDTO orderDTO : dArrayList) {
                        tblOrder.getItems().add(new OrderTM(orderDTO.getOId(), orderDTO.getCustomerId(), orderDTO.getODate(), orderDTO.getOTime()));
                    }
                }
            } else {
                tblOrder.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }
}

