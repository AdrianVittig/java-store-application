package org.informatics.entity;

import org.informatics.exception.NotValidArgumentException;
import org.informatics.util.GoodsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    Store store;
    Store store2;
    Store store3;
    Store store4;

    Employee employee;
    List<Employee> employees;
    List<Employee> employees2;

    Cashdesk cashdesk;
    Cashdesk cashdesk2;
    List<Cashdesk> cashdesks;
    List<Cashdesk> cashdesks2;

    Goods goods;
    List<Goods> deliveredGoods;
    List<Goods> deliveredGoods2;

    HashMap<Goods, BigDecimal> soldGoods;
    HashMap<Goods, BigDecimal> soldGoods2;
    HashMap<Goods, BigDecimal> soldGoods3;

    Receipt receipt;
    List<Receipt> receipts;
    List<Receipt> receipts2;
    @BeforeEach
    void setUp() throws NotValidArgumentException {
        store = new Store();
        store2 = new Store(5);
        store4 = new Store();


        employee = new Employee("Adrian", BigDecimal.valueOf(1000.00));
        employees = new ArrayList<>();
        employees2 = new ArrayList<>();
        employees.add(employee);

        cashdesk = new Cashdesk(employee, null);
        cashdesks = new ArrayList<>();
        cashdesks.add(cashdesk);
        cashdesk2 = new Cashdesk(null, null);
        cashdesks2 = new ArrayList<>();

        store3 = new Store(employees, cashdesks, BigDecimal.valueOf(5),BigDecimal.valueOf(5), 7, 5);

        goods = new Goods("Apple", BigDecimal.valueOf(5), GoodsType.NON_FOODS, LocalDate.of(2025, 1, 12));
        deliveredGoods = new ArrayList<>();
        deliveredGoods2 = new ArrayList<>();
        deliveredGoods.add(goods);

        soldGoods = new HashMap<>();
        soldGoods2 = new HashMap<>();
        soldGoods.put(goods, BigDecimal.valueOf(4));
        soldGoods2.put(goods, BigDecimal.valueOf(4));
        soldGoods3 = new HashMap<>();

        receipt = new Receipt();
        receipts = new ArrayList<>();
        receipts2 = new ArrayList<>();
        receipts.add(receipt);
        cashdesk.setCurrEmployee(employee);

        try {
            store.setCashdesks(cashdesks);
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void setPercentage() {
        try {
            store.setPercentage(10);
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
        assertEquals(10, store.getPercentage());
    }

    @Test
    void testPercentageIfPercentageIsNegative(){
        assertThrows(NotValidArgumentException.class, () -> {
            store.setPercentage(-10);
        });
    }

    @Test
    void getPercentage() {
        assertEquals(5, store2.getPercentage());
    }

    @Test
    void setDaysForSale() {
        try{
            store.setDaysForSale(4);
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4, store.getDaysForSale());
    }

    @Test
    void testDaysForsaleIfIntergerIsNegative(){
        assertThrows(NotValidArgumentException.class, () -> {
            store.setDaysForSale(-10);
        });
    }

    @Test
    void setSurChargeGroceries() {
        try {
            store.setSurChargeGroceries(BigDecimal.valueOf(5));
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(BigDecimal.valueOf(5), store.getSurChargeGroceries());
    }

    @Test
    void testSetSurchargeIfSurchargeGroceriesIsNegative(){
        assertThrows(NotValidArgumentException.class, () -> {
            store.setSurChargeGroceries(BigDecimal.valueOf(-5));
        });
    }

    @Test
    void testSetSurchargeIfSurchargeIsNegative(){
        assertThrows(NotValidArgumentException.class, () -> {
            store.setSurCharge(BigDecimal.valueOf(-5));
        });
    }

    @Test
    void setEmployeesIfEmployeesListIsNotEmpty() {
        try {
            store.setEmployees(employees);
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(employees, store.getEmployees());
    }

    @Test
    void setEmployeesIfEmployeesListIsEmpty() {
        assertThrows(NotValidArgumentException.class, () -> {
            store.setEmployees(employees2);
        });
    }

    @Test
    void setCashdesksIfCashdesksListIsNotEmpty() {
        assertEquals(cashdesks, store.getCashdesks());
    }

    @Test
    void setCashdesksIfCashdesksListIsEmpty() {
        assertThrows(NotValidArgumentException.class, () -> {
            store.setCashdesks(cashdesks2);
        });
    }

    @Test
    void setDeliveredGoodsIfDeliveredGoodsIsNotEmpty() {
        try {
            store.setDeliveredGoods(deliveredGoods);
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(deliveredGoods, store.getDeliveredGoods());
    }

    @Test
    void setDeliveredGoodsIfDeliveredGoodsIsEmpty() {
        assertThrows(NotValidArgumentException.class, () -> {
            store.setDeliveredGoods(deliveredGoods2);
        });
    }

    @Test
    void setSoldGoodsIfSoldGoodsIsNotEmpty() {
        try {
            store.setSoldGoods(soldGoods);
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(soldGoods, store.getSoldGoods());
    }

    @Test
    void setSoldGoodsIfSoldGoodsIsEmpty() {
        assertThrows(NotValidArgumentException.class, () -> {
            store.setSoldGoods(soldGoods3);
        });
    }

    @Test
    void setReceiptsIfReceiptsListIsNotEmpty() {
        try {
            store.setReceipts(receipts);
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(receipts, store.getReceipts());
    }

    @Test
    void setReceiptsIfReceiptsListIsEmpty() {
        assertThrows(NotValidArgumentException.class, () -> {
            store.setReceipts(receipts2);
        });
    }

    @Test
    void setSurCharge() {
        try {
            store.setSurCharge(BigDecimal.valueOf(5));
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(BigDecimal.valueOf(5), store.getSurCharge());
    }

    @Test
    void testSurchargeIfSurchargeIsNegative(){
        assertThrows(NotValidArgumentException.class, () -> {
            store.setSurCharge(BigDecimal.valueOf(-5));
        });
    }

    @Test
    void setSurChargeNonFood() {
        try {
            store.setSurChargeNonFood(BigDecimal.valueOf(5));
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(BigDecimal.valueOf(5), store.getSurChargeNonFood());
    }

    @Test
    void testSurchargeNonFoodIfSurchargeIsNegative(){
        assertThrows(NotValidArgumentException.class, () -> {
            store.setSurChargeNonFood(BigDecimal.valueOf(-5));
        });
    }

    @Test
    void testIfStorePercentageArgumentIsDividedBy100(){
        assertEquals(0.05, store3.getPercentage());
    }

    @Test
    void testIfStoreSurchargeNonFoodArgumentIsDividedBy100(){
        assertEquals(BigDecimal.valueOf(0.05), store3.getSurChargeNonFood());
    }

    @Test
    void testDaysForSaleGet(){
        assertEquals(7, store3.getDaysForSale());
    }

    @Test
    void testAddSoldGoodsIfEqualsStoreSoldGoods() {
        store.addSoldGoods(soldGoods);
        store.addSoldGoods(soldGoods2);
        assertEquals(1, store.getSoldGoods().size());
        assertEquals(BigDecimal.valueOf(8), store.getSoldGoods().get(goods));
    }

    @Test
    void testGetEmployees(){
        assertEquals(employees, store3.getEmployees());
    }

    @Test
    void testGetCashdesks(){
        assertEquals(cashdesks, store3.getCashdesks());
    }

    @Test
    void availableCashdesk() throws NotValidArgumentException {
        assertDoesNotThrow(() -> {
            store.availableCashdesk();
        });
    }

    @Test
    void testAvailableCashdesksIfSCurrEmployeeIsNull() throws NotValidArgumentException {
        List<Cashdesk> noEmployeesCashDesks = new ArrayList<>();
        noEmployeesCashDesks.add(new Cashdesk(null, null));

        store4.setCashdesks(noEmployeesCashDesks);

        assertThrows(NotValidArgumentException.class, () -> {
            store4.availableCashdesk();
        });

    }

}