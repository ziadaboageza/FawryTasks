package org.example.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountManagerImplTest {

    private AccountManagerImpl accountManager;
    private Customer customer;

    @BeforeEach
    void setUp() {
        accountManager = new AccountManagerImpl();
        customer = new Customer();
        customer.setBalance(500);
        customer.setCreditAllowed(false);
        customer.setVip(false);
    }

    // Test cases for deposit

    @Test
    public void givenValidAmountAndCustomer_whenDeposit_thenBalanceIncreases() {
        accountManager.deposit(customer, 100);
        assertEquals(600, customer.getBalance());
    }

    @Test
    public void givenZeroAmountAndCustomer_whenDeposit_thenBalanceUnchanged(){
        accountManager.deposit(customer, 0);
        assertEquals(500, customer.getBalance());  // Balance should remain unchanged
    }

    @Test
    public void givenLargeAmountAndCustomer_whenDeposit_thenBalanceIncreases(){
        accountManager.deposit(customer, 1000000);
        assertEquals(1000500, customer.getBalance());
    }

    // Test cases for withdraw

    @Test
    public void givenSufficientBalanceAndValidAmount_whenWithdraw_thenSuccess(){
        String result = accountManager.withdraw(customer, 100);
        assertEquals("success", result);
        assertEquals(400, customer.getBalance());
    }

    @Test
    public void givenInsufficientBalanceAndNoCredit_whenWithdraw_thenFailure() {
        String result = accountManager.withdraw(customer, 600);
        assertEquals("insufficient account balance", result);
        assertEquals(500, customer.getBalance());
    }

    @Test
    public void givenVipAndAmountWithinCreditLimit_whenWithdraw_thenSuccess(){
        customer.setCreditAllowed(true);
        customer.setVip(true);
        String result = accountManager.withdraw(customer, 1200);
        assertEquals("success", result);
        assertEquals(-700, customer.getBalance());
    }
}
