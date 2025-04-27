package org.informatics.service.contract;

import org.informatics.entity.*;

import java.math.BigDecimal;
import java.util.List;

public interface StoreService {
    BigDecimal getTotalSalaries(Store store);
    BigDecimal getGoodsManufacturerPrice(Store store, Goods goods);
    BigDecimal getTotalRevenue(Store store, GoodsService goodsService);
    BigDecimal getTotalProfit(Store store, GoodsService goodsService);
    void deliverGoods(Store store, Goods goods, BigDecimal quantity, List<Goods> listOfDeliveredGoods);
    BigDecimal getCountOfReceipts(Store store);
    BigDecimal getTotalAmountRevenueFromReceipts(Store store);
}