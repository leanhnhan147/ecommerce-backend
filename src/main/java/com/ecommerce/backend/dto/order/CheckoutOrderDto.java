package com.ecommerce.backend.dto.order;

import com.ecommerce.backend.dto.address.AddressDto;
import com.ecommerce.backend.dto.cartItem.CartItemDto;
import lombok.Data;

import java.util.List;

@Data
public class CheckoutOrderDto {
    private Double shippingPrice;
    private Double totalProductPrice;
    private Double totalPrice;
    private AddressDto address;
    private List<CartItemDto> cartItems;
}
