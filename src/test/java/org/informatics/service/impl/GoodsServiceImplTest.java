package org.informatics.service.impl;

import org.informatics.entity.Goods;
import org.informatics.entity.Store;
import org.informatics.exception.ExpiredGoodsException;
import org.informatics.exception.NotValidArgumentException;
import org.informatics.service.contract.GoodsService;
import org.informatics.util.GoodsType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GoodsServiceImplTest {

    @Test
    void getSellingPrice_whenNoExceptionIsThrown_thenSucceed() throws NotValidArgumentException {
        Goods goods = new Goods("Keyboard", BigDecimal.valueOf(200), GoodsType.NON_FOODS, LocalDate.of(2028,2,2));
        goods.setQuantity(BigDecimal.valueOf(1));

        Store store = Mockito.mock(Store.class);
        Mockito.when(store.getSurChargeNonFood()).thenReturn(BigDecimal.valueOf(0.05));

        GoodsService goodsService = new GoodsServiceImpl();
        assertEquals(BigDecimal.valueOf(210.00).setScale(2, RoundingMode.HALF_UP), goodsService.getSellingPrice(goods, store));
    }

    @Test
    void getSellingPrice_whenSurchargeIsLessOrEqualToZero_thenThrowException() throws NotValidArgumentException {
        Goods goods = new Goods("Keyboard", BigDecimal.valueOf(200), GoodsType.NON_FOODS, LocalDate.of(2028,2,2));
        goods.setQuantity(BigDecimal.valueOf(1));
        Store store = Mockito.mock(Store.class);
        Mockito.when(store.getSurChargeNonFood()).thenReturn(BigDecimal.valueOf(-5));
        Mockito.when(store.getSurChargeGroceries()).thenReturn(BigDecimal.valueOf(-5));

        GoodsService goodsService = new GoodsServiceImpl();
        assertThrows(NotValidArgumentException.class, () -> goodsService.getSellingPrice(goods, store));
    }

    @Test
    void expiredGoods_whenGoodsAreExpired_thenThrowException() throws ExpiredGoodsException {
        Goods goods = Mockito.mock(Goods.class);
        GoodsService goodsService = new GoodsServiceImpl();

        Mockito.when(goods.getExpirationDate()).thenReturn(LocalDate.of(2022,3,1));

        assertThrows(ExpiredGoodsException.class, () -> goodsService.expiredGoods(goods));
    }

    @Test
    void calculatePrice_whenCurrentDateIsBetweenTheExpirationDateAndExpirationDateMinusXDays_thenSucceed() throws ExpiredGoodsException, NotValidArgumentException {
        Goods goods = new Goods("Keyboard", BigDecimal.valueOf(100), GoodsType.NON_FOODS, LocalDate.of(2025,5,4));
        Store store = Mockito.mock(Store.class);

        Mockito.when(store.getSurChargeNonFood()).thenReturn(BigDecimal.valueOf(0.05));
        Mockito.when(store.getPercentage()).thenReturn(5.00);
        Mockito.when(store.getDaysForSale()).thenReturn(5);

        GoodsService goodsService = new GoodsServiceImpl();

        assertEquals(BigDecimal.valueOf(99.75).setScale(2, RoundingMode.HALF_UP), goodsService.calculatePrice(goods, store));
    }
}