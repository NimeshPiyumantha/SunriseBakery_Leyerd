package com.sunRiseBekary.pos.controller;

import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.QueryBO;
import com.sunRiseBekary.pos.dto.CustomDTO;
import com.sunRiseBekary.pos.view.tm.CustomTM;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DailyIncomeFormController implements Initializable {
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Custom);

    public TableView<CustomTM> tblReport;
    public AnchorPane DReportTableContext;
    @FXML
    private AreaChart<?, ?> barChart;

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

        try {
            //----------Load AreaChart-------------//
            areaChart();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //----------Load Table-------------//
        tblReport.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblReport.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemSoldQty"));
        tblReport.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("orderCount"));
        tblReport.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("sumOfTotal"));
        monthlyIncomeLoad();
    }

    private void monthlyIncomeLoad() {
        try {
            ArrayList<CustomDTO> arrayList = queryBO.loadDailyIncomeReport();
            if (arrayList != null) {
                for (CustomDTO customEntity : arrayList) {
                    tblReport.getItems().add(new CustomTM(customEntity.getOrderDate(), customEntity.getItemSoldQty(), customEntity.getOrderCount(), customEntity.getSumOfTotal()));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void areaChart() throws SQLException, ClassNotFoundException {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Daily Income Chart");
        ArrayList<CustomDTO> arrayList = queryBO.loadDailyIncomeReport();

        String date = null;
        double total = 0;

        for (CustomDTO customEntity : arrayList) {

            date += customEntity.getOrderDate();
            total += (customEntity.getSumOfTotal());

            series1.getData().add(new XYChart.Data(customEntity.getOrderDate(), customEntity.getSumOfTotal()));
        }
        barChart.getData().addAll(series1);
    }
}