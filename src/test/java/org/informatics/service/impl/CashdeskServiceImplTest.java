package org.informatics.service.impl;

import org.informatics.entity.Client;
import org.informatics.entity.Employee;
import org.informatics.entity.Goods;
import org.informatics.entity.Store;
import org.informatics.exception.*;
import org.informatics.service.contract.CashdeskService;
import org.informatics.service.contract.GoodsService;
import org.informatics.service.contract.ReceiptService;
import org.informatics.service.contract.StoreService;
import org.informatics.util.GoodsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CashdeskServiceImplTest {

    CashdeskService cashdeskService;
    StoreService storeService;
    Store store;
    Client client;
    Employee employee;
    ReceiptService receiptService;
    HashMap<Goods,BigDecimal> clientMap;

    @BeforeEach
    void setUp() {
        receiptService = new ReceiptServiceImpl();
        cashdeskService = new CashdeskServiceImpl(receiptService);
        storeService = new StoreServiceImpl();
        store = new Store();
        clientMap = new HashMap<>();

        client = new Client(BigDecimal.valueOf(200));
        employee = new Employee("Adrian", BigDecimal.valueOf(950));
    }

    @Test
    void whenCashdeskIsAvailableAndClientHasValidGoods_thenPerformOperation() throws ExpiredGoodsException, NotEnoughQuantityException, NotEnoughBudgetException, NotValidArgumentException, NotAvailableCashdeskException {
        Goods keyboard = new Goods("Keyboard", BigDecimal.valueOf(500), GoodsType.NON_FOODS,
                LocalDate.of(2028, 2,2));
        keyboard.setQuantity(BigDecimal.valueOf(5));

        client.setGoodsToBuy(clientMap);

        Store store1 = Mockito.mock(Store.class);
        Client client1 = Mockito.mock(Client.class);
        Employee employee1 = Mockito.mock(Employee.class);
        ReceiptService receiptService1 = Mockito.mock(ReceiptService.class);

        CashdeskService cashdeskService1 = new CashdeskServiceImpl(receiptService1);

        Mockito.when(store1.availableCashdesk()).thenReturn(true);
        Mockito.when(client1.getGoodsToBuy()).thenReturn(clientMap);

        cashdeskService1.performOperationOnCashdesk(store1, employee1, client1);
    }

    @Test
    void whenCashdeskIsNotAvailableAndClientHasValidGoods_thenDoNotPerformOperation() throws ExpiredGoodsException, NotEnoughQuantityException, NotEnoughBudgetException, NotValidArgumentException {
        Goods keyboard = new Goods("Keyboard", BigDecimal.valueOf(500), GoodsType.NON_FOODS,
                LocalDate.of(2028, 2,2));
        keyboard.setQuantity(BigDecimal.valueOf(5));

        Store store1 = Mockito.mock(Store.class);
        Client client1 = Mockito.mock(Client.class);
        ReceiptService receiptService1 = Mockito.mock(ReceiptService.class);

        CashdeskService cashdeskService1 = new CashdeskServiceImpl(receiptService1);

        Mockito.when(store1.availableCashdesk()).thenReturn(false);
        Mockito.when(client1.getGoodsToBuy()).thenReturn(clientMap);

        assertThrows(NotAvailableCashdeskException.class, () -> cashdeskService1.performOperationOnCashdesk(store1, Mockito.mock(Employee.class), Mockito.mock(Client.class)));
    }

    @Test
    void scanGoods_WhenGoodsAreValidAndClientHasEnoughBudget_thenSucceed() throws ExpiredGoodsException, NotEnoughQuantityException, NotEnoughBudgetException, NotValidArgumentException {
        Goods keyboard = new Goods("Keyboard", BigDecimal.valueOf(500),
                GoodsType.NON_FOODS,LocalDate.of(2028, 2,2));
        keyboard.setQuantity(BigDecimal.valueOf(5));

        Map<Goods, BigDecimal> unscannedGoods = new HashMap<>();
        unscannedGoods.put(keyboard, BigDecimal.valueOf(2));

        Store store1 = Mockito.mock(Store.class);

        Employee employee1 = Mockito.mock(Employee.class);

        Client client1 = Mockito.mock(Client.class);
        Mockito.when(client1.getBudget()).thenReturn(BigDecimal.valueOf(500));
        client1.setGoodsToBuy(unscannedGoods);

        ReceiptService receiptService1 = Mockito.mock(ReceiptService.class);
        GoodsService goodsService1 = Mockito.mock(GoodsService.class);
        CashdeskService cashdeskService1 = new CashdeskServiceImpl(receiptService1, goodsService1);


        Mockito.when(goodsService1.getSellingPrice(keyboard, store1)).thenReturn(new BigDecimal(250));

        Map<Goods, BigDecimal> res = cashdeskService1.scanGoods(unscannedGoods, client1, employee1, store1);

        assertTrue(res.containsKey(keyboard));
        assertEquals(res, unscannedGoods);
    }

    @Test
    void getTotalAmount_WhenValidGoodsAndClientHasBudget_ThenSucceed() throws ExpiredGoodsException, NotValidArgumentException, NotEnoughQuantityException, NotEnoughBudgetException {
        Map<Goods, BigDecimal> goodsToBuy = new HashMap<>();
        Store storeMocked = Mockito.mock(Store.class);
        Client clientMocked = Mockito.mock(Client.class);
        Goods goodsMocked = Mockito.mock(Goods.class);
        goodsToBuy.put(goodsMocked, BigDecimal.valueOf(2));

        GoodsService goodsServiceMock = Mockito.mock(GoodsService.class);
        ReceiptService receiptService1 = Mockito.mock(ReceiptService.class);

        Mockito.when(clientMocked.getGoodsToBuy()).thenReturn(goodsToBuy);
        Mockito.when(clientMocked.getBudget()).thenReturn(BigDecimal.valueOf(500));

        Mockito.when(goodsServiceMock.calculatePrice(goodsMocked, storeMocked)).thenReturn(new BigDecimal(250));
        CashdeskService cashdeskServiceMock = new CashdeskServiceImpl(receiptService1, goodsServiceMock);

        assertEquals(BigDecimal.valueOf(500), cashdeskServiceMock.getTotalAmount(storeMocked, clientMocked));
    }


    @Test
    void getTotalAmount_whenBudgetIsNotEnough_ThenThrowException() throws ExpiredGoodsException, NotValidArgumentException, NotEnoughQuantityException, NotEnoughBudgetException {
        Map<Goods, BigDecimal> goodsToBuy = new HashMap<>();
        Store storeMocked = Mockito.mock(Store.class);
        Client clientMocked = Mockito.mock(Client.class);
        Goods goodsMocked = Mockito.mock(Goods.class);
        goodsToBuy.put(goodsMocked, BigDecimal.valueOf(2));

        GoodsService goodsServiceMock = Mockito.mock(GoodsService.class);
        ReceiptService receiptService1 = Mockito.mock(ReceiptService.class);

        Mockito.when(clientMocked.getGoodsToBuy()).thenReturn(goodsToBuy);
        Mockito.when(clientMocked.getBudget()).thenReturn(BigDecimal.valueOf(200));

        Mockito.when(goodsServiceMock.calculatePrice(goodsMocked, storeMocked)).thenReturn(new BigDecimal(250));
        CashdeskService cashdeskServiceMock = new CashdeskServiceImpl(receiptService1, goodsServiceMock);
        assertThrows(NotEnoughBudgetException.class, () -> {
            cashdeskServiceMock.getTotalAmount(storeMocked, clientMocked);
        });
    }
}