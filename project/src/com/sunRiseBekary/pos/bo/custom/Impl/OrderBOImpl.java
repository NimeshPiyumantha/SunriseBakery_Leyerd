package com.sunRiseBekary.pos.bo.custom.Impl;

import com.sunRiseBekary.pos.bo.custom.OrderBO;
import com.sunRiseBekary.pos.dao.DAOFactory;
import com.sunRiseBekary.pos.dao.custom.OrderDAO;
import com.sunRiseBekary.pos.dto.OrderDTO;
import com.sunRiseBekary.pos.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class OrderBOImpl implements OrderBO {
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        ArrayList<Order> all = orderDAO.getAll();
        ArrayList<OrderDTO> allOrder = new ArrayList<>();
        for (Order orders : all) {
            allOrder.add(new OrderDTO(orders.getOrderID(), orders.getCustomerID(), orders.getOrderDate(), orders.getOrderTime()));
        }
        return allOrder;
    }

    @Override
    public boolean deleteOrder(String code) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(code);
    }

    @Override
    public boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new Order(dto.getOId(), dto.getCustomerId(), dto.getODate(), dto.getOTime()));
    }

    @Override
    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new Order(dto.getOId(), dto.getCustomerId(), dto.getODate(), dto.getOTime()));
    }

    @Override
    public boolean orderExit(String code) throws SQLException, ClassNotFoundException {
        return orderDAO.exist(code);
    }

    @Override
    public String generateNewOrder() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<OrderDTO> OrderSearchId(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = orderDAO.searchId(id);
        ArrayList<OrderDTO> allOrder = new ArrayList<>();
        for (Order orders1 : allOrders) {
            allOrder.add(new OrderDTO(orders1.getOrderID(), orders1.getCustomerID(), orders1.getOrderDate(), orders1.getOrderTime()));
        }
        return allOrder;
    }

    @Override
    public ArrayList<OrderDTO> searchCustomerId(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Order> allCus = orderDAO.searchCustomerId(id);
        ArrayList<OrderDTO> allCusOrder = new ArrayList<>();
        for (Order orders2 : allCus) {
            allCusOrder.add(new OrderDTO(orders2.getOrderID(), orders2.getCustomerID(), orders2.getOrderDate(), orders2.getOrderTime()));
        }
        return allCusOrder;
    }

    @Override
    public ArrayList<OrderDTO> searchDateId(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Order> allDate = orderDAO.searchDateId(id);
        ArrayList<OrderDTO> allDateOrder = new ArrayList<>();
        for (Order orders3 : allDate) {
            allDateOrder.add(new OrderDTO(orders3.getOrderID(), orders3.getCustomerID(), orders3.getOrderDate(), orders3.getOrderTime()));
        }
        return allDateOrder;
    }
}
