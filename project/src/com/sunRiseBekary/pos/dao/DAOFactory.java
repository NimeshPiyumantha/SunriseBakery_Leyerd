package com.sunRiseBekary.pos.dao;

import com.sunRiseBekary.pos.dao.custom.Impl.*;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case CASHIER:
                return new CashierDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailsDAOImpl();
            case QUERYDAO:
                return new QueryDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        CUSTOMER, CASHIER, ITEM, ORDER, ORDERDETAILS, QUERYDAO, USER
    }
}
