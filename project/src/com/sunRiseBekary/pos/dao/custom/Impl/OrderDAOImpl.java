package com.sunRiseBekary.pos.dao.custom.Impl;

import com.sunRiseBekary.pos.dao.custom.OrderDAO;
import com.sunRiseBekary.pos.entity.Order;
import com.sunRiseBekary.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order`");
        ArrayList<Order> allOrders = new ArrayList<>();
        while (rst.next()) {
            allOrders.add(new Order(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return allOrders;
    }

    @Override
    public boolean save(Order dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` (OrderID,CustomerID,OrderDate,OrderTime) VALUES (?,?,?,?)", dto.getOrderID(), dto.getCustomerID(), dto.getOrderDate(), dto.getOrderTime());
    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `Order` SET CustomerID=?, OrderDate=?,OrderTime=? WHERE OrderID=?", dto.getCustomerID(), dto.getOrderDate(), dto.getOrderTime(), dto.getOrderID());
    }

    @Override
    public Order search(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderID FROM `Order` WHERE OrderID=?", oid);
        if (rst.next()) {
            return new Order(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
        }
        return null;
    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT OrderID FROM `Order` WHERE OrderID=?", oid).next();
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Order` WHERE OrderID=?", oid);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderID FROM `Order` ORDER BY OrderID DESC LIMIT 1;");
        return rst.next() ? String.format("O-%03d", (Integer.parseInt(rst.getString("OrderID").replace("O-", "")) + 1)) : "O-001";
    }

    @Override
    public ArrayList<Order> searchId(String id) throws SQLException, ClassNotFoundException {
        ResultSet sOrder = CrudUtil.executeQuery("SELECT * FROM `Order` WHERE OrderID=?", id);
        ArrayList<Order> sAllOrder = new ArrayList<>();
        while (sOrder.next()) {
            sAllOrder.add(new Order(sOrder.getString(1), sOrder.getString(2), sOrder.getString(3), sOrder.getString(4)));
        }
        return sAllOrder;
    }

    @Override
    public ArrayList<Order> searchCustomerId(String id) throws SQLException, ClassNotFoundException {
        ResultSet cusOrder = CrudUtil.executeQuery("SELECT * FROM `order` WHERE CustomerID=?", id);
        ArrayList<Order> cAllOrder = new ArrayList<>();
        while (cusOrder.next()) {
            cAllOrder.add(new Order(cusOrder.getString(1), cusOrder.getString(2), cusOrder.getString(3), cusOrder.getString(4)));
        }
        return cAllOrder;
    }

    @Override
    public ArrayList<Order> searchDateId(String id) throws SQLException, ClassNotFoundException {
        ResultSet dateOrder = CrudUtil.executeQuery("SELECT * FROM `order` WHERE OrderDate=?", id);
        ArrayList<Order> dAllOrder = new ArrayList<>();
        while (dateOrder.next()) {
            dAllOrder.add(new Order(dateOrder.getString(1), dateOrder.getString(2), dateOrder.getString(3), dateOrder.getString(4)));
        }
        return dAllOrder;
    }
}
