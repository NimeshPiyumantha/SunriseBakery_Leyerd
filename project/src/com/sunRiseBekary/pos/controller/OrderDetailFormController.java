package com.sunRiseBekary.pos.controller;

import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.OrderDetailsBO;
import com.sunRiseBekary.pos.dto.OrderDetailsDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.view.tm.OrderDetailsTM;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderDetailFormController implements Initializable {
    private final OrderDetailsBO orderDetailsBO = (OrderDetailsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERDETAILS);

    public TableView<OrderDetailsTM> tblOrderDetail;
    public TextField txtOrderIdSearch;
    public TextField txtItemCode;
    public TextField txtCustomerId;


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

        tblOrderDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        tblOrderDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("CusId"));
        tblOrderDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblOrderDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblOrderDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        tblOrderDetail.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetail.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("Discount"));
        tblOrderDetail.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("Total"));

        try {
            loadAllOrderDetail();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllOrderDetail() throws SQLException, ClassNotFoundException {
        tblOrderDetail.getItems().clear();
        /*Get all customers*/
        try {
            ArrayList<OrderDetailsDTO> allOrderDetail = orderDetailsBO.getAllOrderDetail();
            for (OrderDetailsDTO orderDetailsDTO : allOrderDetail) {
                tblOrderDetail.getItems().add(new OrderDetailsTM(orderDetailsDTO.getOrderID(), orderDetailsDTO.getCusId(), orderDetailsDTO.getItemCode(), orderDetailsDTO.getItemName(), orderDetailsDTO.getOrderQty(), orderDetailsDTO.getUnitPrice(), orderDetailsDTO.getDiscount(), orderDetailsDTO.getTotal()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean orderExit(String id) throws SQLException, ClassNotFoundException {
        return orderDetailsBO.orderDetailExit(id);
    }

    //---------------------------Search Order Id-------------------------------//
    public void OrderIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtOrderIdSearch.getText().trim().isEmpty()) {
            tblOrderDetail.getItems().clear();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllOrderDetail();
        } else {
            if (orderExit(txtOrderIdSearch.getText())) {
                tblOrderDetail.getItems().clear();
                ArrayList<OrderDetailsDTO> arrayList = orderDetailsBO.OrderSearchId(txtOrderIdSearch.getText());
                if (arrayList != null) {
                    for (OrderDetailsDTO detailsDTOID : arrayList) {
                        tblOrderDetail.getItems().add(new OrderDetailsTM(detailsDTOID.getOrderID(), detailsDTOID.getCusId(), detailsDTOID.getItemCode(), detailsDTOID.getItemName(), detailsDTOID.getOrderQty(), detailsDTOID.getUnitPrice(), detailsDTOID.getDiscount(), detailsDTOID.getTotal()));
                    }
                }
            } else {
                tblOrderDetail.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }


    //---------------------------Search Item Code-------------------------------//
    public void ItemCodeSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtOrderIdSearch.getText().trim().isEmpty()) {
            tblOrderDetail.getItems().clear();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllOrderDetail();
        } else {
            if (orderExit(txtOrderIdSearch.getText())) {
                tblOrderDetail.getItems().clear();
                ArrayList<OrderDetailsDTO> iArrayList = orderDetailsBO.searchItemId(txtItemCode.getText());
                if (iArrayList != null) {
                    for (OrderDetailsDTO detailsDTOI : iArrayList) {
                        tblOrderDetail.getItems().add(new OrderDetailsTM(detailsDTOI.getOrderID(), detailsDTOI.getCusId(), detailsDTOI.getItemCode(), detailsDTOI.getItemName(), detailsDTOI.getOrderQty(), detailsDTOI.getUnitPrice(), detailsDTOI.getDiscount(), detailsDTOI.getTotal()));

                    }
                }
            } else {
                tblOrderDetail.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    //---------------------------Search Customer Id-------------------------------//
    public void CustomerIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtOrderIdSearch.getText().trim().isEmpty()) {
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllOrderDetail();
        } else {
            if (orderExit(txtOrderIdSearch.getText())) {
                tblOrderDetail.getItems().clear();
                ArrayList<OrderDetailsDTO> cArrayList = orderDetailsBO.searchCustomerId(txtCustomerId.getText());

                System.out.println(cArrayList);
                if (cArrayList != null) {
                    for (OrderDetailsDTO detailsDTOC : cArrayList) {
                        tblOrderDetail.getItems().add(new OrderDetailsTM(detailsDTOC.getOrderID(), detailsDTOC.getCusId(), detailsDTOC.getItemCode(), detailsDTOC.getItemName(), detailsDTOC.getOrderQty(), detailsDTOC.getUnitPrice(), detailsDTOC.getDiscount(), detailsDTOC.getTotal()));
                    }
                }
            } else {
                tblOrderDetail.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }
}
