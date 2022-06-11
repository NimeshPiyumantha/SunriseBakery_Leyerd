package com.sunRiseBekary.pos.controller;

import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.QueryBO;
import com.sunRiseBekary.pos.dto.CustomDTO;
import com.sunRiseBekary.pos.util.UILoader;
import com.sunRiseBekary.pos.view.tm.CustomTM;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SystemReportFormController implements Initializable {
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Custom);

    public AnchorPane SystemReportFormContext;
    public AnchorPane SystemReportButtonContext;
    public AnchorPane ReportTableContext;
    public Label lblItem;
    public Label lblCustomer;
    public Label lblOrders;
    public Label lblSoldItems;
    public TableView<CustomTM> tblMostSoldItem;
    public TableColumn<? extends Object, ? extends Object> colMCode;
    public TableColumn<? extends Object, ? extends Object> colMName;
    public TableColumn<? extends Object, ? extends Object> colMOrderQty;
    public TableColumn<? extends Object, ? extends Object> colMUnitPrice;
    public TableView<CustomTM> tblLeastSoldItem;
    public TableColumn<? extends Object, ? extends Object> colICode;
    public TableColumn<? extends Object, ? extends Object> colIName;
    public TableColumn<? extends Object, ? extends Object> colIOrderQty;
    public TableColumn<? extends Object, ? extends Object> colIUnitPrice;
    public TableView<CustomTM> tblAllItem;
    public TableColumn<? extends Object, ? extends Object> colCode;
    public TableColumn<? extends Object, ? extends Object> colName;
    public TableColumn<? extends Object, ? extends Object> colSoldQty;
    public TableColumn<? extends Object, ? extends Object> colUnitPrice;

    //--------------------UI Loader------------------------//
    public void DailyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ReportTableContext, "DailySystemReportForm");
    }

    public void MonthlyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ReportTableContext, "MonthlySystemReportForm");
    }

    public void AnnuallyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(ReportTableContext, "AnnuallySystemReportForm");
    }

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

        //--------------------------------Labale----------------------//
        try {
            lblItem.setText(String.valueOf(queryBO.getItem()));
            lblCustomer.setText(String.valueOf(queryBO.getCustomer()));
            lblOrders.setText(String.valueOf(queryBO.getSumSales()));
            lblSoldItems.setText(String.valueOf(queryBO.SoldItems()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //-------------------------------Most Sold Item----------------------//
        tblMostSoldItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblMostSoldItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblMostSoldItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("itemSoldQty"));
        tblMostSoldItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ItemUnitPrice"));

        try {
            MostSoldItem();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //-----------------------------Least Sold Item-----------------------//
        tblLeastSoldItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblLeastSoldItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblLeastSoldItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("itemSoldQty"));
        tblLeastSoldItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ItemUnitPrice"));

        try {
            LeastSoldItem();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //----------------------------- Sold Item-----------------------//
        tblAllItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblAllItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblAllItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("itemSoldQty"));
        tblAllItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ItemUnitPrice"));

        try {
            SoldItem();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //---------------------Sold Item Sum------------------------//
    private void SoldItem() throws SQLException, ClassNotFoundException {
        tblAllItem.getItems().clear();
        try {
            /*Get all Lest Movable Items*/
            ArrayList<CustomDTO> allDetails = queryBO.SoldItem();
            for (CustomDTO items : allDetails) {
                tblAllItem.getItems().add(new CustomTM(items.getItemCode(), items.getItemName(), items.getItemSoldQty(), items.getItemUnitPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //---------------------Least Sold Item Sum------------------------//
    private void LeastSoldItem() throws SQLException, ClassNotFoundException {
        tblLeastSoldItem.getItems().clear();
        try {
            /*Get all Lest Movable Items*/
            ArrayList<CustomDTO> allLDetails = queryBO.LeastSoldItem();

            for (CustomDTO items : allLDetails) {
                tblLeastSoldItem.getItems().add(new CustomTM(items.getItemCode(), items.getItemName(), items.getItemSoldQty(), items.getItemUnitPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //---------------------Most Sold Item Sum------------------------//
    private void MostSoldItem() throws SQLException, ClassNotFoundException {
        tblMostSoldItem.getItems().clear();
        try {
            /*Get all Lest Movable Items*/
            ArrayList<CustomDTO> allMDetails = queryBO.MostSoldItem();
            for (CustomDTO items : allMDetails) {
                tblMostSoldItem.getItems().add(new CustomTM(items.getItemCode(), items.getItemName(), items.getItemSoldQty(), items.getItemUnitPrice()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
