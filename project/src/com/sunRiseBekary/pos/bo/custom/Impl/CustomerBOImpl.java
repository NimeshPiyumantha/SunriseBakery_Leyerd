package com.sunRiseBekary.pos.bo.custom.Impl;

import com.sunRiseBekary.pos.bo.custom.CustomerBO;
import com.sunRiseBekary.pos.dao.DAOFactory;
import com.sunRiseBekary.pos.dao.custom.CustomerDAO;
import com.sunRiseBekary.pos.dto.CustomerDTO;
import com.sunRiseBekary.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCustomerID(),customer.getCustomerTitle(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerCity(),customer.getCustomerProvince(),customer.getCustomerPostCode(),customer.getCustomerAddDate(),customer.getCustomerAddTime()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCustomerID(),dto.getCustomerTitle(),dto.getCustomerName(),dto.getCustomerAddress(),dto.getCustomerCity(),dto.getCustomerProvince(),dto.getCustomerPostCode(),dto.getCustomerAddDate(),dto.getCustomerAddTime()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCustomerID(),dto.getCustomerTitle(),dto.getCustomerName(),dto.getCustomerAddress(),dto.getCustomerCity(),dto.getCustomerProvince(),dto.getCustomerPostCode(),dto.getCustomerAddDate(),dto.getCustomerAddTime()));
    }

    @Override
    public boolean customerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> customerSearchId(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.searchId(id);
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCustomerID(),customer.getCustomerTitle(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerCity(),customer.getCustomerProvince(),customer.getCustomerPostCode(),customer.getCustomerAddDate(),customer.getCustomerAddTime()));
        }
        return allCustomers;
    }
}
