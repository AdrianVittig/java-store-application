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
        List<Employee> emps = store.getEmployees();
        if (emps == null || emps.isEmpty()) {
            throw new EmployeeListEmptyException("Employee list is empty.");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Employee emp : emps) {
            BigDecimal sal = emp.getSalary();
            if (sal == null || sal.compareTo(BigDecimal.ZERO) <= 0) {
                throw new NotValidArgumentException("Salary must be a positive value");
            }
            total = total.add(sal).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return total;
    }

    @Override
    public BigDecimal getGoodsManufacturerPrice(Store store, Goods goods) throws StoreDeliveredGoodsEmptyException {
        Map<Goods, BigDecimal> sold = store.getSoldGoods();
        if (sold == null || sold.isEmpty()) {
            throw new StoreDeliveredGoodsEmptyException("Sold goods list is empty.");
        }
        BigDecimal qty = sold.getOrDefault(goods, BigDecimal.ZERO);
        return goods.getManufacturerPrice().multiply(qty).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal getTotalRevenue(Store store, GoodsService goodsService) throws NotValidArgumentException {
        Map<Goods, BigDecimal> sold = store.getSoldGoods();
        if (sold == null || sold.isEmpty()) {
            throw new NotValidArgumentException("Sold goods list is empty.");
        }
        BigDecimal revenue = BigDecimal.ZERO;
        for (Map.Entry<Goods, BigDecimal> entry : sold.entrySet()) {
            BigDecimal qty = entry.getValue();
            if (qty.compareTo(BigDecimal.ZERO) <= 0) {
                throw new NotValidArgumentException("Quantity must be a positive value");
            }
            BigDecimal price = goodsService.getSellingPrice(entry.getKey(), store);
            revenue = revenue.add(price.multiply(qty)).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return revenue;
    }

    @Override
    public BigDecimal getTotalProfit(Store store, GoodsService goodsService)
            throws StoreDeliveredGoodsEmptyException, NotValidArgumentException {
        BigDecimal revenue = getTotalRevenue(store, goodsService);
        BigDecimal costTotal = BigDecimal.ZERO;
        for (Goods g : store.getSoldGoods().keySet()) {
            costTotal = costTotal.add(getGoodsManufacturerPrice(store, g));
        }
        return revenue.subtract(costTotal).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public void deliverGoods(Store store, Goods goods, BigDecimal quantity, List<Goods> listOfDeliveredGoods)
            throws NotValidArgumentException {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NotValidArgumentException("Quantity must be a positive value");
        }
        goods.setQuantity(quantity);
        store.getDeliveredGoods().add(goods);
        store.getSoldGoods().put(goods, BigDecimal.ZERO);
//        listOfDeliveredGoods.add(goods);
    }

    @Override
    public BigDecimal getCountOfReceipts(Store store) {
        return BigDecimal.valueOf(store.getReceipts().size());
    }

    @Override
    public BigDecimal getTotalAmountRevenueFromReceipts(Store store) {
        BigDecimal total = BigDecimal.ZERO;
        for (Receipt r : store.getReceipts()) {
            total = total.add(r.getTotal());
        }
        return total;
    }
}