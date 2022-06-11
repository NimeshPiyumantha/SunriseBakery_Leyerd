package com.sunRiseBekary.pos.dao.custom.Impl;

import com.sunRiseBekary.pos.dao.custom.CashierDAO;
import com.sunRiseBekary.pos.entity.Cashier;
import com.sunRiseBekary.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class CashierDAOImpl implements CashierDAO {
    @Override
    public ArrayList<Cashier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Cashier");
        ArrayList<Cashier> allCashier = new ArrayList<>();
        while (rst.next()) {
            allCashier.add(new Cashier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6),rst.getDouble(7),rst.getString(8), rst.getString(9), rst.getString(10)));
        }
        return allCashier;
    }

    @Override
    public boolean save(Cashier dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Cashier VALUES (?,?,?,?,?,?,?,?,?,?)", dto.getCashierID(), dto.getCashierName(), dto.getCashierPsw(),dto.getCashierAddress(), dto.getCashierCNumber(), dto.getCashierNIC(), dto.getCashierSalary(), dto.getCashierDescription(), dto.getCashierAddDate(), dto.getCashierAddTime());
    }

    @Override
    public boolean update(Cashier dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Cashier SET CashierName=? ," + " CashierPsw=? ," + " CashierAddress=? ," + " CashierCNumber=? , CashierNIC=? ," + " CashierSalary=?,CashierDescription=?, CashierAddDate=?,CashierAddTime=? WHERE CashierID=?", dto.getCashierName(), dto.getCashierPsw(), dto.getCashierAddress(), dto.getCashierCNumber(), dto.getCashierNIC(), dto.getCashierSalary(), dto.getCashierDescription(), dto.getCashierAddDate(), dto.getCashierAddTime(), dto.getCashierID());
    }

    @Override
    public Cashier search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Cashier WHERE CashierID=?", id);
        if (rst.next()) {
            return new Cashier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6),rst.getDouble(7),rst.getString(8), rst.getString(9), rst.getString(10));
        }
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT CashierID FROM Cashier WHERE CashierID=?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Cashier WHERE CashierID=?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT CashierID FROM Cashier ORDER BY CashierID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("CashierID");
            int newCustomerId = Integer.parseInt(id.replace("CH-", "")) + 1;
            return String.format("CH-%03d", newCustomerId);
        } else {
            return "CH-001";
        }
    }

    @Override
    public ArrayList<Cashier> searchId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Cashier WHERE CashierID=?", id);
        ArrayList<Cashier> allCashier = new ArrayList<>();
        while (rst.next()) {
            allCashier.add(new Cashier(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6),rst.getDouble(7),rst.getString(8), rst.getString(9), rst.getString(10)));
        }
        return allCashier;
    }
}
