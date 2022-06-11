package com.sunRiseBekary.pos.bo.custom.Impl;


import com.sunRiseBekary.pos.bo.custom.PurchaseOrderBO;
import com.sunRiseBekary.pos.dao.DAOFactory;
import com.sunRiseBekary.pos.dao.custom.*;
import com.sunRiseBekary.pos.db.DBConnection;
import com.sunRiseBekary.pos.dto.CustomerDTO;
import com.sunRiseBekary.pos.dto.ItemDTO;
import com.sunRiseBekary.pos.dto.OrderDTO;
import com.sunRiseBekary.pos.dto.OrderDetailsDTO;
import com.sunRiseBekary.pos.entity.Customer;
import com.sunRiseBekary.pos.entity.Item;
import com.sunRiseBekary.pos.entity.Order;
import com.sunRiseBekary.pos.entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {

        /*Transaction*/
        Connection connection = DBConnection.getInstance().getConnection();
        /*if order id already exist*/
        if (orderDAO.exist(dto.getOId())) {

        }
        connection.setAutoCommit(false);
        boolean save = orderDAO.save(new Order(dto.getOId(), dto.getCustomerId(), dto.getODate(), dto.getOTime()));

        if (!save) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailsDTO detailDTO : dto.getOrderDetails()) {
            boolean save1 = orderDetailsDAO.save(new OrderDetails(detailDTO.getOrderID(), detailDTO.getCusId(), detailDTO.getItemCode(), detailDTO.getItemName(), detailDTO.getOrderQty(), detailDTO.getUnitPrice(), detailDTO.getDiscount(), detailDTO.getTotal()));
            if (!save1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemDTO item = searchItem(detailDTO.getItemCode());
            item.setItemQtyOnHand(item.getItemQtyOnHand() - detailDTO.getOrderQty());

            //update item
            boolean update = itemDAO.update(new Item(item.getItemCode(), item.getItemName(), item.getItemDescription(), item.getItemPackSize(), item.getItemUnitPrice(), item.getItemQtyOnHand(), item.getItemAddDate(), item.getItemAddTime()));

            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer ent = customerDAO.search(id);
        return new CustomerDTO(ent.getCustomerID(), ent.getCustomerTitle(), ent.getCustomerName(), ent.getCustomerAddress(), ent.getCustomerCity(), ent.getCustomerProvince(), ent.getCustomerPostCode(), ent.getCustomerAddDate(), ent.getCustomerAddTime());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item ent = itemDAO.search(code);
        return new ItemDTO(ent.getItemCode(), ent.getItemName(), ent.getItemDescription(), ent.getItemPackSize(), ent.getItemUnitPrice(), ent.getItemQtyOnHand(), ent.getItemAddDate(), ent.getItemAddTime());
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCustomerID(), customer.getCustomerTitle(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerCity(), customer.getCustomerProvince(), customer.getCustomerPostCode(), customer.getCustomerAddDate(), customer.getCustomerAddTime()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getItemName(), item.getItemDescription(), item.getItemPackSize(), item.getItemUnitPrice(), item.getItemQtyOnHand(), item.getItemAddDate(), item.getItemAddTime()));
        }
        return allItems;
    }
}
