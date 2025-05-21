package org.informatics.service.impl;

import org.informatics.entity.Client;
import org.informatics.entity.Employee;
import org.informatics.entity.Goods;
import org.informatics.entity.Receipt;
import org.informatics.entity.Store;
import org.informatics.exception.ExpiredGoodsException;
import org.informatics.exception.NotEnoughBudgetException;
import org.informatics.exception.NotValidArgumentException;
import org.informatics.exception.ReceiptsListIsEmptyException;
import org.informatics.service.contract.CashdeskService;
import org.informatics.service.contract.GoodsService;
import org.informatics.service.contract.ReceiptService;
import org.informatics.service.contract.FileService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReceiptServiceImpl implements ReceiptService {
    private final GoodsService goodsService;
    private final FileService fileService;

    public ReceiptServiceImpl(GoodsService goodsService, FileService fileService) {
        this.goodsService = goodsService;
        this.fileService = fileService;
    }

    @Override
    public Receipt getReceipt(Store store, Client client, Employee employee) throws NotEnoughBudgetException, ExpiredGoodsException, NotValidArgumentException {

        Receipt receipt = new Receipt();
        LocalDateTime now = LocalDateTime.now();

        receipt.setDate(now.toLocalDate());
        receipt.setTime(now.toLocalTime());
        receipt.setEmployeeIssued(employee);

        Map<Goods, BigDecimal> cart = client.getGoodsToBuy();
        receipt.setGoodsOnReceipt(cart);

        for(Map.Entry<Goods, BigDecimal> entry : cart.entrySet()) {
            Goods goods = entry.getKey();
            BigDecimal wanted = entry.getValue();

            BigDecimal sellingPrice = goodsService.calculatePrice(goods, store);
            goods.setSellingPrice(sellingPrice);
        }

        BigDecimal total = cart.entrySet().stream().map(e -> e.getKey()
                .getSellingPrice().multiply(e.getValue())).reduce(BigDecimal.ZERO, BigDecimal::add);
        receipt.setTotal(total);

        String fileName = String.format("receipt-%s.ser", store.getId(), receipt.getId());

        try{
            fileService.serializeReceipt(fileName, receipt);
            fileService.deserializeReceipt(fileName);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        client.setBudget(client.getBudget().subtract(total));

        store.getReceipts().add(receipt);
        return receipt;
    }

    @Override
    public BigDecimal getTotalAmountSoFar(Store store) throws ReceiptsListIsEmptyException {
        BigDecimal totalAmountSoFar = BigDecimal.ZERO;
        for(Receipt receipt : store.getReceipts()){
            totalAmountSoFar = totalAmountSoFar.add(receipt.getTotal());
        }
        if(store.getReceipts().isEmpty()){
            throw new ReceiptsListIsEmptyException("Receipts list is empty.");
        }
        return totalAmountSoFar;
    }

    @Override
    public BigDecimal getTotalCountSoFar(Store store) {
        BigDecimal totalCountSoFar = BigDecimal.ZERO;
        for(Receipt receipt : store.getReceipts()){
            totalCountSoFar = totalCountSoFar.add(BigDecimal.ONE);
        }
        return totalCountSoFar;
    }
}