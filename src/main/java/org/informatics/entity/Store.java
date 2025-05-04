package org.informatics.entity;

import org.informatics.exception.NotValidArgumentException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store implements Serializable {
    private String name;

    private List<Employee> employees;
    private List<Cashdesk> cashdesks;
    private List<Goods> deliveredGoods;
    private HashMap<Goods, BigDecimal> soldGoods;
    private List<Receipt> receipts;
    private BigDecimal surChargeGroceries;
    private BigDecimal surChargeNonFood;
    private int daysForSale;
    private double percentage;

    public Store() {
    }

    public Store(double percentage) {
        this.percentage = percentage;
    }

    public Store(String name, List<Employee> employees, List<Cashdesk> cashdesks, BigDecimal surChargeGroceries, BigDecimal surChargeNonFood, int daysForSale, double percentage) {
        this.name = name;
        this.employees = employees;
        this.cashdesks = cashdesks;
        this.surChargeGroceries = surChargeGroceries.divide(BigDecimal.valueOf(100));
        this.surChargeNonFood = surChargeNonFood.divide(BigDecimal.valueOf(100));
        this.daysForSale = daysForSale;
        this.percentage = percentage / 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) throws NotValidArgumentException {
        if(percentage < 0) {
            throw new NotValidArgumentException("Percentage should be a positive number.");
        }
        this.percentage = percentage;
    }

    public int getDaysForSale() {
        return daysForSale;
    }

    public void setDaysForSale(int daysForSale) throws NotValidArgumentException {
        if(daysForSale <= 0){
            throw new NotValidArgumentException("Days should be a positive number.");
        }
        this.daysForSale = daysForSale;
    }

    public BigDecimal getSurChargeGroceries() {
        return surChargeGroceries;
    }

    public void setSurChargeGroceries(BigDecimal surChargeGroceries) throws NotValidArgumentException {
        if(surChargeGroceries.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotValidArgumentException("The surcharge should be a positive number");
        }
        this.surChargeGroceries = surChargeGroceries;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) throws NotValidArgumentException {
        if(employees == null || employees.size() <= 0) {
            throw new NotValidArgumentException("Employees list can not be empty.");
        }
        this.employees = employees;
    }

    public List<Cashdesk> getCashdesks() {
        return cashdesks;
    }

    public void setCashdesks(List<Cashdesk> cashdesks) throws NotValidArgumentException {
        if(cashdesks == null || cashdesks.size() <= 0) {
            throw new NotValidArgumentException("Cashdesk list can not be empty.");
        }
        this.cashdesks = cashdesks;
    }

    public List<Goods> getDeliveredGoods() {
        return deliveredGoods;
    }

    public void setDeliveredGoods(List<Goods> deliveredGoods) throws NotValidArgumentException {
        if(deliveredGoods == null || deliveredGoods.size() <= 0) {
            throw new NotValidArgumentException("Cashdesk list can not be empty.");
        }
        this.deliveredGoods = deliveredGoods;
    }

    public HashMap<Goods, BigDecimal> getSoldGoods() {
        return soldGoods;
    }

    public void setSoldGoods(HashMap<Goods, BigDecimal> soldGoods) throws NotValidArgumentException {
        if(soldGoods == null || soldGoods.isEmpty()) {
            throw new NotValidArgumentException("Sold goods list can not be empty.");
        }
        this.soldGoods = soldGoods;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) throws NotValidArgumentException {
        if(receipts.isEmpty() || receipts.size() <= 0) {
            throw new NotValidArgumentException("Receipts list can not be empty.");
        }
        this.receipts = receipts;
    }

    public BigDecimal getSurCharge() {
        return surChargeGroceries;
    }

    public void setSurCharge(BigDecimal surCharge) throws NotValidArgumentException {
        if(surCharge == null || surCharge.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotValidArgumentException("Surcharge should be positive.");
        }
        this.surChargeGroceries = surCharge;
    }

    public BigDecimal getSurChargeNonFood() {
        return surChargeNonFood;
    }

    public void setSurChargeNonFood(BigDecimal surChargeNonFood) throws NotValidArgumentException {
        if(surChargeNonFood == null || surChargeNonFood.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotValidArgumentException("Surcharge should be positive.");
        }
        this.surChargeNonFood = surChargeNonFood;
    }

    public void addSoldGoods(Map<Goods, BigDecimal> soldGoods) {
        if (this.soldGoods == null) {
            this.soldGoods = new HashMap<>();
        }

        for (Map.Entry<Goods, BigDecimal> entry : soldGoods.entrySet()) {
            Goods goods = entry.getKey();
            BigDecimal quantity = entry.getValue();
            this.soldGoods.put(goods, this.soldGoods.getOrDefault(goods, BigDecimal.ZERO).add(quantity));
        }

    }

    public boolean availableCashdesk() throws NotValidArgumentException {
        for(Cashdesk cashdesk : this.getCashdesks()){
            if(cashdesk.getCurrEmployee() != null){
                return true;
            }
        }
        throw new NotValidArgumentException("Not available cashdesk");
    }


    @Override
    public String toString() {
        return "Store{" +
                "name=" + name +
                ", employees=" + employees +
                ", cashdesks=" + cashdesks +
                ", deliveredGoods=" + deliveredGoods +
                ", receipts=" + receipts +
                ", surChargeGroceries=" + surChargeGroceries +
                ", surChargeNonFood=" + surChargeNonFood +
                ", daysForSale=" + daysForSale +
                ", percentage=" + percentage +
                '}';
    }

}