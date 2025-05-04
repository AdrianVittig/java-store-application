package org.informatics.service.contract;

import org.informatics.entity.*;
import org.informatics.exception.EmployeeListEmptyException;
import org.informatics.exception.NotValidArgumentException;
import org.informatics.exception.StoreDeliveredGoodsEmptyException;

import java.math.BigDecimal;
import java.util.List;

public interface StoreService {
    BigDecimal getTotalSalaries(Store store) throws EmployeeListEmptyException, NotValidArgumentException;
    BigDecimal getGoodsManufacturerPrice(Store store, Goods goods) throws StoreDeliveredGoodsEmptyException;
    BigDecimal getTotalRevenue(Store store, GoodsService goodsService) throws NotValidArgumentException;
    BigDecimal getTotalProfit(Store store, GoodsService goodsService) throws NotValidArgumentException, StoreDeliveredGoodsEmptyException;
    void deliverGoods(Store store, Goods goods, BigDecimal quantity, List<Goods> listOfDeliveredGoods) throws NotValidArgumentException;
    BigDecimal getCountOfReceipts(Store store);
    BigDecimal getTotalAmountRevenueFromReceipts(Store store);
}