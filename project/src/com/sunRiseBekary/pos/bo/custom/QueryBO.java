package com.sunRiseBekary.pos.bo.custom;

import com.sunRiseBekary.pos.bo.SuperBO;
import com.sunRiseBekary.pos.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public interface QueryBO extends SuperBO {
    int getSumSales() throws SQLException, ClassNotFoundException;

    int SoldItems() throws SQLException, ClassNotFoundException;

    int getItem() throws SQLException, ClassNotFoundException;

    int getCustomer() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> SoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> LeastSoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> MostSoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> loadDailyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> loadAnnuallyIncomeReport() throws SQLException, ClassNotFoundException;

}
