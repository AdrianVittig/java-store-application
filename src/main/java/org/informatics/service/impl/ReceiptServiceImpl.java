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
import org.informatics.service.contract.SerializeDeserializeService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {
    List<Receipt> receipts = new ArrayList<>();
    BigDecimal totalCountSoFar = BigDecimal.ZERO;
    BigDecimal totalAmountSoFar = BigDecimal.ZERO;

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

        SerializeDeserializeService serializeDeserializeService = new SerializeDeserializeServiceImpl();
        try {
            serializeDeserializeService.serializeReceipt("receipt-"+receipt.getId()+".ser", receipt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            serializeDeserializeService.deserializeReceipt("receipt-"+receipt.getId()+".ser");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        client.setBudget(client.getBudget().subtract(clientTotal));
        receipts.add(receipt);
        store.setReceipts(receipts);
        return receipt;
    }

    @Override
    public BigDecimal getTotalAmountSoFar(Store store) {
        for(Receipt receipt : receipts){
            totalAmountSoFar = totalAmountSoFar.add(receipt.getTotal());
        }
        return totalAmountSoFar;
    }

    @Override
    public BigDecimal getTotalCountSoFar(Store store) {
        for(Receipt receipt : receipts){
            totalCountSoFar = totalCountSoFar.add(BigDecimal.ONE);
        }
        return totalCountSoFar;
    }
}