package com.sunRiseBekary.pos.dao.custom;

import com.sunRiseBekary.pos.dao.SuperDAO;
import com.sunRiseBekary.pos.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public interface QueryDAO extends SuperDAO {
    int getSumSales() throws SQLException, ClassNotFoundException;

    int SoldItems() throws SQLException, ClassNotFoundException;

    int getItem() throws SQLException, ClassNotFoundException;

    int getCustomer() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> SoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> LeastSoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> MostSoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> loadDailyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> loadAnnuallyIncomeReport() throws SQLException, ClassNotFoundException;

}
