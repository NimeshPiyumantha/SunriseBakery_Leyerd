package com.sunRiseBekary.pos.view.tm;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class CustomTM {
    private String CustomerID;
    private String CustomerTitle;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerCity;
    private String CustomerProvince;
    private String CustomerPostCode;
    private String CustomerAddDate;
    private String CustomerAddTime;

    private String CashierID;
    private String CashierName;
    private String CashierPsw;
    private String CashierAddress;
    private String CashierCNumber;
    private String CashierNIC;
    private double CashierSalary;
    private String CashierDescription;
    private String CashierAddDate;
    private String CashierAddTime;

    private String ItemCode;
    private String ItemName;
    private String ItemDescription;
    private String ItemPackSize;
    private double ItemUnitPrice;
    private int ItemQtyOnHand;
    private String ItemAddDate;
    private String ItemAddTime;

    private String OrderID;
    private String OrderDate;
    private String OrderTime;

    private int OrderQty;
    private double unitPrice;
    private double Discount;
    private double Total;

    private String UserName;
    private String Password;

    private int itemSoldQty;
    private int orderCount;
    private double sumOfTotal;

    public CustomTM() {
    }

    public CustomTM(String itemCode, String itemName, int itemSoldQty, double itemUnitPrice) {
        ItemCode = itemCode;
        ItemName = itemName;
        ItemUnitPrice = itemUnitPrice;
        this.itemSoldQty = itemSoldQty;
    }

    public CustomTM(String orderDate, int itemSoldQty,int orderCount,  double sumOfTotal) {
        OrderDate = orderDate;
        this.itemSoldQty = itemSoldQty;
        this.orderCount = orderCount;
        this.sumOfTotal = sumOfTotal;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCustomerTitle() {
        return CustomerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        CustomerTitle = customerTitle;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return CustomerCity;
    }

    public void setCustomerCity(String customerCity) {
        CustomerCity = customerCity;
    }

    public String getCustomerProvince() {
        return CustomerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        CustomerProvince = customerProvince;
    }

    public String getCustomerPostCode() {
        return CustomerPostCode;
    }

    public void setCustomerPostCode(String customerPostCode) {
        CustomerPostCode = customerPostCode;
    }

    public String getCustomerAddDate() {
        return CustomerAddDate;
    }

    public void setCustomerAddDate(String customerAddDate) {
        CustomerAddDate = customerAddDate;
    }

    public String getCustomerAddTime() {
        return CustomerAddTime;
    }

    public void setCustomerAddTime(String customerAddTime) {
        CustomerAddTime = customerAddTime;
    }

    public String getCashierID() {
        return CashierID;
    }

    public void setCashierID(String cashierID) {
        CashierID = cashierID;
    }

    public String getCashierName() {
        return CashierName;
    }

    public void setCashierName(String cashierName) {
        CashierName = cashierName;
    }

    public String getCashierPsw() {
        return CashierPsw;
    }

    public void setCashierPsw(String cashierPsw) {
        CashierPsw = cashierPsw;
    }

    public String getCashierAddress() {
        return CashierAddress;
    }

    public void setCashierAddress(String cashierAddress) {
        CashierAddress = cashierAddress;
    }

    public String getCashierCNumber() {
        return CashierCNumber;
    }

    public void setCashierCNumber(String cashierCNumber) {
        CashierCNumber = cashierCNumber;
    }

    public String getCashierNIC() {
        return CashierNIC;
    }

    public void setCashierNIC(String cashierNIC) {
        CashierNIC = cashierNIC;
    }

    public double getCashierSalary() {
        return CashierSalary;
    }

    public void setCashierSalary(double cashierSalary) {
        CashierSalary = cashierSalary;
    }

    public String getCashierDescription() {
        return CashierDescription;
    }

    public void setCashierDescription(String cashierDescription) {
        CashierDescription = cashierDescription;
    }

    public String getCashierAddDate() {
        return CashierAddDate;
    }

    public void setCashierAddDate(String cashierAddDate) {
        CashierAddDate = cashierAddDate;
    }

    public String getCashierAddTime() {
        return CashierAddTime;
    }

    public void setCashierAddTime(String cashierAddTime) {
        CashierAddTime = cashierAddTime;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getItemPackSize() {
        return ItemPackSize;
    }

    public void setItemPackSize(String itemPackSize) {
        ItemPackSize = itemPackSize;
    }

    public double getItemUnitPrice() {
        return ItemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        ItemUnitPrice = itemUnitPrice;
    }

    public int getItemQtyOnHand() {
        return ItemQtyOnHand;
    }

    public void setItemQtyOnHand(int itemQtyOnHand) {
        ItemQtyOnHand = itemQtyOnHand;
    }

    public String getItemAddDate() {
        return ItemAddDate;
    }

    public void setItemAddDate(String itemAddDate) {
        ItemAddDate = itemAddDate;
    }

    public String getItemAddTime() {
        return ItemAddTime;
    }

    public void setItemAddTime(String itemAddTime) {
        ItemAddTime = itemAddTime;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        OrderQty = orderQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getItemSoldQty() {
        return itemSoldQty;
    }

    public void setItemSoldQty(int itemSoldQty) {
        this.itemSoldQty = itemSoldQty;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getSumOfTotal() {
        return sumOfTotal;
    }

    public void setSumOfTotal(double sumOfTotal) {
        this.sumOfTotal = sumOfTotal;
    }

    @Override
    public String toString() {
        return "CustomTM{" +
                "CustomerID='" + CustomerID + '\'' +
                ", CustomerTitle='" + CustomerTitle + '\'' +
                ", CustomerName='" + CustomerName + '\'' +
                ", CustomerAddress='" + CustomerAddress + '\'' +
                ", CustomerCity='" + CustomerCity + '\'' +
                ", CustomerProvince='" + CustomerProvince + '\'' +
                ", CustomerPostCode='" + CustomerPostCode + '\'' +
                ", CustomerAddDate='" + CustomerAddDate + '\'' +
                ", CustomerAddTime='" + CustomerAddTime + '\'' +
                ", CashierID='" + CashierID + '\'' +
                ", CashierName='" + CashierName + '\'' +
                ", CashierPsw='" + CashierPsw + '\'' +
                ", CashierAddress='" + CashierAddress + '\'' +
                ", CashierCNumber='" + CashierCNumber + '\'' +
                ", CashierNIC='" + CashierNIC + '\'' +
                ", CashierSalary=" + CashierSalary +
                ", CashierDescription='" + CashierDescription + '\'' +
                ", CashierAddDate='" + CashierAddDate + '\'' +
                ", CashierAddTime='" + CashierAddTime + '\'' +
                ", ItemCode='" + ItemCode + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemDescription='" + ItemDescription + '\'' +
                ", ItemPackSize='" + ItemPackSize + '\'' +
                ", ItemUnitPrice=" + ItemUnitPrice +
                ", ItemQtyOnHand=" + ItemQtyOnHand +
                ", ItemAddDate='" + ItemAddDate + '\'' +
                ", ItemAddTime='" + ItemAddTime + '\'' +
                ", OrderID='" + OrderID + '\'' +
                ", OrderDate='" + OrderDate + '\'' +
                ", OrderTime='" + OrderTime + '\'' +
                ", OrderQty=" + OrderQty +
                ", unitPrice=" + unitPrice +
                ", Discount=" + Discount +
                ", Total=" + Total +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", itemSoldQty=" + itemSoldQty +
                ", orderCount=" + orderCount +
                ", sumOfTotal=" + sumOfTotal +
                '}';
    }
}
