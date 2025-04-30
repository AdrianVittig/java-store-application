package org.informatics.entity;

import org.informatics.exception.NotValidArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashdeskTest {
    Cashdesk cashdesk;
    Cashdesk cashdeskWithNoEmployeeSet;
    Employee employee;
    List<Goods> scannedGoods;
    @BeforeEach
    void setUp() {
        employee = new Employee("Adrian", BigDecimal.valueOf(1200.00));
        cashdesk = new Cashdesk(employee, new ArrayList<>());
        scannedGoods = new ArrayList<>();
        cashdeskWithNoEmployeeSet = new Cashdesk();
    }

    @Test
    void getCurrEmployee() {
        assertEquals(employee, cashdesk.getCurrEmployee());
    }

    @Test
    void setCurrEmployee() {
        try {
            cashdeskWithNoEmployeeSet.setCurrEmployee(employee);
        } catch (NotValidArgumentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(employee, cashdeskWithNoEmployeeSet.getCurrEmployee());
    }

    @Test
    void setCurrEmployeeIfArgumentIsNull() {
        assertThrows(NotValidArgumentException.class, () -> cashdesk.setCurrEmployee(null));
    }

    @Test
    void getScannedGoods() {
        assertEquals(scannedGoods , cashdesk.getScannedGoods());
    }

    @Test
    void setScannedGoods() {
        assertThrows(NotValidArgumentException.class, () -> cashdesk.setScannedGoods(null));
    }
}