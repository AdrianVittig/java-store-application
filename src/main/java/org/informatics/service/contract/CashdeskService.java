package org.informatics.service.contract;

import org.informatics.entity.*;
import org.informatics.exception.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public interface CashdeskService {
    BigDecimal getTotalAmount(Store store, Client client) throws ExpiredGoodsException, NotEnoughQuantityException, NotEnoughBudgetException, NotValidArgumentException;
    boolean isCashdeskAvailable(Cashdesk cashdesk, Store store, Employee employee) throws CashdeskAlreadyBusyException, EmployeeAlreadyWorkingException;
    Cashdesk setEmployeeOnACashdesk(Store store, Cashdesk cashdesk ,Employee employee) throws CashdeskAlreadyBusyException, EmployeeAlreadyWorkingException, NotValidArgumentException;
    Map<Goods, BigDecimal> scanGoods(Map<Goods, BigDecimal> clientMap, Client client, Employee employee, Store store) throws NotEnoughQuantityException, NotEnoughBudgetException, ExpiredGoodsException, NotValidArgumentException;

    void performOperationOnCashdesk(Store store, Employee employee, Client client) throws ExpiredGoodsException, NotEnoughQuantityException, NotEnoughBudgetException, NotValidArgumentException, NotAvailableCashdeskException;
}