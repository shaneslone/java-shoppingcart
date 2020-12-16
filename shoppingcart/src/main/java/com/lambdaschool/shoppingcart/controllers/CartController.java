package com.lambdaschool.shoppingcart.controllers;

import com.lambdaschool.shoppingcart.models.CartItem;
import com.lambdaschool.shoppingcart.models.User;
import com.lambdaschool.shoppingcart.services.CartItemService;
import com.lambdaschool.shoppingcart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController
{
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user",
        produces = {"application/json"})
    public ResponseEntity<?> listCartItemsByUserId(Authentication authentication)
    {
        User u = userService.findByName(authentication.getName());
        return new ResponseEntity<>(u,
            HttpStatus.OK);
    }

    @PutMapping(value = "/add/user/product/{productid}",
        produces = {"application/json"})
    public ResponseEntity<?> addToCart(Authentication authentication,
        @PathVariable
            long productid)
    {
        User u = userService.findByName(authentication.getName());
        CartItem addCartTtem = cartItemService.addToCart(u.getUserid(),
            productid,
            "I am not working");
        return new ResponseEntity<>(addCartTtem,
            HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove/user/product/{productid}",
        produces = {"application/json"})
    public ResponseEntity<?> removeFromCart(Authentication authentication,
        @PathVariable
            long productid)
    {
        User u = userService.findByName(authentication.getName());
        CartItem removeCartItem = cartItemService.removeFromCart(u.getUserid(),
            productid,
            "I am still not working");
        return new ResponseEntity<>(removeCartItem,
            HttpStatus.OK);
    }
}
