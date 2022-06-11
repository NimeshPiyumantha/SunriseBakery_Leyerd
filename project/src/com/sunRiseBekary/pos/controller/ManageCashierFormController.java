package com.sunRiseBekary.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.CashierBO;
import com.sunRiseBekary.pos.dto.CashierDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.util.ValidationUtil;
import com.sunRiseBekary.pos.view.tm.CashierTM;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class ManageCashierFormController implements Initializable {
    private final CashierBO cashierBO = (CashierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CASHIER);

    public AnchorPane ManageCashierFormContext;
    public JFXButton btnSave;
    public TextField txtCashierID;
    public TextField txtContactNo;
    public TextField txtCashierIName;
    public TextField txtNIC;
    public TextField txtUserPassWord;
    public TextField txtSalary;
    public TextField txtAddress;
    public TextField txtDescription;
    public TableView<CashierTM> tblCashier;
    public Label lblCADate;
    public Label lblCATime;
    public TextField txtCashierSearch;
    public JFXButton btnAdd;
    public JFXButton btnDelete;

    //--------------------------------CHECK TEXT FILED ----------------------------//
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern CashierID = Pattern.compile("^[CH]{2}(-[0-9]{3,4})$");
    Pattern CashierIName = Pattern.compile("^([A-Z a-z]{5,40})$");
    Pattern UserPassword = Pattern.compile("^([A-Z a-z]{5,15}[0-9]{1,10})$");
    Pattern Address = Pattern.compile("^([A-Za-z]{4,10})$");
    Pattern ContactNo = Pattern.compile("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$");
    Pattern NIC = Pattern.compile("^([0-9]{12}|[0-9V]{10})$");
    Pattern Salary = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    Pattern Description = Pattern.compile("^([A-Z a-z]{5,15})$");

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

        storeValidations();
        loadCDateAndTime();

        //-------------------------------Table Load---------------------------------//

        tblCashier.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("CashierID"));
        tblCashier.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("CashierName"));
        tblCashier.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("CashierPsw"));
        tblCashier.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("CashierAddress"));
        tblCashier.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("CashierCNumber"));
        tblCashier.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("CashierNIC"));
        tblCashier.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("CashierSalary"));
        tblCashier.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("CashierDescription"));

        initUI();

        try {
            loadAllCashier();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblCashier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtCashierID.setText(newValue.getCashierID());
                txtCashierIName.setText(newValue.getCashierName());
                txtUserPassWord.setText(newValue.getCashierPsw());
                txtAddress.setText(newValue.getCashierAddress());
                txtContactNo.setText(newValue.getCashierCNumber());
                txtNIC.setText(newValue.getCashierNIC());
                txtSalary.setText(newValue.getCashierSalary() + "");
                txtDescription.setText(newValue.getCashierDescription());

                txtCashierID.setDisable(false);
                txtCashierIName.setDisable(false);
                txtUserPassWord.setDisable(false);
                txtAddress.setDisable(false);
                txtContactNo.setDisable(false);
                txtNIC.setDisable(false);
                txtSalary.setDisable(false);
                txtDescription.setDisable(false);
            }
        });

        txtDescription.setOnAction(event -> btnSave.fire());
    }

    private void initUI() {
        txtCashierID.clear();
        txtCashierIName.clear();
        txtUserPassWord.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtNIC.clear();
        txtSalary.clear();
        txtDescription.clear();

        txtCashierID.setDisable(true);
        txtCashierIName.setDisable(true);
        txtUserPassWord.setDisable(true);
        txtAddress.setDisable(true);
        txtContactNo.setDisable(true);
        txtNIC.setDisable(true);
        txtSalary.setDisable(true);
        txtDescription.setDisable(true);

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    //-----------------Time Date -------------------//
    private void loadCDateAndTime() {
        //Set Date
        lblCADate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblCATime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    //-----------------Load  Cashier-------------------//
    private void loadAllCashier() throws SQLException, ClassNotFoundException {
        tblCashier.getItems().clear();
        try {
            ArrayList<CashierDTO> allCashier = cashierBO.getAllCashier();
            for (CashierDTO cashierDTO : allCashier) {
                tblCashier.getItems().add(new CashierTM(cashierDTO.getCashierID(), cashierDTO.getCashierName(), cashierDTO.getCashierPsw(), cashierDTO.getCashierAddress(), cashierDTO.getCashierCNumber(), cashierDTO.getCashierNIC(), cashierDTO.getCashierSalary(), cashierDTO.getCashierDescription(), cashierDTO.getCashierAddDate(), cashierDTO.getCashierAddTime()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    //-----------------Save Cashier-------------------------//
    public void SaveCashierOnAction(ActionEvent actionEvent) {
        String id = txtCashierID.getText();
        String name = txtCashierIName.getText();
        String pws = txtUserPassWord.getText();
        String address = txtAddress.getText();
        String cNo = txtContactNo.getText();
        String nic = txtNIC.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String description = txtDescription.getText();

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Cashier*/
            try {
                if (exitCashier(id)) {
                    NotificationController.WarringError("Save Cashier Warning", id, "Already exists ");
                }
                cashierBO.saveCashier(new CashierDTO(id, name, pws, address, cNo, nic, salary, description, lblCADate.getText(), lblCATime.getText()));
                tblCashier.getItems().add(new CashierTM(id, name, pws, address, cNo, nic, salary, description, lblCADate.getText(), lblCATime.getText()));
                NotificationController.SuccessfulTableNotification("Save", "Cashier");
            } catch (SQLException e) {
                NotificationController.WarringError("Save Cashier Warning", id + e.getMessage(), "Failed to save the Cashier ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update Cashier*/
            try {
                if (!exitCashier(id)) {
                    NotificationController.WarringError("Update Cashier Warning", id, "There is no such Cashier associated with the ");
                }
                //Customer update
                cashierBO.updateCashier(new CashierDTO(id, name, pws, address, cNo, nic, salary, description, lblCADate.getText(), lblCATime.getText()));
                NotificationController.SuccessfulTableNotification("Update", "Cashier");
            } catch (SQLException e) {
                NotificationController.WarringError("Update Cashier Warning", id + e.getMessage(), "Failed to update the Cashier ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CashierTM selectedCashier = tblCashier.getSelectionModel().getSelectedItem();
            selectedCashier.setCashierName(name);
            selectedCashier.setCashierPsw(pws);
            selectedCashier.setCashierAddress(address);
            selectedCashier.setCashierCNumber(cNo);
            selectedCashier.setCashierNIC(nic);
            selectedCashier.setCashierSalary(salary);
            selectedCashier.setCashierDescription(description);
            tblCashier.refresh();
        }
        btnAdd.fire();
    }

    private boolean exitCashier(String id) throws SQLException, ClassNotFoundException {
        return cashierBO.cashierExist(id);
    }

    //-----------------Search Cashier-------------//
    public void SearchCashierOnAction() throws SQLException, ClassNotFoundException {
        if (txtCashierSearch.getText().trim().isEmpty()) {
            tblCashier.getItems().clear();
            initUI();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllCashier();
        } else {
            if (exitCashier(txtCashierSearch.getText())) {
                tblCashier.getItems().clear();
                ArrayList<CashierDTO> arrayList = cashierBO.cashierSearchId(txtCashierSearch.getText());
                if (arrayList != null) {
                    for (CashierDTO cashierDTO : arrayList) {
                        tblCashier.getItems().add(new CashierTM(cashierDTO.getCashierID(), cashierDTO.getCashierName(), cashierDTO.getCashierPsw(), cashierDTO.getCashierAddress(), cashierDTO.getCashierCNumber(), cashierDTO.getCashierNIC(), cashierDTO.getCashierSalary(), cashierDTO.getCashierDescription(), cashierDTO.getCashierAddDate(), cashierDTO.getCashierAddTime()));

                        txtCashierID.setText(cashierDTO.getCashierID());
                        txtCashierIName.setText(cashierDTO.getCashierName());
                        txtUserPassWord.setText(cashierDTO.getCashierPsw());
                        txtAddress.setText(cashierDTO.getCashierAddress());
                        txtContactNo.setText(cashierDTO.getCashierCNumber());
                        txtNIC.setText(cashierDTO.getCashierNIC());
                        txtSalary.setText(cashierDTO.getCashierSalary() + "");
                        txtDescription.setText(cashierDTO.getCashierDescription());
                    }
                }
            } else {
                tblCashier.getItems().clear();
                initUI();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    //-------------Validation----------------//
    private void storeValidations() {
        map.put(txtCashierID, CashierID);
        map.put(txtCashierIName, CashierIName);
        map.put(txtUserPassWord, UserPassword);
        map.put(txtAddress, Address);
        map.put(txtContactNo, ContactNo);
        map.put(txtNIC, NIC);
        map.put(txtSalary, Salary);
        map.put(txtDescription, Description);
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

    //----------------Add New---------------//
    public void btnAddNewOnAction(ActionEvent actionEvent) {
        txtCashierID.clear();
        txtCashierIName.clear();
        txtUserPassWord.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtNIC.clear();
        txtSalary.clear();
        txtDescription.clear();

        txtCashierID.setDisable(false);
        txtCashierIName.setDisable(false);
        txtUserPassWord.setDisable(false);
        txtAddress.setDisable(false);
        txtContactNo.setDisable(false);
        txtNIC.setDisable(false);
        txtSalary.setDisable(false);
        txtDescription.setDisable(false);

        txtCashierID.setText(generateNewId());
        txtCashierIName.requestFocus();
        setBorders(txtCashierID, txtCashierIName, txtUserPassWord, txtAddress, txtContactNo, txtNIC, txtSalary, txtDescription);
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCashier.getSelectionModel().clearSelection();
    }

    //----------------generateNewId---------------//
    private String generateNewId() {
        try {
            return cashierBO.generateNewCashierID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (tblCashier.getItems().isEmpty()) {
            return "CH-001";
        } else {
            String id = getLastCashierId();
            int newCustomerId = Integer.parseInt(id.replace("CH", "")) + 1;
            return String.format("CH-%03d", newCustomerId);
        }
    }

    //----------------getLastCashierId---------------//
    private String getLastCashierId() {
        List<CashierTM> tempCustomersList = new ArrayList<>(tblCashier.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getCashierID();
    }

    //----------------Delete Cashier---------------//
    public void DeleteCashierOnAction(ActionEvent actionEvent) {
        String id = tblCashier.getSelectionModel().getSelectedItem().getCashierID();
        try {
            if (!exitCashier(id)) {
                NotificationController.WarringError("Delete Cashier Warning", id, "There is no such Cashier associated with the ");
            }
            cashierBO.deleteCashier(id);
            tblCashier.getItems().remove(tblCashier.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Cashier");
            tblCashier.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            NotificationController.WarringError("Delete Cashier Warning", id, "Failed to delete the Cashier ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
