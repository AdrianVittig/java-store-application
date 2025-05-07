package org.informatics.service.impl;

import org.informatics.entity.*;
import org.informatics.exception.*;
import org.informatics.service.contract.CashdeskService;
import org.informatics.service.contract.GoodsService;
import org.informatics.service.contract.ReceiptService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashdeskServiceImpl implements CashdeskService {
    private final ReceiptService receiptService;
    GoodsService goodsService = new GoodsServiceImpl();
    public Map<Goods, BigDecimal> scannedGoods = new HashMap<>();

    public CashdeskServiceImpl(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    public CashdeskServiceImpl(ReceiptService receiptService, GoodsService goodsService) {
        this.receiptService = receiptService;
        this.goodsService = goodsService;
    }

    @Override
    public void performOperationOnCashdesk(Store store, Employee employee, Client client) throws ExpiredGoodsException, NotEnoughQuantityException, NotEnoughBudgetException, NotValidArgumentException, NotAvailableCashdeskException {
        if(store.availableCashdesk()){
            scanGoods(client.getGoodsToBuy(), client, employee, store);
            receiptService.getReceipt(store, client, employee);
        }
        if(!store.availableCashdesk()){
            throw new NotAvailableCashdeskException("There is not available cashdesk at the moment.");
        }
        return;
    }

    @Override
    public boolean isCashdeskAvailable(Cashdesk cashdesk, Store store, Employee employee) throws CashdeskAlreadyBusyException, EmployeeAlreadyWorkingException {
        return !isCashdeskBusy(cashdesk) && !employeeAlreadyWorking(store, employee);
    }

    @Override
    public Cashdesk setEmployeeOnACashdesk(Store store, Cashdesk cashdesk, Employee employee) throws CashdeskAlreadyBusyException, EmployeeAlreadyWorkingException, NotValidArgumentException {
        if (!isCashdeskBusy(cashdesk) && !employeeAlreadyWorking(store, employee)) {
            cashdesk.setCurrEmployee(employee);
        }
        return cashdesk;
    }

    @Override
    public Map<Goods, BigDecimal> scanGoods(Map<Goods, BigDecimal> unscannedGoods, Client client, Employee employee, Store store) throws ExpiredGoodsException, NotEnoughQuantityException, NotEnoughBudgetException, NotValidArgumentException {
        for (Map.Entry<Goods, BigDecimal> entry : unscannedGoods.entrySet()) {
            Goods goods = entry.getKey();
            BigDecimal quantity = entry.getValue();

            if (quantity.compareTo(goods.getQuantity()) > 0) {
                throw new NotEnoughQuantityException(quantity.subtract(goods.getQuantity()), goods);
            }

            if (goods.getExpirationDate().isBefore(LocalDate.now())) {
                throw new ExpiredGoodsException("Expired goods!");
            }

            if (goods.getQuantity().compareTo(quantity) >= 0) {
                goods.setQuantity(goods.getQuantity().subtract(quantity));
                scannedGoods.computeIfAbsent(goods, k -> quantity);
                goods.setSellingPrice(goodsService.calculatePrice(goods, store));
            }

            this.canBuyGoods(client, this.getTotalAmount(store, client));

            client.setGoodsToBuy(scannedGoods);
        }
        store.addSoldGoods(scannedGoods);

        return scannedGoods;
    }

    @Override
    public BigDecimal getTotalAmount(Store store, Client client) throws ExpiredGoodsException, NotEnoughBudgetException, NotValidArgumentException, NotEnoughQuantityException {
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Goods, BigDecimal> entry : client.getGoodsToBuy().entrySet()) {
            if(entry.getValue().compareTo(BigDecimal.ZERO) > 0) {
                total = total.add(goodsService.calculatePrice(entry.getKey(), store).multiply(entry.getValue()));
                this.canBuyGoods(client, total);
            }
            if(entry.getValue().compareTo(client.getGoodsToBuy().get(entry.getKey())) < 0) {
                throw new NotEnoughQuantityException(entry.getValue().subtract(client.getGoodsToBuy().get(entry.getKey())), entry.getKey());
            }
        }
        client.setTotalAmount(total);
        if(client.getBudget().compareTo(total) < 0) {
            throw new NotEnoughBudgetException("Not enough budget!");
        }

        return total;
    }

    private boolean canBuyGoods(Client client, BigDecimal total) {
        return client.getBudget().compareTo(total) >= 0;
    }

    private void validateCanBuyGoods(Client client, BigDecimal total) throws NotEnoughBudgetException {
        if (!canBuyGoods(client, client.getTotalAmount())) {
            throw new NotEnoughBudgetException("Not enough budget! You can not buy the goods.");
        }
    }

    private boolean isCashdeskBusy(Cashdesk cashdesk) throws CashdeskAlreadyBusyException {
        if (cashdesk.getCurrEmployee() != null) {
            throw new CashdeskAlreadyBusyException("Cashdesk already busy!");
        }
        return false;
    }

    private boolean employeeAlreadyWorking(Store store, Employee employee) throws EmployeeAlreadyWorkingException {
        for (Cashdesk cashdesk : store.getCashdesks()) {
            if (cashdesk.getCurrEmployee() == employee) {
                throw new EmployeeAlreadyWorkingException("Employee already working!");
            }
        }
        return false;
    }
}