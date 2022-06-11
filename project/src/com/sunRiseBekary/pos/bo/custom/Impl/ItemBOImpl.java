package com.sunRiseBekary.pos.bo.custom.Impl;

import com.sunRiseBekary.pos.bo.custom.ItemBO;
import com.sunRiseBekary.pos.dao.DAOFactory;
import com.sunRiseBekary.pos.dao.custom.ItemDAO;
import com.sunRiseBekary.pos.dto.ItemDTO;
import com.sunRiseBekary.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getItemName(),item.getItemDescription(),item.getItemPackSize(),item.getItemUnitPrice(),item.getItemQtyOnHand(),item.getItemAddDate(),item.getItemAddTime()));
        }
        return allItems;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(dto.getItemCode(),dto.getItemName(),dto.getItemDescription(),dto.getItemPackSize(),dto.getItemUnitPrice(),dto.getItemQtyOnHand(),dto.getItemAddDate(),dto.getItemAddTime()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getItemCode(),dto.getItemName(),dto.getItemDescription(),dto.getItemPackSize(),dto.getItemUnitPrice(),dto.getItemQtyOnHand(),dto.getItemAddDate(),dto.getItemAddTime()));
    }

    @Override
    public boolean itemExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public String generateNewItemCode() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }

    @Override
    public ArrayList<ItemDTO> itemSearchId(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.searchId(id);
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getItemName(),item.getItemDescription(),item.getItemPackSize(),item.getItemUnitPrice(),item.getItemQtyOnHand(),item.getItemAddDate(),item.getItemAddTime()));
        }
        return allItems;
    }
}
