package com.sunRiseBekary.pos.dao.custom.Impl;

import com.sunRiseBekary.pos.dao.custom.QueryDAO;
import com.sunRiseBekary.pos.entity.CustomEntity;
import com.sunRiseBekary.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class QueryDAOImpl implements QueryDAO {
    @Override
    public int getItem() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT COUNT(ItemCode) FROM Item");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public int getCustomer() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT COUNT(CustomerID) FROM Customer");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public ArrayList<CustomEntity> SoldItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT ItemCode,ItemName,sum(OrderQty),unitPrice FROM `Order Details` GROUP BY  ItemCode ORDER BY sum(OrderQty) DESC");
        ArrayList<CustomEntity> soldItem = new ArrayList();
        while (rst.next()) {
            soldItem.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4)));
        }
        return soldItem;
    }


    @Override
    public int getSumSales() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT count(OrderID) FROM `Order`");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public int SoldItems() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT sum(OrderQty) FROM `Order Details`");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public ArrayList<CustomEntity> LeastSoldItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT ItemCode,ItemName,sum(OrderQty),unitPrice FROM `Order Details` GROUP BY  ItemCode ORDER BY sum(OrderQty) ASC limit 1 ");
        ArrayList<CustomEntity> leastSoldItem = new ArrayList();
        while (rst.next()) {
            leastSoldItem.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4)));
        }
        return leastSoldItem;
    }

    @Override
    public ArrayList<CustomEntity> MostSoldItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT ItemCode,ItemName,sum(OrderQty),unitPrice FROM `Order Details` GROUP BY  ItemCode ORDER BY sum(OrderQty) DESC limit 1 ");
        ArrayList<CustomEntity> mostSoldItem = new ArrayList();
        while (rst.next()) {
            mostSoldItem.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4)));
        }
        return mostSoldItem;
    }

    @Override
    public ArrayList<CustomEntity> loadDailyIncomeReport() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT `Order`.orderDate,sum(`Order Details`.OrderQty),count(`Order`.OrderID),sum(`Order Details`.Total) FROM `Order` INNER JOIN `Order Details` ON `Order`.OrderID = `Order Details`.OrderID GROUP BY OrderDate");
        ArrayList<CustomEntity> dailyIncomeReport = new ArrayList();
        while (rst.next()) {
            dailyIncomeReport.add(new CustomEntity(rst.getString(1), rst.getInt(2), rst.getInt(3), rst.getDouble(4)));
        }
        return dailyIncomeReport;
    }

    @Override
    public ArrayList<CustomEntity> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT (MONTHNAME(OrderDate)) ,sum(`Order Details`.OrderQty),count(`Order`.OrderID),sum(`Order Details`.Total)  FROM `Order` INNER JOIN `Order Details` ON `Order`.OrderID = `Order Details`.OrderID GROUP BY extract(MONTH FROM(OrderDate))");
        ArrayList<CustomEntity> monthlyIncomeReport = new ArrayList();
        while (rst.next()) {
            monthlyIncomeReport.add(new CustomEntity(rst.getString(1), rst.getInt(2), rst.getInt(3), rst.getDouble(4)));
        }
        return monthlyIncomeReport;
    }

    @Override
    public ArrayList<CustomEntity> loadAnnuallyIncomeReport() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT (YEAR(OrderDate)) ,sum(`Order Details`.OrderQty),count(`Order`.OrderID),sum(`Order Details`.Total)  FROM `Order` INNER JOIN `Order Details` ON `Order`.OrderID = `Order Details`.OrderID GROUP BY extract(YEAR FROM(OrderDate))");
        ArrayList<CustomEntity> annuallyIncomeReport = new ArrayList();
        while (rst.next()) {
            annuallyIncomeReport.add(new CustomEntity(rst.getString(1), rst.getInt(2), rst.getInt(3), rst.getDouble(4)));
        }
        return annuallyIncomeReport;
    }

}
