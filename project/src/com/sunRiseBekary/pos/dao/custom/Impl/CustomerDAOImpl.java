package com.sunRiseBekary.pos.dao.custom.Impl;

import com.sunRiseBekary.pos.dao.custom.CustomerDAO;
import com.sunRiseBekary.pos.entity.Cashier;
import com.sunRiseBekary.pos.entity.Customer;
import com.sunRiseBekary.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> allCustomers = new ArrayList<>();
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9)));
        }
        return allCustomers;
    }

    @Override
    public boolean save(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer VALUES (?,?,?,?,?,?,?,?,?)", dto.getCustomerID(), dto.getCustomerTitle(), dto.getCustomerName(), dto.getCustomerAddress(), dto.getCustomerCity(), dto.getCustomerProvince(), dto.getCustomerPostCode(), dto.getCustomerAddDate(), dto.getCustomerAddTime());
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate( "UPDATE Customer SET CustomerTitle=? , CustomerName=? , CustomerAddress=? , CustomerCity=? , CustomerProvince=? , CustomerPostCode=?,CustomerAddDate=?,CustomerAddTime=? WHERE CustomerID=?", dto.getCustomerTitle(), dto.getCustomerName(), dto.getCustomerAddress(), dto.getCustomerCity(), dto.getCustomerProvince(), dto.getCustomerPostCode(), dto.getCustomerAddDate(), dto.getCustomerAddTime(), dto.getCustomerID());
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE CustomerID=?", id);
        if (rst.next()) {
            return new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9));
        }
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT CustomerID FROM Customer WHERE CustomerID=?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE CustomerID=?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT CustomerID FROM Customer ORDER BY CustomerID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("CustomerID");
            int newCustomerId = Integer.parseInt(id.replace("C-", "")) + 1;
            return String.format("C-%03d", newCustomerId);
        } else {
            return "C-001";
        }
    }

    @Override
    public ArrayList<Customer> searchId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE CustomerID=?", id);
        ArrayList<Customer> allCustomer = new ArrayList<>();
        while (rst.next()) {
            allCustomer.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9)));
        }
        return allCustomer;
    }
}
