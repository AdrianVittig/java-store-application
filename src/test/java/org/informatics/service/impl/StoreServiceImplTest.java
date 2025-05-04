package org.informatics.service.impl;

import org.informatics.entity.*;
import org.informatics.exception.EmployeeListEmptyException;
import org.informatics.exception.NotValidArgumentException;
import org.informatics.exception.StoreDeliveredGoodsEmptyException;
import org.informatics.service.contract.GoodsService;
import org.informatics.service.contract.StoreService;
import org.informatics.util.GoodsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StoreServiceImplTest {

    Store store;
    Employee employee;
    Client client;
    List<Employee> employeeList;
    StoreService storeService;
    GoodsService goodsService;
    @BeforeEach
    void setUp() {
        store = Mockito.mock(Store.class);
        employee = Mockito.mock(Employee.class);
        client = Mockito.mock(Client.class);
        goodsService = Mockito.mock(GoodsService.class);
        employeeList = new ArrayList<>();
        storeService = new StoreServiceImpl();
        employeeList.add(employee);
    }

    @Test
    void getTotalSalaries_whenEmployeeListIsNotEmpty_thenSucceed() throws EmployeeListEmptyException, NotValidArgumentException {
        Mockito.when(employee.getSalary()).thenReturn(BigDecimal.valueOf(1200));
        Mockito.when(store.getEmployees()).thenReturn(employeeList);

        assertEquals(BigDecimal.valueOf(1200).setScale(2, RoundingMode.HALF_UP), storeService.getTotalSalaries(store));
    }

    @Test
    void getTotalSalaries_whenEmployeeListIsEmpty_thenThrowException(){
        List<Employee> employeeList1 = new ArrayList<>();

        Mockito.when(employee.getSalary()).thenReturn(BigDecimal.valueOf(1200));

        Mockito.when(store.getEmployees()).thenReturn(employeeList1);

        assertThrows(EmployeeListEmptyException.class, () -> storeService.getTotalSalaries(store));
    }

    @Test
    void getTotalSalaries_whenSalaryOfEmployeeIsEqualOrLessThanZero_thenThrowException(){

        Mockito.when(employee.getSalary()).thenReturn(BigDecimal.valueOf(-2));

        Mockito.when(store.getEmployees()).thenReturn(employeeList);

        assertThrows(NotValidArgumentException.class, () -> storeService.getTotalSalaries(store));
    }

    @Test
    void getGoodsManufacturerPrice_whenDeliveredGoodsListIsNotEmpty_thenSucceed() throws EmployeeListEmptyException, NotValidArgumentException, StoreDeliveredGoodsEmptyException {
        List<Goods> delivered = new ArrayList<>();
        Goods goods = Mockito.mock(Goods.class);
        Mockito.when(goods.getQuantity()).thenReturn(BigDecimal.ONE);
        delivered.add(goods);
        Mockito.when(goods.getManufacturerPrice()).thenReturn(BigDecimal.ONE);
        Mockito.when(store.getDeliveredGoods()).thenReturn(delivered);

        assertEquals(BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP), storeService.getGoodsManufacturerPrice(store, goods));
    }

    @Test
    void getGoodsManufacturerPrice_whenDeliveredGoodsListIsEmpty_thenThrowException() throws EmployeeListEmptyException, NotValidArgumentException, StoreDeliveredGoodsEmptyException {
        List<Goods> delivered = new ArrayList<>();
        Goods goods = Mockito.mock(Goods.class);

        Mockito.when(goods.getQuantity()).thenReturn(BigDecimal.ONE);
        Mockito.when(goods.getManufacturerPrice()).thenReturn(BigDecimal.ONE);
        Mockito.when(store.getDeliveredGoods()).thenReturn(delivered);

        assertThrows(StoreDeliveredGoodsEmptyException.class, () -> storeService.getGoodsManufacturerPrice(store, goods));
    }

    @Test
    void getTotalRevenue_whenSoldGoodsListIsEmpty_thenThrowException() {
        HashMap<Goods, BigDecimal> soldGoodsMock = new HashMap();
        Mockito.when(store.getSoldGoods()).thenReturn(soldGoodsMock);

        assertThrows(NotValidArgumentException.class, () -> storeService.getTotalRevenue(store, goodsService));
    }


    @Test
    void getTotalProfit_whenSoldGoodsListIsNotEmpty_thenSucceed() throws StoreDeliveredGoodsEmptyException, NotValidArgumentException {
        HashMap<Goods, BigDecimal> soldGoodsMock = new HashMap();
        List<Goods> delivered = new ArrayList<>();
        Goods goods = Mockito.mock(Goods.class);

        Mockito.when(goods.getExpirationDate()).thenReturn(LocalDate.of(2027,2,2));
        Mockito.when(goods.getQuantity()).thenReturn(BigDecimal.ONE);
        Mockito.when(goods.getManufacturerPrice()).thenReturn(BigDecimal.ONE);

        delivered.add(goods);
        Mockito.when(store.getDeliveredGoods()).thenReturn(delivered);
        Mockito.when(goodsService.getSellingPrice(goods, store)).thenReturn(BigDecimal.ONE);

        soldGoodsMock.put(goods, BigDecimal.TWO);

        Mockito.when(store.getSoldGoods()).thenReturn(soldGoodsMock);
        Mockito.when(storeService.getTotalRevenue(store, goodsService)).thenReturn(BigDecimal.TWO);
        Mockito.when(storeService.getGoodsManufacturerPrice(store, goods)).thenReturn(BigDecimal.ONE);

        assertEquals(BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP), storeService.getTotalProfit(store, goodsService));
    }

    @Test
    void getTotalProfit_whenSoldGoodsListIsEmpty_thenThrowException() {
        HashMap<Goods, BigDecimal> soldGoodsMock = new HashMap();
        Mockito.when(store.getSoldGoods()).thenReturn(soldGoodsMock);

        assertThrows(NotValidArgumentException.class, () -> storeService.getTotalRevenue(store, goodsService));
    }

    @Test
    void deliverGoods_whenQuantityIsEqualOrLessThanZero_thenThrowException() throws NotValidArgumentException {
        Goods goods = Mockito.mock(Goods.class);
        List<Goods> deliveredGoods = new ArrayList<>();
        deliveredGoods.add(goods);
        assertThrows(NotValidArgumentException.class, () -> storeService.deliverGoods(store, goods, BigDecimal.valueOf(-1), deliveredGoods));
    }

    @Test
    void getCountOfReceipts() {
        Store store1 = Mockito.mock(Store.class);
        List<Receipt> receipts = new ArrayList<>();
        Receipt receipt1 = Mockito.mock(Receipt.class);
        Receipt receipt2 = Mockito.mock(Receipt.class);
        receipts.add(receipt1);
        receipts.add(receipt2);
        Mockito.when(store1.getReceipts()).thenReturn(receipts);
        assertEquals(BigDecimal.valueOf(2), storeService.getCountOfReceipts(store1));
    }

    @Test
    void getTotalAmountRevenueFromReceipts() {
        Store store1 = Mockito.mock(Store.class);
        List<Receipt> receipts = new ArrayList<>();
        Receipt receipt1 = Mockito.mock(Receipt.class);
        Receipt receipt2 = Mockito.mock(Receipt.class);
        receipts.add(receipt1);
        receipts.add(receipt2);

        Mockito.when(receipt1.getTotal()).thenReturn(BigDecimal.ONE);
        Mockito.when(receipt2.getTotal()).thenReturn(BigDecimal.ONE);
        Mockito.when(store1.getReceipts()).thenReturn(receipts);

        assertEquals(BigDecimal.TWO, storeService.getTotalAmountRevenueFromReceipts(store1));
    }
}