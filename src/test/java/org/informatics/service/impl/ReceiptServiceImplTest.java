package org.informatics.service.impl;

import org.informatics.entity.*;
import org.informatics.exception.NotValidArgumentException;
import org.informatics.exception.ReceiptsListIsEmptyException;
import org.informatics.service.contract.FileService;
import org.informatics.service.contract.GoodsService;
import org.informatics.service.contract.ReceiptService;
import org.informatics.util.GoodsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptServiceImplTest {
    Map<Goods, BigDecimal> goodsToBuy;
    Store store;
    Employee employee;
    Receipt receipt;
    ReceiptService receiptService;
    GoodsService goodsService;
    FileService fileService;

    @BeforeEach
    void setUp() {
        store = Mockito.mock(Store.class);
        Mockito.when(store.getId()).thenReturn(1L);
        employee = new Employee("Adrian", BigDecimal.valueOf(1200));
        goodsToBuy = new HashMap<>();
        receipt = Mockito.mock(Receipt.class);
        goodsService = new GoodsServiceImpl();
        fileService = new FileServiceImpl();
        receiptService = new ReceiptServiceImpl(goodsService, fileService);
    }

    @Test
    void getReceipt_whenDataIsSetCorrectly_thenSucceed() {
        Client client = Mockito.mock(Client.class);
        Receipt receipt = Mockito.mock(Receipt.class);
        Goods goods = new Goods("Keyboard", BigDecimal.ONE, GoodsType.NON_FOODS, LocalDate.of(2028,5,5));
        goods.setQuantity(BigDecimal.ONE);

        goodsToBuy.put(goods, BigDecimal.ONE);

        Mockito.when(store.getSurChargeNonFood()).thenReturn(BigDecimal.valueOf(0.05));

        Mockito.when(client.getGoodsToBuy()).thenReturn(goodsToBuy);
        Mockito.when(client.getBudget()).thenReturn(BigDecimal.TEN);
        Mockito.when(client.getTotalAmount()).thenReturn(BigDecimal.TWO);

        Mockito.when(receipt.getGoodsOnReceipt()).thenReturn(goodsToBuy);
        Mockito.when(receipt.getDate()).thenReturn(LocalDate.now());
        Mockito.when(receipt.getTime()).thenReturn(LocalTime.now());
        Mockito.when(receipt.getEmployeeIssued()).thenReturn(employee);
        Mockito.when(receipt.getTotal()).thenReturn(BigDecimal.TWO);
        Mockito.when(receipt.getTotal()).thenReturn(BigDecimal.ONE);

        assertDoesNotThrow(() -> receiptService.getReceipt(store, client, employee));
    }

    @Test
    void getTotalAmountSoFar_whenReceiptsListIsNotEmpty_thenSucceed() throws ReceiptsListIsEmptyException {

        Mockito.when(receipt.getGoodsOnReceipt()).thenReturn(goodsToBuy);
        Mockito.when(receipt.getDate()).thenReturn(LocalDate.now());
        Mockito.when(receipt.getTime()).thenReturn(LocalTime.now());
        Mockito.when(receipt.getEmployeeIssued()).thenReturn(employee);
        Mockito.when(receipt.getTotal()).thenReturn(BigDecimal.TWO);

        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receipt);

        Mockito.when(store.getReceipts()).thenReturn(receipts);

        assertEquals(BigDecimal.TWO, receiptService.getTotalAmountSoFar(store));
    }

    @Test
    void getTotalAmountSoFar_whenReceiptsListIsEmpty_thenThrowException() throws ReceiptsListIsEmptyException {

        Mockito.when(receipt.getGoodsOnReceipt()).thenReturn(goodsToBuy);
        Mockito.when(receipt.getDate()).thenReturn(LocalDate.now());
        Mockito.when(receipt.getTime()).thenReturn(LocalTime.now());
        Mockito.when(receipt.getEmployeeIssued()).thenReturn(employee);
        Mockito.when(receipt.getTotal()).thenReturn(BigDecimal.TWO);
        Mockito.when(receipt.getTotal()).thenReturn(BigDecimal.ONE);

        assertThrows(ReceiptsListIsEmptyException.class, () -> receiptService.getTotalAmountSoFar(store));
    }

    @Test
    void getTotalCountSoFar() {
        Mockito.when(receipt.getGoodsOnReceipt()).thenReturn(goodsToBuy);
        Mockito.when(receipt.getDate()).thenReturn(LocalDate.now());
        Mockito.when(receipt.getTime()).thenReturn(LocalTime.now());
        Mockito.when(receipt.getEmployeeIssued()).thenReturn(employee);
        Mockito.when(receipt.getTotal()).thenReturn(BigDecimal.TWO);

        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receipt);
        Mockito.when(store.getReceipts()).thenReturn(receipts);

        assertEquals(BigDecimal.ONE, receiptService.getTotalCountSoFar(store));
    }
}