package org.informatics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Client implements Serializable {
    private BigDecimal budget;
    private Map<Goods, BigDecimal> goodsToBuy;
    BigDecimal totalAmount;

    public Client(BigDecimal budget) {
        this.budget = budget;
        this.goodsToBuy = new HashMap<>();
    }

    public Map<Goods, BigDecimal> getGoodsToBuy() {
        return goodsToBuy;
    }

    public void setGoodsToBuy(Map<Goods, BigDecimal> goodsToBuy) {
        this.goodsToBuy = goodsToBuy;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(budget, client.budget) && Objects.equals(goodsToBuy, client.goodsToBuy) && Objects.equals(totalAmount, client.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budget, goodsToBuy, totalAmount);
    }

    @Override
    public String toString() {
        return "Client{" +
                "budget=" + budget +
                ", goodsToBuy=" + goodsToBuy +
                ", totalAmount=" + totalAmount +
                '}';
    }
}