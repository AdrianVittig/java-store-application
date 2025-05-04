package org.informatics.entity;

import org.informatics.exception.NotValidArgumentException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cashdesk implements Serializable {
    private Employee currEmployee;
    private List<Goods> scannedGoods;

    public Cashdesk() {
        this.scannedGoods = new ArrayList<>();
    }

    public Cashdesk(Employee currEmployee) {
        this.currEmployee = currEmployee;
    }

    public Cashdesk(Employee currEmployee, List<Goods> scannedGoods) {
        this.currEmployee = currEmployee;
        this.scannedGoods = scannedGoods;
    }

    public Employee getCurrEmployee() {
        return currEmployee;
    }

    public void setCurrEmployee(Employee currEmployee) throws NotValidArgumentException {
        if(currEmployee == null) {
            throw new NotValidArgumentException("Employee to set should be a real object.");
        }
        this.currEmployee = currEmployee;
    }

    public List<Goods> getScannedGoods() {
        return scannedGoods;
    }

    public void setScannedGoods(List<Goods> scannedGoods) throws NotValidArgumentException {
        if(scannedGoods == null || scannedGoods.size() == 0) {
            throw new NotValidArgumentException("Scanned goods can not be empty!");
        }
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