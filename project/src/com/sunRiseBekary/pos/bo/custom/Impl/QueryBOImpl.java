package com.sunRiseBekary.pos.bo.custom.Impl;

import com.sunRiseBekary.pos.bo.custom.QueryBO;
import com.sunRiseBekary.pos.dao.DAOFactory;
import com.sunRiseBekary.pos.dao.custom.QueryDAO;
import com.sunRiseBekary.pos.dto.CustomDTO;
import com.sunRiseBekary.pos.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class QueryBOImpl implements QueryBO {
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public int getSumSales() throws SQLException, ClassNotFoundException {
        return queryDAO.getSumSales();
    }

    @Override
    public int SoldItems() throws SQLException, ClassNotFoundException {
        return queryDAO.SoldItems();
    }

    @Override
    public int getItem() throws SQLException, ClassNotFoundException {
        return queryDAO.getItem();
    }

    @Override
    public int getCustomer() throws SQLException, ClassNotFoundException {
        return queryDAO.getCustomer();
    }

    @Override
    public ArrayList<CustomDTO> SoldItem() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.SoldItem();
        ArrayList<CustomDTO> soldItem = new ArrayList<>();
        for (CustomEntity customEntity : all) {
            soldItem.add(new CustomDTO(customEntity.getItemCode(), customEntity.getItemName(), customEntity.getItemSoldQty(), customEntity.getItemUnitPrice()));
        }
        return soldItem;
    }

    @Override
    public ArrayList<CustomDTO> LeastSoldItem() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.LeastSoldItem();
        ArrayList<CustomDTO> customEntityArrayList = new ArrayList<>();
        for (CustomEntity customEntity : all) {
            customEntityArrayList.add(new CustomDTO(customEntity.getItemCode(), customEntity.getItemName(), customEntity.getItemSoldQty(), customEntity.getItemUnitPrice()));
        }
        return customEntityArrayList;
    }

    @Override
    public ArrayList<CustomDTO> MostSoldItem() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.MostSoldItem();
        ArrayList<CustomDTO> customEntityArrayList = new ArrayList<>();
        for (CustomEntity customEntity : all) {
            customEntityArrayList.add(new CustomDTO(customEntity.getItemCode(), customEntity.getItemName(), customEntity.getItemSoldQty(), customEntity.getItemUnitPrice()));
        }
        return customEntityArrayList;
    }

    @Override
    public ArrayList<CustomDTO> loadDailyIncomeReport() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.loadDailyIncomeReport();
        ArrayList<CustomDTO> customEntityDailyIncome = new ArrayList<>();
        for (CustomEntity customEntityDaily : all) {
            customEntityDailyIncome.add(new CustomDTO(customEntityDaily.getOrderDate(), customEntityDaily.getItemSoldQty(), customEntityDaily.getOrderCount(), customEntityDaily.getSumOfTotal()));
        }
        return customEntityDailyIncome;
    }

    @Override
    public ArrayList<CustomDTO> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.loadMonthlyIncomeReport();
        ArrayList<CustomDTO> customEntityDailyIncome = new ArrayList<>();
        for (CustomEntity customEntityDaily : all) {
            customEntityDailyIncome.add(new CustomDTO(customEntityDaily.getOrderDate(), customEntityDaily.getItemSoldQty(), customEntityDaily.getOrderCount(), customEntityDaily.getSumOfTotal()));
        }
        return customEntityDailyIncome;
    }

    @Override
    public ArrayList<CustomDTO> loadAnnuallyIncomeReport() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.loadAnnuallyIncomeReport();
        ArrayList<CustomDTO> customEntityDailyIncome = new ArrayList<>();
        for (CustomEntity customEntityDaily : all) {
            customEntityDailyIncome.add(new CustomDTO(customEntityDaily.getOrderDate(), customEntityDaily.getItemSoldQty(), customEntityDaily.getOrderCount(), customEntityDaily.getSumOfTotal()));
        }
        return customEntityDailyIncome;
    }

}
