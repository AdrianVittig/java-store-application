package org.informatics;

import org.informatics.entity.*;
import org.informatics.exception.*;
import org.informatics.service.contract.CashdeskService;
import org.informatics.service.contract.GoodsService;
import org.informatics.service.contract.ReceiptService;
import org.informatics.service.contract.StoreService;
import org.informatics.service.impl.CashdeskServiceImpl;
import org.informatics.service.impl.GoodsServiceImpl;
import org.informatics.service.impl.ReceiptServiceImpl;
import org.informatics.service.impl.StoreServiceImpl;
import org.informatics.util.GoodsType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Goods keyboard = new Goods("CM STORM", BigDecimal.valueOf(200).setScale(2, RoundingMode.HALF_UP), GoodsType.NON_FOODS, LocalDate.of(2025, 8, 28));

        Goods mouse = new Goods("Razer", BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), GoodsType.NON_FOODS, LocalDate.of(2027, 8, 12));

        Employee adrian = new Employee("Adrian", BigDecimal.valueOf(1200));
        Employee martin = new Employee("Martin", BigDecimal.valueOf(1000));
        Employee nikolai = new Employee("Nikolai", BigDecimal.valueOf(1400));

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(adrian);
        employeeList.add(martin);
        employeeList.add(nikolai);

        Client client = new Client(BigDecimal.valueOf(5000));

        Cashdesk cashdeskOne = new Cashdesk();
        Cashdesk cashdeskTwo = new Cashdesk();
        cashdeskOne.setCurrEmployee(adrian);

        List<Cashdesk> cashdeskList = new ArrayList<>();
        cashdeskList.add(cashdeskOne);

        ReceiptService receiptService = new ReceiptServiceImpl();
        GoodsService goodsService = new GoodsServiceImpl();
        CashdeskService cashdeskService = new CashdeskServiceImpl(receiptService);

        //TODO  update
//        try {
//            System.out.println(cashdeskService.canBuyGoods(client));
//        } catch (NotEnoughBudgetException e) {
//            throw new RuntimeException(e);
//        }

        Store store = new Store(employeeList, cashdeskList, BigDecimal.valueOf(5),BigDecimal.valueOf(5), 7, 20);
        List<Goods> deliveredGoods = new ArrayList<>();
//        List<Goods> soldGoods = new ArrayList<>();
        StoreService storeService = new StoreServiceImpl();

        storeService.deliverGoods(store, keyboard, BigDecimal.valueOf(10), deliveredGoods);

        //DELIVERING TO STORE
        System.out.println(storeService.getTotalSalaries(store));
        System.out.println(storeService.getGoodsManufacturerPrice(store, keyboard));

        Map<Goods, BigDecimal> clientMap = new HashMap<>();
        clientMap.put(keyboard, BigDecimal.valueOf(3));

        try {
            cashdeskService.scanGoods(clientMap, client, adrian, store);
        } catch (NotEnoughQuantityException e) {
            throw new RuntimeException(e);
        } catch (NotEnoughBudgetException e) {
            throw new RuntimeException(e);
        } catch (ExpiredGoodsException e) {
            throw new RuntimeException(e);
        }
        System.out.println(client);

        try {
            System.out.println(cashdeskService.getTotalAmount(store, client));
        } catch (ExpiredGoodsException e) {
            throw new RuntimeException(e);
        } catch (NotEnoughQuantityException e) {
            throw new RuntimeException(e);
        } catch (NotEnoughBudgetException e) {
            throw new RuntimeException(e);
        }

        System.out.println(client);

        System.out.println(client);

        System.out.println(storeService.getTotalRevenue(store, goodsService));
        System.out.println(storeService.getTotalProfit(store, goodsService));





        try {
            System.out.println(receiptService.getReceipt(store, client, adrian));
        } catch (NotEnoughBudgetException e) {
            throw new RuntimeException(e);
        } catch (ExpiredGoodsException | NotEnoughQuantityException e) {
            throw new RuntimeException(e);
        }

        System.out.println(storeService.getTotalAmountRevenueFromReceipts(store));
        System.out.println(storeService.getCountOfReceipts(store));
    }
}