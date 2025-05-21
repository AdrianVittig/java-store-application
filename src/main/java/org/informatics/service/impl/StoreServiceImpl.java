package org.informatics.service.impl;

import org.informatics.entity.*;
import org.informatics.exception.EmployeeListEmptyException;
import org.informatics.exception.NotValidArgumentException;
import org.informatics.exception.StoreDeliveredGoodsEmptyException;
import org.informatics.service.contract.GoodsService;
import org.informatics.service.contract.StoreService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StoreServiceImpl implements StoreService {
    @Override
    public BigDecimal getTotalSalaries(Store store) throws EmployeeListEmptyException, NotValidArgumentException {
        BigDecimal totalSalaries = BigDecimal.ZERO;
        if(store.getEmployees().isEmpty()){
            throw new EmployeeListEmptyException("Employee list is empty.");
        }
        for(Employee emp : store.getEmployees()){
            if(emp.getSalary().compareTo(BigDecimal.ZERO) <= 0){
                throw new NotValidArgumentException("Salary must be a positive value");
            }
            totalSalaries = totalSalaries.add(emp.getSalary()).setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        return totalSalaries;
    }

    @Override
    public BigDecimal getGoodsManufacturerPrice(Store store, Goods goods) throws StoreDeliveredGoodsEmptyException {
        BigDecimal totalManufacturerPrice = BigDecimal.ZERO;
        if(store.getDeliveredGoods().isEmpty()){
            throw new StoreDeliveredGoodsEmptyException("Delivered goods list is empty.");
        }
        for(Goods good : store.getDeliveredGoods()){
            totalManufacturerPrice = totalManufacturerPrice.add(goods.getQuantity().multiply(goods.getManufacturerPrice())).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return totalManufacturerPrice;
    }

    @Override
    public BigDecimal getTotalRevenue(Store store, GoodsService goodsService) throws NotValidArgumentException {
        BigDecimal totalRevenue = BigDecimal.ZERO;

        if(store.getReceipts().isEmpty()){
            throw new NotValidArgumentException("Receipts list is empty.");
        }
        for(Receipt receipt : store.getReceipts()){
            totalRevenue = totalRevenue.add(receipt.getTotal());
        }
        return totalRevenue;
    }

    @Override
    public BigDecimal getTotalProfit(Store store, GoodsService goodsService) throws NotValidArgumentException, StoreDeliveredGoodsEmptyException {
        BigDecimal totalExpenses = BigDecimal.ZERO;
        for(Map.Entry<Goods, BigDecimal> entry: store.getSoldGoods().entrySet()){
            BigDecimal priceOne = entry.getKey().getManufacturerPrice();
            BigDecimal quantity = entry.getValue();
            totalExpenses = totalExpenses.add(priceOne.multiply(quantity));
        }
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for( Receipt receipt : store.getReceipts()){
            totalRevenue = totalRevenue.add(receipt.getTotal());
        }

        return totalRevenue.subtract(totalExpenses).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public void deliverGoods(Store store, Goods goods, BigDecimal quantity, List<Goods> listOfDeliveredGoods) throws NotValidArgumentException {
        if(quantity.compareTo(BigDecimal.ZERO) <= 0){
            throw new NotValidArgumentException("Quantity must be a positive value");
        }
        goods.setQuantity(quantity);
        store.getDeliveredGoods().add(goods);
        listOfDeliveredGoods.add(goods);
    }

    @Override
    public BigDecimal getCountOfReceipts(Store store) {
        return BigDecimal.valueOf(store.getReceipts().size());
    }

    @Override
    public BigDecimal getTotalAmountRevenueFromReceipts(Store store) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(Receipt receipt : store.getReceipts()){
            totalAmount = totalAmount.add(receipt.getTotal());
        }
        return totalAmount;
    }
}