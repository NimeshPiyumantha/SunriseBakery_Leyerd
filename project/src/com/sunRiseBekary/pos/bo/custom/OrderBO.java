package com.sunRiseBekary.pos.bo.custom;

import com.sunRiseBekary.pos.bo.SuperBO;
import com.sunRiseBekary.pos.dto.ItemDTO;
import com.sunRiseBekary.pos.dto.OrderDTO;
import com.sunRiseBekary.pos.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public interface OrderBO extends SuperBO {
    ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String code) throws SQLException, ClassNotFoundException;

    boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    boolean orderExit(String code) throws SQLException, ClassNotFoundException;

    String generateNewOrder() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> OrderSearchId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> searchCustomerId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> searchDateId(String id) throws SQLException, ClassNotFoundException;
}
