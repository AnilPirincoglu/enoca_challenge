package dev.anilp.enoca_challenge.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class CartRepositoryTest {

    @Mock
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findCartByCustomerId_returnsCart_whenCustomerIdExists() {
        UUID customerId = UUID.randomUUID();
        Cart cart = new Cart();
        when(cartRepository.findCartByCustomerId(customerId)).thenReturn(Optional.of(cart));

        Optional<Cart> result = cartRepository.findCartByCustomerId(customerId);

        assertTrue(result.isPresent());
        assertEquals(cart, result.get());
    }

    @Test
    void findCartByCustomerId_returnsEmpty_whenCustomerIdDoesNotExist() {
        UUID customerId = UUID.randomUUID();
        when(cartRepository.findCartByCustomerId(customerId)).thenReturn(Optional.empty());

        Optional<Cart> result = cartRepository.findCartByCustomerId(customerId);

        assertFalse(result.isPresent());
    }
}