package com.sunRiseBekary.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sunRiseBekary.pos.bo.BOFactory;
import com.sunRiseBekary.pos.bo.custom.PurchaseOrderBO;
import com.sunRiseBekary.pos.dto.CustomerDTO;
import com.sunRiseBekary.pos.dto.ItemDTO;
import com.sunRiseBekary.pos.dto.OrderDTO;
import com.sunRiseBekary.pos.dto.OrderDetailsDTO;
import com.sunRiseBekary.pos.util.NotificationController;
import com.sunRiseBekary.pos.util.ValidationUtil;
import com.sunRiseBekary.pos.view.tm.CartTM;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ManageOrderFormController implements Initializable {
    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);

    public AnchorPane ManageOrderFormContext;
    public Label lblId;
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtName;
    public JFXTextField txtCity;
    public JFXTextField txtAddress;
    public JFXTextField txtUnitPrize;
    public JFXTextField txtQTYOnHand;
    public TextField txtQTY;
    public TextField txtDiscount;
    public TableView<CartTM> tblOrder;
    public JFXTextField txtItemName;
    public JFXButton btnPalaceOrder;
    public JFXButton btnPrintBill;
    public JFXButton btnAddToCart;
    public Label lblDate;
    public Label lblTime;
    public Label lblTotal;

    //--------------------------------CHECK TEXT FILED ----------------------------//
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern QTY = Pattern.compile("^[0-9]{1,5}$");
    Pattern Discount = Pattern.compile("^[0-9]{1,2}.{1,3}$");
    private String orderId;

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

        orderId = generateNewOrderId();
        lblId.setText(orderId);

        //--------------------------------CHECK TEXT FILED ----------------------------//
        btnAddToCart.setDisable(true);
        btnPalaceOrder.setDisable(true);
        storeValidations();
        loadDateAndTime();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer(newValue + "")) {
                            //"There is no such customer associated with the id " + id
                            NotificationController.WarringError("Search Customer Warning", newValue, "There is no such customer associated with the ");
                        }
                        /*Search Customer*/
                        CustomerDTO customerDTO = purchaseOrderBO.searchCustomer(newValue + "");
                        txtName.setText(customerDTO.getCustomerName());
                        txtAddress.setText(customerDTO.getCustomerAddress());
                        txtCity.setText(customerDTO.getCustomerCity());

                    } catch (SQLException e) {
                        NotificationController.WarringError("Search Customer Warning", newValue, "Failed to find the customer ");
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtName.clear();
                txtAddress.clear();
                txtCity.clear();
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQTYOnHand.setEditable(newItemCode != null);
            btnAddToCart.setDisable(newItemCode == null);

            if (newItemCode != null) {
                try {
                    if (!existItem(newItemCode + "")) {
                        //throw new NotFoundException("There is no such item associated with the id " + code);
                    }
                    /*Find Item*/
                    ItemDTO item = purchaseOrderBO.searchItem(newItemCode + "");
                    txtItemName.setText(item.getItemName());
                    txtQTYOnHand.setText(String.valueOf(item.getItemQtyOnHand()));
                    txtUnitPrize.setText(String.valueOf(item.getItemUnitPrice()));
                    Optional<CartTM> optOrderDetail = tblOrder.getItems().stream().filter(detail -> detail.getItemCode().equals(newItemCode)).findFirst();
                    txtQTYOnHand.setText((optOrderDetail.isPresent() ? item.getItemQtyOnHand() - optOrderDetail.get().getOrderQty() : item.getItemQtyOnHand()) + "");

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

            } else {
                txtItemName.clear();
                txtQTYOnHand.clear();
                txtUnitPrize.clear();
                txtQTY.clear();
                txtDiscount.clear();
            }
        });
        //-----------Table Cart Load-------//
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Discount"));
        tblOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("Total"));

        //---------------------Delete Add Item---------------//
        TableColumn<CartTM, Button> lastCol = (TableColumn<CartTM, Button>) tblOrder.getColumns().get(6);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(event -> {
                tblOrder.getItems().remove(param.getValue());
                NotificationController.SuccessfulTableNotification("Cart Item Delete", "Cart Item");
                tblOrder.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {
            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbCustomerId.setDisable(true);
                txtName.setDisable(true);
                txtAddress.setDisable(true);
                txtCity.setDisable(true);
                txtItemName.setDisable(true);
                txtQTYOnHand.setDisable(true);
                txtUnitPrize.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getItemCode());
                btnAddToCart.setText("Update");
                txtQTYOnHand.setText(Integer.parseInt(txtQTYOnHand.getText()) + selectedOrderDetail.getOrderQty() + "");
                txtDiscount.setText(selectedOrderDetail.getDiscount() + "");
                txtQTY.setText(selectedOrderDetail.getOrderQty() + "");
            } else {
                btnAddToCart.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQTYOnHand.clear();
            }
        });
        loadAllCustomerIds();
        loadAllItemCodes();
        btnAddToCart.setDisable(true);
    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = purchaseOrderBO.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems().add(customerDTO.getCustomerID());
            }

        } catch (SQLException e) {
            NotificationController.Warring("Customer Load", "Failed to load customer ids.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemCodes() {
        try {
            /*Get all items*/
            ArrayList<ItemDTO> all = purchaseOrderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemCode.getItems().add(dto.getItemCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //-----------------Data Time--------------//
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

    //------Calculate Full Total-----------//
    private void calculateTotal() {
        double total = 0;
        for (CartTM detail : tblOrder.getItems()) {
            total += detail.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    //----------Btn Place Order----------------//
    public void conformOrderBtnOnAction(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        cmbCustomerId.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtCity.setDisable(false);
        txtItemName.setDisable(false);
        txtQTYOnHand.setDisable(false);
        txtUnitPrize.setDisable(false);
        boolean b = saveOrder(orderId, cmbCustomerId.getValue(), lblDate.getText(), lblTime.getText(),
                tblOrder.getItems().stream().map(tm -> new OrderDetailsDTO(orderId, tm.getCusId(), tm.getItemCode(), tm.getItemName(), tm.getOrderQty(), tm.getUnitPrice(), tm.getDiscount(), tm.getTotal())).collect(Collectors.toList()));
        if (b) {
            //makePayment();
            NotificationController.SuccessfulTableNotification("Order Place", "Orders Place");
        } else {
            System.out.println(b);
            NotificationController.UnSuccessfulTableNotification("Order Place", "Orders Place");
        }

        btnPrintBill(); //PrintBill
        orderId = generateNewOrderId(); //Generate id
        lblId.setText(orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrder.getItems().clear();
        txtQTY.clear();
        txtDiscount.clear();
        calculateTotal();
    }

    //--------------btn Add Cart --------------//
    public void addToCartOnAction(ActionEvent actionEvent) {
        btnPalaceOrder.setDisable(false);
        double unitPrice = Double.parseDouble(txtUnitPrize.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        int qty = Integer.parseInt(txtQTY.getText());
        double Cost = ((unitPrice * qty) * discount / 100);
        double totalCost = (unitPrice * qty) - Cost;

        if (qty <= Integer.parseInt(txtQTYOnHand.getText())) {
            boolean exists = tblOrder.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(cmbItemCode.getValue()));

            if (exists) {
                CartTM cartTM = tblOrder.getItems().stream().filter(detail -> detail.getItemCode().equals(cmbItemCode.getValue())).findFirst().get();

                if (btnAddToCart.getText().equalsIgnoreCase("Update")) {
                    txtItemName.setDisable(false);
                    txtQTYOnHand.setDisable(false);
                    txtUnitPrize.setDisable(false);
                    cartTM.setOrderQty(qty);
                    cartTM.setTotal(totalCost);
                    cartTM.setDiscount(discount);
                    tblOrder.getSelectionModel().clearSelection();
                } else {
                    cartTM.setOrderQty(cartTM.getOrderQty() + qty);
                    totalCost = cartTM.getOrderQty() * unitPrice;
                    cartTM.setTotal(totalCost);
                }
                tblOrder.refresh();
            } else {
                tblOrder.getItems().add(new CartTM(lblId.getText(), cmbCustomerId.getValue(), cmbItemCode.getValue(), txtItemName.getText(), qty, unitPrice, discount, totalCost));
            }
            cmbItemCode.getSelectionModel().clearSelection();
            cmbCustomerId.requestFocus();
            calculateTotal();
            enableOrDisablePlaceOrderButton();
        } else {
            NotificationController.Warring("Item Stock", "Item Stock Out ...");
            cmbItemCode.getSelectionModel().clearSelection();
            txtItemName.clear();
            txtQTYOnHand.clear();
            txtUnitPrize.clear();
            txtQTY.clear();
            txtDiscount.clear();
            setBorders(txtQTY, txtDiscount);
        }
    }

    //----------------Save order---------------//
    public boolean saveOrder(String orderId, String customerId, String orderDate, String orderTime, List<OrderDetailsDTO> orderDetails) {
        try {
            return purchaseOrderBO.purchaseOrder(new OrderDTO(orderId, customerId, orderDate, orderTime, orderDetails));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    //-----------------Print Bill Button------------------------------//
    public void btnPrintBillOnAction(ActionEvent actionEvent) {
        btnPrintBill();
    }

    //-----------------Print Bill Method------------------------------//
    public void btnPrintBill() {
        try {
            ObservableList<CartTM> items = tblOrder.getItems();
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/reports/sunRise.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, new JRBeanCollectionDataSource(items));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    //-----------------Validation--------------------------//
    private void storeValidations() {
        map.put(txtQTY, QTY);
        map.put(txtDiscount, Discount);

    }

    //--------------------Text Key Released---------------------------//
    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddToCart);

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

    //------------Item Exit-------//
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.checkItemIsAvailable(code);
    }

    //--------------Customer Exit-----//
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.checkCustomerIsAvailable(id);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPalaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrder.getItems().isEmpty()));
    }

    //----------Generate Order Id-------------//
    private String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateNewOrderID();
        } catch (SQLException e) {
            NotificationController.Warring("Generate Order Id", "Failed to generate a new order id...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "O-001";
    }

    //-----------------------------Clear Table Button--------------------------//
    public void btnClearOnAction(ActionEvent actionEvent) {
        ClearOnAction();
    }

    //--------------Clear Method---------------------------//
    public void ClearOnAction() {
        tblOrder.getSelectionModel().clearSelection();
        lblTotal.setText("0.00/=");
        btnPalaceOrder.setDisable(true);
    }
}
