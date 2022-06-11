package com.sunRiseBekary.pos.dao.custom.Impl;

import com.sunRiseBekary.pos.dao.custom.OrderDetailsDAO;
import com.sunRiseBekary.pos.entity.OrderDetails;
import com.sunRiseBekary.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order Details`");
        ArrayList<OrderDetails> allOrderDetails = new ArrayList<>();
        while (rst.next()) {
            allOrderDetails.add(new OrderDetails(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getInt(5), rst.getDouble(6), rst.getDouble(7), rst.getDouble(8)));
        }
        return allOrderDetails;
    }

    @Override
    public boolean save(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order Details` (OrderID,CustomerID,ItemCode,ItemName,OrderQty,unitPrice,Discount,Total) VALUES (?,?,?,?,?,?,?,?)", dto.getOrderID(), dto.getCustomerID(), dto.getItemCode(), dto.getItemName(), dto.getOrderQty(), dto.getUnitPrice(), dto.getDiscount(), dto.getTotal());
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `Order Details` SET  OrderQty=?, Discount=?,Total=? WHERE ItemCode=?", dto.getOrderQty(), dto.getDiscount(), dto.getTotal());
    }

    @Override
    public OrderDetails search(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderID FROM `Order Details` WHERE OrderID LIKE ?", oid);
        if (rst.next()) {
            return new OrderDetails(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getInt(5), rst.getDouble(6), rst.getDouble(7), rst.getDouble(8));
        }
        return null;
    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT OrderID FROM `Order Details` WHERE OrderID=?", oid).next();
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Order Details` WHERE OrderID=?", oid);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderID FROM `Order Details` ORDER BY OrderID DESC LIMIT 1;");
        return rst.next() ? String.format("O-%03d", (Integer.parseInt(rst.getString("OrderID").replace("O-", "")) + 1)) : "O-001";

    }

    @Override
    public ArrayList<OrderDetails> searchId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst1 = CrudUtil.executeQuery("SELECT * FROM `Order Details` WHERE OrderID=?", id);
        ArrayList<OrderDetails> sAllOrderDetails = new ArrayList<>();
        while (rst1.next()) {
            sAllOrderDetails.add(new OrderDetails(rst1.getString(1), rst1.getString(2), rst1.getString(3), rst1.getString(4), rst1.getInt(5), rst1.getDouble(6), rst1.getDouble(7), rst1.getDouble(8)));
        }
        return sAllOrderDetails;
    }

    @Override
    public ArrayList<OrderDetails> searchCustomerId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst2 = CrudUtil.executeQuery("SELECT * FROM `order details` WHERE CustomerID=?", id);
        ArrayList<OrderDetails> cAllOrderDetails = new ArrayList<>();
        while (rst2.next()) {
            cAllOrderDetails.add(new OrderDetails(rst2.getString(1), rst2.getString(2), rst2.getString(3), rst2.getString(4), rst2.getInt(5), rst2.getDouble(6), rst2.getDouble(7), rst2.getDouble(8)));
        }
        return cAllOrderDetails;
    }

    @Override
    public ArrayList<OrderDetails> searchItemId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst3 = CrudUtil.executeQuery("SELECT * FROM `order details` WHERE ItemCode=?", id);
        ArrayList<OrderDetails> oAllOrderDetails = new ArrayList<>();
        while (rst3.next()) {
            oAllOrderDetails.add(new OrderDetails(rst3.getString(1), rst3.getString(2), rst3.getString(3), rst3.getString(4), rst3.getInt(5), rst3.getDouble(6), rst3.getDouble(7), rst3.getDouble(8)));
        }
        return oAllOrderDetails;
    }
}
