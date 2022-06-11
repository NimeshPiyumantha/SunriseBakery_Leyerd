package com.sunRiseBekary.pos.bo.custom.Impl;

import com.sunRiseBekary.pos.bo.custom.OrderDetailsBO;
import com.sunRiseBekary.pos.dao.DAOFactory;
import com.sunRiseBekary.pos.dao.custom.OrderDetailsDAO;
import com.sunRiseBekary.pos.dto.OrderDetailsDTO;
import com.sunRiseBekary.pos.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class OrderDetailsBOImpl implements OrderDetailsBO {

    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrderDetail() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> all = orderDetailsDAO.getAll();
        ArrayList<OrderDetailsDTO> orderDetail = new ArrayList<>();
        for (OrderDetails orderDetails : all) {
            orderDetail.add(new OrderDetailsDTO(orderDetails.getOrderID(), orderDetails.getCustomerID(), orderDetails.getItemCode(), orderDetails.getItemName(), orderDetails.getOrderQty(), orderDetails.getUnitPrice(), orderDetails.getDiscount(), orderDetails.getTotal()));
        }
        return orderDetail;
    }

    @Override
    public boolean deleteOrderDetail(String code) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.delete(code);
    }

    @Override
    public boolean saveOrderDetail(OrderDetailsDTO dto) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.save(new OrderDetails(dto.getOrderID(), dto.getCusId(), dto.getItemCode(), dto.getItemName(), dto.getOrderQty(), dto.getUnitPrice(), dto.getDiscount(), dto.getTotal()));
    }

    @Override
    public boolean updateOrderDetail(OrderDetailsDTO dto) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.update(new OrderDetails(dto.getOrderID(), dto.getCusId(), dto.getItemCode(), dto.getItemName(), dto.getOrderQty(), dto.getUnitPrice(), dto.getDiscount(), dto.getTotal()));
    }

    @Override
    public boolean orderDetailExit(String code) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.exist(code);
    }

    @Override
    public String generateNewOrderDetail() throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.generateNewID();
    }

    @Override
    public ArrayList<OrderDetailsDTO> OrderSearchId(String oid) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> allOrder = orderDetailsDAO.searchId(oid);
        ArrayList<OrderDetailsDTO> orderDetail = new ArrayList<>();
        for (OrderDetails orderDetails1 : allOrder) {
            orderDetail.add(new OrderDetailsDTO(orderDetails1.getOrderID(), orderDetails1.getCustomerID(), orderDetails1.getItemCode(), orderDetails1.getItemName(), orderDetails1.getOrderQty(), orderDetails1.getUnitPrice(), orderDetails1.getDiscount(), orderDetails1.getTotal()));
        }
        return orderDetail;
    }

    @Override
    public ArrayList<OrderDetailsDTO> searchCustomerId(String id) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> allCus = orderDetailsDAO.searchCustomerId(id);
        ArrayList<OrderDetailsDTO> orderCusDetail = new ArrayList<>();
        for (OrderDetails orderDetail : allCus) {
            orderCusDetail.add(new OrderDetailsDTO(orderDetail.getOrderID(), orderDetail.getCustomerID(), orderDetail.getItemCode(), orderDetail.getItemName(), orderDetail.getOrderQty(), orderDetail.getUnitPrice(), orderDetail.getDiscount(), orderDetail.getTotal()));
        }
        return orderCusDetail;
    }

    @Override
    public ArrayList<OrderDetailsDTO> searchItemId(String Iid) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> allItem = orderDetailsDAO.searchItemId(Iid);
        ArrayList<OrderDetailsDTO> orderItemDetail = new ArrayList<>();
        for (OrderDetails orderDetails2 : allItem) {
            orderItemDetail.add(new OrderDetailsDTO(orderDetails2.getOrderID(), orderDetails2.getCustomerID(), orderDetails2.getItemCode(), orderDetails2.getItemName(), orderDetails2.getOrderQty(), orderDetails2.getUnitPrice(), orderDetails2.getDiscount(), orderDetails2.getTotal()));
        }
        return orderItemDetail;
    }
}
