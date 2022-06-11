package com.sunRiseBekary.pos.dao.custom.Impl;

import com.sunRiseBekary.pos.dao.custom.ItemDAO;
import com.sunRiseBekary.pos.entity.Customer;
import com.sunRiseBekary.pos.entity.Item;
import com.sunRiseBekary.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> allItem = new ArrayList<>();
        while (rst.next()) {
            allItem.add(new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getDouble(5), rst.getInt(6), rst.getString(7), rst.getString(8)));
        }
        return allItem;
    }

    @Override
    public boolean save(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?,?,?,?)", dto.getItemCode(), dto.getItemName(), dto.getItemDescription(), dto.getItemPackSize(), dto.getItemUnitPrice(), dto.getItemQtyOnHand(), dto.getItemAddDate(), dto.getItemAddTime());
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET ItemName=? ,ItemDescription=? ,ItemPackSize=? ,ItemUnitPrice=? , ItemQtyOnHand=?,ItemAddDate=?,ItemAddTime=? WHERE ItemCode=?", dto.getItemName(), dto.getItemDescription(), dto.getItemPackSize(), dto.getItemUnitPrice(), dto.getItemQtyOnHand(), dto.getItemAddDate(), dto.getItemAddTime(), dto.getItemCode());
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE ItemCode=?", id);
        if (rst.next()) {
            return new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getDouble(5), rst.getInt(6), rst.getString(7), rst.getString(8));
        }
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT ItemCode FROM Item WHERE ItemCode=?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE ItemCode=?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT ItemCode FROM Item ORDER BY ItemCode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("ItemCode");
            int newCustomerId = Integer.parseInt(id.replace("I-", "")) + 1;
            return String.format("I-%03d", newCustomerId);
        } else {
            return "I-001";
        }
    }

    @Override
    public ArrayList<Item> searchId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE ItemCode=?", id);
        ArrayList<Item> allItem = new ArrayList<>();
        while (rst.next()) {
            allItem.add(new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getDouble(5), rst.getInt(6), rst.getString(7), rst.getString(8)));
        }
        return allItem;
    }
}
