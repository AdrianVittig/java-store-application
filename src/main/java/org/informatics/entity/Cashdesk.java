package org.informatics.entity;

import java.util.ArrayList;
import java.util.List;

public class Cashdesk {
    private Employee currEmployee;
    private List<Goods> scannedGoods;

    public Cashdesk() {
        this.scannedGoods = new ArrayList<>();
    }

    public Cashdesk(Employee currEmployee, List<Goods> scannedGoods) {
        this.currEmployee = currEmployee;
        this.scannedGoods = scannedGoods;
    }

    public Employee getCurrEmployee() {
        return currEmployee;
    }

    public void setCurrEmployee(Employee currEmployee) {
        this.currEmployee = currEmployee;
    }

    public List<Goods> getScannedGoods() {
        return scannedGoods;
    }

    public void setScannedGoods(List<Goods> scannedGoods) {
        this.scannedGoods = scannedGoods;
    }

    @Override
    public String toString() {
        return "Cashdesk{" +
                "currEmployee=" + currEmployee +
                ", scannedGoods=" + scannedGoods +
                '}';
    }
}