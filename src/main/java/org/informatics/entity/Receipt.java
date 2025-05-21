package org.informatics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return id == receipt.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receipt #").append(id).append(" Date: ").append(date).append(" Time: ").append(time.format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .append("\nIssued by: ").append(employeeIssued).append("\n").append("Items: ");
        for(var entry : goodsOnReceipt.entrySet()) {
            Goods goods = entry.getKey();
            BigDecimal quantity = entry.getValue();
            sb.append(String.format(
                    "\n  - %s  x %s  @ %s each = %s",
                    goods.getName(),
                    quantity,
                    goods.getSellingPrice(),
                    goods.getSellingPrice().multiply(quantity)
            ));
        }
        sb.append("\n Total: ").append(total);
        return sb.toString();
    }
}