package org.informatics.entity;

import org.informatics.util.GoodsType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Goods implements Serializable {
    private long id;
    private String name;
    private BigDecimal quantity;
    private BigDecimal manufacturerPrice;
    private GoodsType goodsType;
    private LocalDate expirationDate;
    private static long nextId = 1000;
    private BigDecimal sellingPrice;

    public Goods(String name, BigDecimal manufacturerPrice, GoodsType goodsType, LocalDate expirationDate) {
        this.id = ++nextId;
        this.name = name;
        this.manufacturerPrice = manufacturerPrice;
        this.goodsType = goodsType;
        this.expirationDate = expirationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getManufacturerPrice() {
        return manufacturerPrice;
    }

    public void setManufacturerPrice(BigDecimal manufacturerPrice) {
        this.manufacturerPrice = manufacturerPrice;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public static long getNextId() {
        return nextId;
    }

    public static void setNextId(long nextId) {
        Goods.nextId = nextId;
    }


    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return id == goods.id && Objects.equals(name, goods.name) && goodsType == goods.goodsType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, goodsType);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", manufacturerPrice=" + manufacturerPrice +
                ", goodsType=" + goodsType +
                ", expirationDate=" + expirationDate +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}