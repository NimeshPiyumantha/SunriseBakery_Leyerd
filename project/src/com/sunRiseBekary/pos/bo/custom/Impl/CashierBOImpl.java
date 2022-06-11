package com.sunRiseBekary.pos.bo.custom.Impl;

import com.sunRiseBekary.pos.bo.custom.CashierBO;
import com.sunRiseBekary.pos.dao.DAOFactory;
import com.sunRiseBekary.pos.dao.custom.CashierDAO;
import com.sunRiseBekary.pos.dto.CashierDTO;
import com.sunRiseBekary.pos.entity.Cashier;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class CashierBOImpl implements CashierBO {

    private final CashierDAO cashierDAO = (CashierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CASHIER);

    @Override
    public ArrayList<CashierDTO> getAllCashier() throws SQLException, ClassNotFoundException {
        ArrayList<Cashier> all = cashierDAO.getAll();
        ArrayList<CashierDTO> allCashier = new ArrayList<>();
        for (Cashier cashier : all) {
            allCashier.add(new CashierDTO(cashier.getCashierID(),cashier.getCashierName(),cashier.getCashierPsw(),cashier.getCashierAddress(),cashier.getCashierCNumber(),cashier.getCashierNIC(),cashier.getCashierSalary(),cashier.getCashierDescription(),cashier.getCashierAddDate(),cashier.getCashierAddTime()));
        }
        return allCashier;
    }

    @Override
    public boolean saveCashier(CashierDTO dto) throws SQLException, ClassNotFoundException {
        return cashierDAO.save(new Cashier(dto.getCashierID(),dto.getCashierName(),dto.getCashierPsw(),dto.getCashierAddress(),dto.getCashierCNumber(),dto.getCashierNIC(),dto.getCashierSalary(),dto.getCashierDescription(),dto.getCashierAddDate(),dto.getCashierAddTime()));
    }

    @Override
    public boolean updateCashier(CashierDTO dto) throws SQLException, ClassNotFoundException {
        return cashierDAO.update(new Cashier(dto.getCashierID(),dto.getCashierName(),dto.getCashierPsw(),dto.getCashierAddress(),dto.getCashierCNumber(),dto.getCashierNIC(),dto.getCashierSalary(),dto.getCashierDescription(),dto.getCashierAddDate(),dto.getCashierAddTime()));
    }

    @Override
    public boolean cashierExist(String id) throws SQLException, ClassNotFoundException {
        return cashierDAO.exist(id);
    }

    @Override
    public boolean deleteCashier(String id) throws SQLException, ClassNotFoundException {
        return cashierDAO.delete(id);
    }

    @Override
    public String generateNewCashierID() throws SQLException, ClassNotFoundException {
        return cashierDAO.generateNewID();
    }

    @Override
    public ArrayList<CashierDTO> cashierSearchId(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Cashier> all = cashierDAO.searchId(id);
        ArrayList<CashierDTO> allCashierID = new ArrayList<>();
        for (Cashier cashier : all) {
            allCashierID.add(new CashierDTO(cashier.getCashierID(),cashier.getCashierName(),cashier.getCashierPsw(),cashier.getCashierAddress(),cashier.getCashierCNumber(),cashier.getCashierNIC(),cashier.getCashierSalary(),cashier.getCashierDescription(),cashier.getCashierAddDate(),cashier.getCashierAddTime()));
        }
        return allCashierID;
    }
}
