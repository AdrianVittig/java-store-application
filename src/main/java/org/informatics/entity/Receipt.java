package org.informatics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Receipt implements Serializable {
    private long id;
    private Employee employeeIssued;
    private LocalDate date;
    private LocalTime time;
    private Map<Goods, BigDecimal> goodsOnReceipt;
    private static long nextId = 0;
    private BigDecimal total;

    public Receipt() {
        this.id = ++nextId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployeeIssued() {
        return employeeIssued;
    }

    public void setEmployeeIssued(Employee employeeIssued) {
        this.employeeIssued = employeeIssued;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Map<Goods, BigDecimal> getGoodsOnReceipt() {
        return goodsOnReceipt;
    }

    public void setGoodsOnReceipt(Map<Goods, BigDecimal> goodsOnReceipt) {
        this.goodsOnReceipt = goodsOnReceipt;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", employeeIssued=" + employeeIssued +
                ", date=" + date +
                ", time=" + time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                ", goodsOnReceipt=" + goodsOnReceipt +
                ", total=" + total +
                '}';
    }
}