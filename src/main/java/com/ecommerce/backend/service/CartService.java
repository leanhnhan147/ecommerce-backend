package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.cartItem.CartItemDto;
import com.ecommerce.backend.form.cartItem.CreateCartItemForm;
import com.ecommerce.backend.form.cartItem.UpdateCartItemForm;

import java.util.List;

public interface CartService {

    List<CartItemDto> getCart(Long customerId);

    void createItem(CreateCartItemForm createCartItemForm, Long customerId);

    void updateItem(UpdateCartItemForm updateCartItemForm, Long customerId);

    void deleteItem(Long cartItemId, Long customerId);
}
