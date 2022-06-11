package com.sunRiseBekary.pos.bo;

import com.sunRiseBekary.pos.bo.custom.Impl.*;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case CASHIER:
                return new CashierBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDERDETAILS:
                return new OrderDetailsBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case Custom:
                return new QueryBOImpl();
            case User:
                return new UserBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        CUSTOMER, ITEM, PURCHASE_ORDER, ORDER, ORDERDETAILS, User, Custom, CASHIER
    }

}
