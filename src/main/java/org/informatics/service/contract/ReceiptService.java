package org.informatics.service.contract;

import org.informatics.entity.Client;
import org.informatics.entity.Employee;
import org.informatics.entity.Receipt;
import org.informatics.entity.Store;
import org.informatics.exception.ExpiredGoodsException;
import org.informatics.exception.NotEnoughBudgetException;
import org.informatics.exception.NotEnoughQuantityException;

public interface ReceiptService {
    Receipt getReceipt(Store store, Client client, Employee employee) throws NotEnoughBudgetException, ExpiredGoodsException, NotEnoughQuantityException;
}
