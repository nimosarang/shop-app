package com.shop.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ItemController {

    @GetMapping(value = "/admin/item/new")
    public String itemForm(){
        return "/item/itemForm";
    }

}
