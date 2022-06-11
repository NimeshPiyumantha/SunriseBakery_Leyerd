package com.sunRiseBekary.pos.controller;

import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.ItemBO;
import com.sunRiseBekary.pos.dto.ItemDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.view.tm.ItemTM;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemDetailFormController implements Initializable {
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public TableView<ItemTM> tblItemView;
    public TextField txtItemCode;


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //-------------------------------Table Load---------------------------------//

        tblItemView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblItemView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblItemView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ItemDescription"));
        tblItemView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ItemPackSize"));
        tblItemView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("ItemUnitPrice"));
        tblItemView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("ItemQtyOnHand"));
        tblItemView.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("ItemAddDate"));
        tblItemView.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("ItemAddTime"));


        try {
            loadAllItem();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItem() throws SQLException, ClassNotFoundException {
        tblItemView.getItems().clear();
        /*Get all item*/
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO itemDTO : allItems) {
                tblItemView.getItems().add(new ItemTM(itemDTO.getItemCode(), itemDTO.getItemName(), itemDTO.getItemDescription(), itemDTO.getItemPackSize(), itemDTO.getItemUnitPrice(), itemDTO.getItemQtyOnHand(), itemDTO.getItemAddDate(), itemDTO.getItemAddTime()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean itemExit(String id) throws SQLException, ClassNotFoundException {
        return itemBO.itemExist(id);
    }

    //-----------------------------Item Search---------------------------------------//
    public void ItemSearchOnAction() throws SQLException, ClassNotFoundException {
        if (txtItemCode.getText().trim().isEmpty()) {
            tblItemView.getItems().clear();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllItem();
        } else {
            if (itemExit(txtItemCode.getText())) {
                tblItemView.getItems().clear();
                ArrayList<ItemDTO> arrayList = itemBO.itemSearchId(txtItemCode.getText());
                if (arrayList != null) {
                    for (ItemDTO itemDTO : arrayList) {
                        tblItemView.getItems().add(new ItemTM(itemDTO.getItemCode(), itemDTO.getItemName(), itemDTO.getItemDescription(), itemDTO.getItemPackSize(), itemDTO.getItemUnitPrice(), itemDTO.getItemQtyOnHand(), itemDTO.getItemAddDate(), itemDTO.getItemAddTime()));
                    }
                }
            } else {
                tblItemView.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }
}
