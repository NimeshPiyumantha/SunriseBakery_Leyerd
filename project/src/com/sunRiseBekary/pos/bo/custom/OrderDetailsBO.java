package com.sunRiseBekary.pos.bo.custom;

import com.sunRiseBekary.pos.bo.SuperBO;
import com.sunRiseBekary.pos.dto.ItemDTO;
import com.sunRiseBekary.pos.dto.OrderDTO;
import com.sunRiseBekary.pos.dto.OrderDetailsDTO;
import com.sunRiseBekary.pos.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public interface OrderDetailsBO extends SuperBO {
    ArrayList<OrderDetailsDTO> getAllOrderDetail() throws SQLException, ClassNotFoundException;

    boolean deleteOrderDetail(String code) throws SQLException, ClassNotFoundException;

    boolean saveOrderDetail(OrderDetailsDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateOrderDetail(OrderDetailsDTO dto) throws SQLException, ClassNotFoundException;

    boolean orderDetailExit(String code) throws SQLException, ClassNotFoundException;

    String generateNewOrderDetail() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailsDTO> OrderSearchId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailsDTO> searchCustomerId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailsDTO> searchItemId(String id) throws SQLException, ClassNotFoundException;
}
