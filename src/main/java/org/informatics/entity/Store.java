package org.informatics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store implements Serializable {
    private List<Employee> employees;
    private List<Cashdesk> cashdesks;
    private List<Goods> deliveredGoods;
    private HashMap<Goods, BigDecimal> soldGoods;
    private List<Receipt> receipts;
    private BigDecimal surChargeGroceries;
    private BigDecimal surChargeNonFood;
    private int daysForSale;
    private double percentage;

    public Store(List<Employee> employees, List<Cashdesk> cashdesks, BigDecimal surChargeGroceries, BigDecimal surChargeNonFood, int daysForSale, double percentage) {
        this.employees = employees;
        this.cashdesks = cashdesks;
        this.surChargeGroceries = surChargeGroceries.divide(BigDecimal.valueOf(100));
        this.surChargeNonFood = surChargeNonFood.divide(BigDecimal.valueOf(100));
        this.daysForSale = daysForSale;
        this.percentage = percentage / 100;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getDaysForSale() {
        return daysForSale;
    }

    public void setDaysForSale(int daysForSale) {
        this.daysForSale = daysForSale;
    }

    public BigDecimal getSurChargeGroceries() {
        return surChargeGroceries;
    }

    public void setSurChargeGroceries(BigDecimal surChargeGroceries) {
        this.surChargeGroceries = surChargeGroceries;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Cashdesk> getCashdesks() {
        return cashdesks;
    }

    public void setCashdesks(List<Cashdesk> cashdesks) {
        this.cashdesks = cashdesks;
    }

    public List<Goods> getDeliveredGoods() {
        return deliveredGoods;
    }

    public void setDeliveredGoods(List<Goods> deliveredGoods) {
        this.deliveredGoods = deliveredGoods;
    }

    public HashMap<Goods, BigDecimal> getSoldGoods() {
        return soldGoods;
    }

    public void setSoldGoods(HashMap<Goods, BigDecimal> soldGoods) {
        this.soldGoods = soldGoods;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public BigDecimal getSurCharge() {
        return surChargeGroceries;
    }

    public void setSurCharge(BigDecimal surCharge) {
        this.surChargeGroceries = surCharge;
    }

    public BigDecimal getSurChargeNonFood() {
        return surChargeNonFood;
    }

    public void setSurChargeNonFood(BigDecimal surChargeNonFood) {
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

    public boolean availableCashdesk(){
        for(Cashdesk cashdesk : this.getCashdesks()){
            if(cashdesk.getCurrEmployee() != null){
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "Store{" +
                "employees=" + employees +
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