package org.informatics.service.impl;

import org.informatics.entity.Client;
import org.informatics.entity.Employee;
import org.informatics.entity.Goods;
import org.informatics.entity.Receipt;
import org.informatics.entity.Store;
import org.informatics.exception.ExpiredGoodsException;
import org.informatics.exception.NotEnoughBudgetException;
import org.informatics.service.contract.CashdeskService;
import org.informatics.service.contract.GoodsService;
import org.informatics.service.contract.ReceiptService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {
    List<Receipt> receipts = new ArrayList<>();

    @Override
    public Receipt getReceipt(Store store, Client client, Employee employee) throws NotEnoughBudgetException, ExpiredGoodsException {
        BigDecimal clientTotal = client.getTotalAmount();
        LocalDateTime dateTime = LocalDateTime.now();
        Receipt receipt = new Receipt();
        receipt.setGoodsOnReceipt(client.getGoodsToBuy());
        receipt.setDate(dateTime.toLocalDate());
        receipt.setTime(dateTime.toLocalTime());
        receipt.setEmployeeIssued(employee);
        GoodsService goodsService = new GoodsServiceImpl();

        for (Goods goods : client.getGoodsToBuy().keySet()) {
            goods.setSellingPrice(goodsService.calculatePrice(goods, store));
        }

        receipt.setTotal(clientTotal);
        client.setBudget(client.getBudget().subtract(clientTotal));
        receipts.add(receipt);
        store.setReceipts(receipts);
        return receipt;
    }
}