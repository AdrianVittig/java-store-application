package org.informatics.service.impl;

import org.informatics.entity.Goods;
import org.informatics.entity.Store;
import org.informatics.exception.ExpiredGoodsException;
import org.informatics.exception.NotValidArgumentException;
import org.informatics.service.contract.GoodsService;
import org.informatics.util.GoodsType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GoodsServiceImpl implements GoodsService {
    @Override
    public BigDecimal getSellingPrice(Goods goods, Store store) throws NotValidArgumentException {
        if(goods.getGoodsType().equals(GoodsType.GROCERIES)){
            if(store.getSurChargeGroceries().compareTo(BigDecimal.ZERO) <= 0){
                throw new NotValidArgumentException("Surcharge must be positive value");
            }
            return goods.getManufacturerPrice().add(goods.getManufacturerPrice().multiply(store.getSurCharge())).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        else if(goods.getGoodsType().equals(GoodsType.NON_FOODS)){
            if(store.getSurChargeNonFood().compareTo(BigDecimal.ZERO) <= 0){
                throw new NotValidArgumentException("Surcharge must be positive value");
            }
            return goods.getManufacturerPrice().add(goods.getManufacturerPrice().multiply(store.getSurChargeNonFood())).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return goods.getManufacturerPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public boolean expiredGoods(Goods goods) throws ExpiredGoodsException {
        if(LocalDate.now().isAfter(goods.getExpirationDate())){
            throw new ExpiredGoodsException("Expired goods! You can not buy!");
        }
        return false;
    }

    @Override
    public BigDecimal calculatePrice(Goods goods, Store store) throws ExpiredGoodsException, NotValidArgumentException {
        LocalDate expirationDate = goods.getExpirationDate();
        LocalDate discountDay = goods.getExpirationDate().minusDays(store.getDaysForSale());

        if(LocalDate.now().isAfter(discountDay) && LocalDate.now().isBefore(expirationDate) || LocalDate.now().equals(expirationDate) || LocalDate.now().equals(discountDay)){
            if(goods.getGoodsType().equals(GoodsType.NON_FOODS)){
                return getSellingPrice(goods, store).subtract((getSellingPrice(goods, store).multiply(store.getSurChargeNonFood()))).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            return getSellingPrice(goods, store).subtract((getSellingPrice(goods, store).multiply(store.getSurChargeGroceries()))).setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        if(LocalDate.now().isAfter(expirationDate)){
            throw new ExpiredGoodsException("Expired goods! You can not buy!");
        }

        return getSellingPrice(goods, store).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}