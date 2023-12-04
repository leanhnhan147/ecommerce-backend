package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.cartItem.CartItemDto;
import com.ecommerce.backend.form.cartItem.CreateCartItemForm;
import com.ecommerce.backend.form.cartItem.UpdateCartItemForm;
import com.ecommerce.backend.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CartController extends BasicController{

    @Autowired
    private CartService cartService;

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<CartItemDto>> get() {
        ApiMessageDto<List<CartItemDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(cartService.getCart(getCurrentUser()));
        apiMessageDto.setMessage("Get cart success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create-item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateCartItemForm createCartItemForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        cartService.createItem(createCartItemForm, getCurrentUser());
        apiMessageDto.setMessage("Create cart item success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateCartItemForm updateCartItemForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        cartService.updateItem(updateCartItemForm, getCurrentUser());
        apiMessageDto.setMessage("Update cart item success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete-item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        cartService.deleteItem(id, getCurrentUser());
        apiMessageDto.setMessage("Delete cart item success");
        return apiMessageDto;
    }
}
