package com.sunRiseBekary.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.CustomerBO;
import com.sunRiseBekary.pos.dto.CustomerDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.util.ValidationUtil;
import com.sunRiseBekary.pos.view.tm.CustomerTM;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageCustomerFormController implements Initializable {
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public AnchorPane ManageCustomerFormContext;
    public JFXButton btnSave;
    public TextField txtCustomerID;
    public TextField txtAddress;
    public TextField txtICustomerName;
    public TextField txtTitle;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public TableView<CustomerTM> tblCustomer;
    public Label lblCustomerAddTime;
    public Label lblCustomerAddDate;
    public TextField txtSearchCustomer;
    public JFXButton btnAdd;
    public JFXButton btnDelete;


    //--------------------------------CHECK TEXT FILED ----------------------------//
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern CustomerID = Pattern.compile("^[C](-[0-9]{3,4})$");
    Pattern Title = Pattern.compile("^[Mr|Mrs]{2,3}$");
    Pattern CustomerName = Pattern.compile("^([A-Z a-z]{5,40})$");
    Pattern Address = Pattern.compile("^[A-z0-9/, ]{6,30}$");
    Pattern City = Pattern.compile("^([A-Z  a-z]{4,20})$");
    Pattern Province = Pattern.compile("^([A-Z  a-z]{4,20})$");
    Pattern PostalCode = Pattern.compile("^([0-9]{2,10})$");

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
        loadCUDateAndTime();
        //-------------------------------Table Load---------------------------------//

        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("CustomerTitle"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("CustomerAddress"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("CustomerCity"));
        tblCustomer.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("CustomerProvince"));
        tblCustomer.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("CustomerPostCode"));

        initUI();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtCustomerID.setText(newValue.getCustomerID());
                txtTitle.setText(newValue.getCustomerTitle());
                txtICustomerName.setText(newValue.getCustomerName());
                txtAddress.setText(newValue.getCustomerAddress());
                txtCity.setText(newValue.getCustomerCity());
                txtProvince.setText(newValue.getCustomerProvince());
                txtPostalCode.setText(newValue.getCustomerPostCode());

                txtCustomerID.setDisable(false);
                txtTitle.setDisable(false);
                txtICustomerName.setDisable(false);
                txtAddress.setDisable(false);
                txtCity.setDisable(false);
                txtProvince.setDisable(false);
                txtPostalCode.setDisable(false);
            }
        });

        txtPostalCode.setOnAction(event -> btnSave.fire());

        try {
            loadAllCustomers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtCustomerID.clear();
        txtTitle.clear();
        txtICustomerName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();

        txtCustomerID.setDisable(true);
        txtTitle.setDisable(true);
        txtICustomerName.setDisable(true);
        txtAddress.setDisable(true);
        txtCity.setDisable(true);
        txtProvince.setDisable(true);
        txtPostalCode.setDisable(true);

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        tblCustomer.getItems().clear();
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            for (CustomerDTO customerDTO : allCustomers) {
                tblCustomer.getItems().add(new CustomerTM(customerDTO.getCustomerID(), customerDTO.getCustomerTitle(), customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerCity(), customerDTO.getCustomerProvince(), customerDTO.getCustomerPostCode(), customerDTO.getCustomerAddDate(), customerDTO.getCustomerAddTime()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    //-----------------Save Customer-------------------------//
    public void SaveCustomerOnAction(ActionEvent actionEvent) {
        String id = txtCustomerID.getText();
        String title = txtTitle.getText();
        String name = txtICustomerName.getText();
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String pCode = txtPostalCode.getText();
        String date = lblCustomerAddDate.getText();
        String time = lblCustomerAddTime.getText();

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existCustomer(id)) {
                    NotificationController.WarringError("Save Customer Warning", id, "Already exists ");
                }
                customerBO.saveCustomer(new CustomerDTO(id, title, name, address, city, province, pCode, date, time));
                tblCustomer.getItems().add(new CustomerTM(id, title, name, address, city, province, pCode, date, time));
                NotificationController.SuccessfulTableNotification("Save", "Customer");
            } catch (SQLException e) {
                NotificationController.WarringError("Save Customer Warning", id + e.getMessage(), "Failed to save the Customer ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update Customer*/
            try {
                if (!existCustomer(id)) {
                    NotificationController.WarringError("Update Customer Warning", id, "There is no such Customer associated with the ");
                }
                //Customer update
                customerBO.updateCustomer(new CustomerDTO(id, title, name, address, city, province, pCode, date, time));
                NotificationController.SuccessfulTableNotification("Update", "Customer");
            } catch (SQLException e) {
                NotificationController.WarringError("Update Customer Warning", id + e.getMessage(), "Failed to update the Customer ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTM selectCustomer = tblCustomer.getSelectionModel().getSelectedItem();
            selectCustomer.setCustomerTitle(title);
            selectCustomer.setCustomerName(name);
            selectCustomer.setCustomerAddress(address);
            selectCustomer.setCustomerCity(city);
            selectCustomer.setCustomerProvince(province);
            selectCustomer.setCustomerPostCode(pCode);
            tblCustomer.refresh();
        }
        btnAdd.fire();
    }

    private boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.customerExist(id);
    }

    //-----------------Delete Customer-------------------------//
    public void DeleteCustomerOnAction(ActionEvent actionEvent) {
        String id = tblCustomer.getSelectionModel().getSelectedItem().getCustomerID();
        try {
            if (!exitCustomer(id)) {
                NotificationController.WarringError("Delete Customer Warning", id, "There is no such Customer associated with the ");
            }
            customerBO.deleteCustomer(id);
            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Customer");
            tblCustomer.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            NotificationController.WarringError("Delete Customer Warning", id, "Failed to delete the Customer ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean exitCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.customerExist(id);
    }

    //-----------------Search Customer-------------------------//
    public void CustomerSearchOnAction() throws SQLException, ClassNotFoundException {
        if (txtSearchCustomer.getText().trim().isEmpty()) {
            tblCustomer.getItems().clear();
            initUI();
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllCustomers();
        } else {
            if (exitCustomer(txtSearchCustomer.getText())) {
                tblCustomer.getItems().clear();
                ArrayList<CustomerDTO> arrayList = customerBO.customerSearchId(txtSearchCustomer.getText());
                if (arrayList != null) {
                    for (CustomerDTO customerDTO : arrayList) {
                        tblCustomer.getItems().add(new CustomerTM(customerDTO.getCustomerID(), customerDTO.getCustomerTitle(), customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerCity(), customerDTO.getCustomerProvince(), customerDTO.getCustomerPostCode(), customerDTO.getCustomerAddDate(), customerDTO.getCustomerAddTime()));

                        txtCustomerID.setText(customerDTO.getCustomerID());
                        txtTitle.setText(customerDTO.getCustomerTitle());
                        txtICustomerName.setText(customerDTO.getCustomerName());
                        txtAddress.setText(customerDTO.getCustomerAddress());
                        txtCity.setText(customerDTO.getCustomerCity());
                        txtProvince.setText(customerDTO.getCustomerProvince());
                        txtPostalCode.setText(customerDTO.getCustomerPostCode());
                    }
                }
            } else {
                tblCustomer.getItems().clear();
                initUI();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    //-------------Validation----------------//
    private void storeValidations() {
        map.put(txtCustomerID, CustomerID);
        map.put(txtTitle, Title);
        map.put(txtICustomerName, CustomerName);
        map.put(txtAddress, Address);
        map.put(txtCity, City);
        map.put(txtProvince, Province);
        map.put(txtPostalCode, PostalCode);
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

    //------------Time Date-------//
    private void loadCUDateAndTime() {
        //Set Date
        lblCustomerAddDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblCustomerAddTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    //----------------Add New---------------//
    public void AddNewOnAction(ActionEvent actionEvent) {
        txtCustomerID.clear();
        txtTitle.clear();
        txtICustomerName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();

        txtCustomerID.setDisable(false);
        txtTitle.setDisable(false);
        txtICustomerName.setDisable(false);
        txtAddress.setDisable(false);
        txtCity.setDisable(false);
        txtProvince.setDisable(false);
        txtPostalCode.setDisable(false);

        txtCustomerID.setText(generateNewId());
        txtTitle.requestFocus();
        setBorders(txtCustomerID, txtTitle, txtICustomerName, txtAddress, txtCity, txtProvince, txtPostalCode);
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomer.getSelectionModel().clearSelection();
    }

    //----------------generateNewId---------------//
    private String generateNewId() {
        try {
            return customerBO.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (tblCustomer.getItems().isEmpty()) {
            return "C-001";
        } else {
            String id = getLastCashierId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C-%03d", newCustomerId);
        }
    }

    //----------------getLastCashierId---------------//
    private String getLastCashierId() {
        try {
            return customerBO.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "C-001";
    }
}
