package com.sunRiseBekary.pos.dao.custom;

import com.sunRiseBekary.pos.dao.CrudDAO;
import com.sunRiseBekary.pos.entity.Order;
import com.sunRiseBekary.pos.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public interface OrderDAO extends CrudDAO<Order, String> {
    ArrayList<Order> searchCustomerId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<Order> searchDateId(String id) throws SQLException, ClassNotFoundException;
}
