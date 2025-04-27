package org.informatics.exception;

import org.informatics.entity.Goods;

import java.math.BigDecimal;

public class NotEnoughQuantityException extends Exception {
    BigDecimal insufficientQuantity = BigDecimal.ZERO;
    Goods goods;
    public NotEnoughQuantityException(BigDecimal insufficientQuantity, Goods goods) {
        this.insufficientQuantity = insufficientQuantity;
        this.goods = goods;
    }

    public BigDecimal getInsufficientQuantity() {
        return insufficientQuantity;
    }

    public void setInsufficientQuantity(BigDecimal insufficientQuantity) {
        this.insufficientQuantity = insufficientQuantity;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "NotEnoughQuantityException{" +
                "insufficientQuantity=" + insufficientQuantity +
                ", goods=" + goods.getName() +
                "} " + super.toString();
    }
}