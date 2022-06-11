package com.sunRiseBekary.pos.dao.custom;

import com.sunRiseBekary.pos.dao.CrudDAO;
import com.sunRiseBekary.pos.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public interface OrderDetailsDAO extends CrudDAO<OrderDetails, String> {
    ArrayList<OrderDetails> searchCustomerId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetails> searchItemId(String id) throws SQLException, ClassNotFoundException;

}
