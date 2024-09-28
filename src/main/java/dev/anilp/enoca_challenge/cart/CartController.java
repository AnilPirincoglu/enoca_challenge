package dev.anilp.enoca_challenge.cart;

import dev.anilp.enoca_challenge.cart.util.dto.AddProductToCartRequestDto;
import dev.anilp.enoca_challenge.cart.util.dto.CartResponseDto;
import dev.anilp.enoca_challenge.cart.util.dto.RemoveProductFromCartRequestDto;
import dev.anilp.enoca_challenge.cart.util.dto.UpdateCartRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("{cartId}")
    public CartResponseDto getCart(@PathVariable Long cartId) {
        return cartService.getCart(cartId);
    }

    @PostMapping
    public void addProductToCart(@Valid @RequestBody AddProductToCartRequestDto addProductToCartRequest,
                                 @Valid @RequestParam(defaultValue = "1") Integer quantity) {
        cartService.addProductToCart(addProductToCartRequest, quantity);
    }

    @PutMapping("{cartId}")
    public void updateCart(@PathVariable Long cartId, @Valid @RequestBody UpdateCartRequestDto updateCartRequest) {
        cartService.updateCart(cartId, updateCartRequest);
    }

    @PutMapping("/empty/{cartId}")
    public void emptyCart(@PathVariable Long cartId) {
        cartService.emptyCart(cartId);
    }

    @DeleteMapping("{productName}")
    public void removeProductFromCart(@PathVariable String productName, @RequestBody RemoveProductFromCartRequestDto removeProductFromCartRequest) {
        cartService.removeProductFromCart(productName, removeProductFromCartRequest);
    }
}
