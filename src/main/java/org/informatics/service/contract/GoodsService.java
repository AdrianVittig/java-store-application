package org.informatics.service.contract;

import org.informatics.entity.Goods;
import org.informatics.entity.Store;

import org.informatics.exception.ExpiredGoodsException;
import org.informatics.exception.NotValidArgumentException;

import java.math.BigDecimal;

public interface GoodsService {
    BigDecimal getSellingPrice(Goods goods, Store store) throws NotValidArgumentException;
    boolean expiredGoods(Goods goods) throws ExpiredGoodsException;
    BigDecimal calculatePrice(Goods goods, Store store) throws ExpiredGoodsException, NotValidArgumentException;
}