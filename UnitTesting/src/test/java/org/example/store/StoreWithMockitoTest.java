package org.example.store;

import org.example.account.AccountManager;
import org.example.account.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StoreWithMockitoTest {

    private StoreImpl store;
    private AccountManager accountManager;
    private Customer customer;
    private Product product;

    @BeforeEach
    void setUp() {
        accountManager = mock(AccountManager.class);
        store = new StoreImpl(accountManager);
        customer = mock(Customer.class);
        product = new Product();
    }
    @Test
    public void givenProductWithPositiveQuantityAndCustomerWithSufficientBalance_whenBuy_thenSuccess() {

        // Arrange
        Product product = new Product();
        product.setPrice(100);
        product.setQuantity(4);

        Customer customer = new Customer();

        AccountManager accountManager = mock(AccountManager.class);
        when(accountManager.withdraw(customer, 80))
                .thenReturn("success");

        when(accountManager.withdraw(customer, 100))
                .thenReturn("success");

        Store store = new StoreImpl(accountManager);

        // Act
        store.buy(product, customer);

        // Assert
        assertEquals(3, product.getQuantity());
        verify(accountManager).withdraw(customer, 100);
    }
    @Test
    void givenProductWithZeroQuantity_whenBuy_thenThrowOutOfStockException() {
        // Arrange
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(0);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> store.buy(product, customer));
        assertEquals("Product out of stock", exception.getMessage());
    }

    @Test
    void givenCustomerWithInsufficientBalance_whenBuy_thenThrowPaymentFailureException() {
        // Arrange
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(5);

        when(accountManager.withdraw(customer, product.getPrice())).thenReturn("insufficient funds");

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> store.buy(product, customer));
        assertEquals("Payment failure: insufficient funds", exception.getMessage());
    }

    @Test
    void givenProductWithPositiveQuantity_whenBuy_thenQuantityDecreasesByOne() {
        // Arrange
        product.setName("Test Product");
        product.setPrice(100);
        product.setQuantity(10);

        when(accountManager.withdraw(customer, product.getPrice())).thenReturn("success");

        // Act
        store.buy(product, customer);

        // Assert
        assertEquals(9, product.getQuantity(), "Product quantity should decrease by 1 after purchase");
    }


    static class AlwaysSuccessAccountManager implements AccountManager {

        @Override
        public void deposit(Customer customer, int amount) {

        }

        @Override
        public String withdraw(Customer customer, int amount) {
            return "success";
        }
    }
}
