package com.sunRiseBekary.pos.bo.custom;

import com.sunRiseBekary.pos.bo.SuperBO;
import com.sunRiseBekary.pos.dto.CashierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public interface CashierBO extends SuperBO {
    ArrayList<CashierDTO> getAllCashier() throws SQLException, ClassNotFoundException;

    boolean saveCashier(CashierDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateCashier(CashierDTO dto) throws SQLException, ClassNotFoundException;

    boolean cashierExist(String id) throws SQLException, ClassNotFoundException;

    boolean deleteCashier(String id) throws SQLException, ClassNotFoundException;

    String generateNewCashierID() throws SQLException, ClassNotFoundException;

    ArrayList<CashierDTO> cashierSearchId(String id) throws SQLException, ClassNotFoundException;
}
