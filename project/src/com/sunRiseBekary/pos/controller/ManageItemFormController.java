package com.sunRiseBekary.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.ItemBO;
import com.sunRiseBekary.pos.dto.ItemDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.util.ValidationUtil;
import com.sunRiseBekary.pos.view.tm.ItemTM;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageItemFormController implements Initializable {
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public AnchorPane ManageItemFormContext;
    public JFXButton btnSave;
    public TextField txtItemCode;
    public TextField txtUnitPrice;
    public TextField txtItemName;
    public TextField txtQtyOnHand;
    public TextField txtDescription;
    public TextField txtPackWeight;
    public TableView<ItemTM> tblItem;
    public TableColumn<? extends Object, ? extends Object> colItemCode;
    public TableColumn<? extends Object, ? extends Object> colItemName;
    public TableColumn<? extends Object, ? extends Object> colDescription;
    public TableColumn<? extends Object, ? extends Object> colPackWeight;
    public TableColumn<? extends Object, ? extends Object> colUnitPrice;
    public TableColumn<? extends Object, ? extends Object> colQtyOnHand;
    public Label lblTime;
    public Label lblDate;
    public TextField txtSearch;
    public JFXButton btnAddNew;
    public JFXButton btnDelete;


    //--------------------------------CHECK TEXT FILED ----------------------------//
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern ItemCode = Pattern.compile("^[I](-[0-9]{3,4})$");
    Pattern ItemName = Pattern.compile("^([A-Z a-z]{5,40})$");
    Pattern Description = Pattern.compile("^([A-z /s]{3,30})$");
    Pattern PackWeight = Pattern.compile("^[1-9][0-9]*( )(|g|kg|ml|l)$");
    Pattern UnitPrice = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    Pattern QtyOnHand = Pattern.compile("^[1-9][0-9]*$");

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
        //--------------------------------CHECK TEXT FILED ----------------------------//
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        storeValidations();
        loadDateAndTime();

        //-------------------------------Table Load---------------------------------//

        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ItemDescription"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ItemPackSize"));
        tblItem.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("ItemUnitPrice"));
        tblItem.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("ItemQtyOnHand"));

        initUI();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtItemCode.setText(newValue.getItemCode());
                txtItemName.setText(newValue.getItemName());
                txtDescription.setText(newValue.getItemDescription());
                txtPackWeight.setText(newValue.getItemPackSize());
                txtUnitPrice.setText(newValue.getItemUnitPrice() + "");
                txtQtyOnHand.setText(newValue.getItemQtyOnHand() + "");

                txtItemCode.setDisable(false);
                txtItemName.setDisable(false);
                txtDescription.setDisable(false);
                txtPackWeight.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
            }
        });

        txtQtyOnHand.setOnAction(event -> btnSave.fire());

        try {
            loadAllItem();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtItemCode.clear();
        txtItemName.clear();
        txtDescription.clear();
        txtPackWeight.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();

        txtItemCode.setDisable(true);
        txtItemName.setDisable(true);
        txtDescription.setDisable(true);
        txtPackWeight.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    //----------Date Time-------------//
    private void loadDateAndTime() {
        //Set Date
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void loadAllItem() throws SQLException, ClassNotFoundException {
        tblItem.getItems().clear();
        try {
            ArrayList<ItemDTO> allItem = itemBO.getAllItems();
            for (ItemDTO itemDTO : allItem) {
                tblItem.getItems().add(new ItemTM(itemDTO.getItemCode(), itemDTO.getItemName(), itemDTO.getItemDescription(), itemDTO.getItemPackSize(), itemDTO.getItemUnitPrice(), itemDTO.getItemQtyOnHand(), itemDTO.getItemAddDate(), itemDTO.getItemAddTime()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    //---------------------Save Item-------------------//
    public void SaveItemOnAction(ActionEvent actionEvent) {
        String id = txtItemCode.getText();
        String name = txtItemName.getText();
        String description = txtDescription.getText();
        String packSize = txtPackWeight.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        String itemDate = lblDate.getText();
        String itemTime = lblTime.getText();

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save item*/
            try {
                if (exitItem(id)) {
                    NotificationController.WarringError("Save Item Warning", id, "Already exists ");
                }
                itemBO.saveItem(new ItemDTO(id, name, description, packSize, unitPrice, qtyOnHand, itemDate, itemTime));
                tblItem.getItems().add(new ItemTM(id, name, description, packSize, unitPrice, qtyOnHand, itemDate, itemTime));
                NotificationController.SuccessfulTableNotification("Save", "Item");
            } catch (SQLException e) {
                NotificationController.WarringError("Save Item Warning", id + e.getMessage(), "Failed to save the Item ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update item*/
            try {
                if (!exitItem(id)) {
                    NotificationController.WarringError("Update Item Warning", id, "There is no such Item associated with the ");
                }
                //item update
                itemBO.updateItem(new ItemDTO(id, name, description, packSize, unitPrice, qtyOnHand, itemDate, itemTime));
                NotificationController.SuccessfulTableNotification("Update", "Item");
            } catch (SQLException e) {
                NotificationController.WarringError("Update Item Warning", id + e.getMessage(), "Failed to update the Item ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
            selectedItem.setItemCode(id);
            selectedItem.setItemName(name);
            selectedItem.setItemDescription(description);
            selectedItem.setItemPackSize(packSize);
            selectedItem.setItemUnitPrice(unitPrice);
            selectedItem.setItemQtyOnHand(qtyOnHand);
            tblItem.refresh();
        }
        btnAddNew.fire();
    }

    private boolean exitItem(String id) throws SQLException, ClassNotFoundException {
        return itemBO.itemExist(id);
    }

    //---------------------Delete Item-------------------//
    public void DeleteItemOnAction(ActionEvent actionEvent) {
        String id = tblItem.getSelectionModel().getSelectedItem().getItemCode();
        try {
            if (!exitItem(id)) {
                NotificationController.WarringError("Delete Item Warning", id, "There is no such Item associated with the ");
            }
            itemBO.deleteItem(id);
            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Item");
            tblItem.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            NotificationController.WarringError("Delete Item Warning", id, "Failed to delete the Item ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean itemExit(String id) throws SQLException, ClassNotFoundException {
        return itemBO.itemExist(id);
    }

    //---------------------Search Item-------------------//
    public void Search() throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()) {
            tblItem.getItems().clear();
            initUI();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllItem();
        } else {
            if (itemExit(txtSearch.getText())) {
                tblItem.getItems().clear();
                ArrayList<ItemDTO> arrayList = itemBO.itemSearchId(txtSearch.getText());
                if (arrayList != null) {
                    for (ItemDTO itemDTO : arrayList) {
                        tblItem.getItems().add(new ItemTM(itemDTO.getItemCode(), itemDTO.getItemName(), itemDTO.getItemDescription(), itemDTO.getItemPackSize(), itemDTO.getItemUnitPrice(), itemDTO.getItemQtyOnHand(), itemDTO.getItemAddDate(), itemDTO.getItemAddTime()));


                        txtItemCode.setText(itemDTO.getItemCode());
                        txtItemName.setText(itemDTO.getItemName());
                        txtDescription.setText(itemDTO.getItemDescription());
                        txtPackWeight.setText(itemDTO.getItemPackSize());
                        txtUnitPrice.setText(itemDTO.getItemUnitPrice() + "");
                        txtQtyOnHand.setText(itemDTO.getItemQtyOnHand() + "");

                    }
                }
            } else {
                tblItem.getItems().clear();
                initUI();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    //---------------Validation---------------//
    private void storeValidations() {
        map.put(txtItemCode, ItemCode);
        map.put(txtItemName, ItemName);
        map.put(txtDescription, Description);
        map.put(txtPackWeight, PackWeight);
        map.put(txtUnitPrice, UnitPrice);
        map.put(txtQtyOnHand, QtyOnHand);

    }

    //----------------Key Resealed---------------//
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                System.out.println("Work");
            }
        }
    }

    //reset border colors to default color
    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    public void AddNewOnAction(ActionEvent actionEvent) {
        txtItemCode.clear();
        txtItemName.clear();
        txtDescription.clear();
        txtPackWeight.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();

        txtItemCode.setDisable(false);
        txtItemName.setDisable(false);
        txtDescription.setDisable(false);
        txtPackWeight.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);


        txtItemCode.setText(generateNewId());
        txtItemName.requestFocus();
        setBorders(txtItemCode, txtItemName, txtDescription, txtPackWeight, txtUnitPrice, txtQtyOnHand);
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblItem.getSelectionModel().clearSelection();
    }

    //----------------generateNewId---------------//
    private String generateNewId() {
        try {
            return itemBO.generateNewItemCode();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (tblItem.getItems().isEmpty()) {
            return "I-001";
        } else {
            String id = getLastItem();
            int newCustomerId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I-%03d", newCustomerId);
        }
    }

    //----------------getLastCashierId---------------//
    private String getLastItem() {
        try {
            return itemBO.generateNewItemCode();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I-001";
    }
}
