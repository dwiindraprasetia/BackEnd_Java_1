package com.challengechapter5.restfulapi.controller;

import com.challengechapter5.restfulapi.model.entity.MenuWaroeng;
import com.challengechapter5.restfulapi.model.entity.Pesanan;
import com.challengechapter5.restfulapi.service.WaroengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/waroeng")
public class WaroengController {

    private final WaroengService waroengService;

    @Autowired
    public WaroengController(WaroengService waroengService) {
        this.waroengService = waroengService;
    }

    @GetMapping("/menu")
    public List<MenuWaroeng> getMenuList() {
        return waroengService.getMenuList();
    }

    @GetMapping("/order")
    public List<Pesanan> getOrderList() {
        return waroengService.getOrderList();
    }

    @PostMapping("/order")
    public void placeOrder(int menuItem, int quantity) {
        waroengService.placeOrder(menuItem, quantity);
    }

    @GetMapping("/totalAmount")
    public double calculateTotalAmount() {
        return waroengService.calculateTotalAmount();
    }

    @GetMapping("/confirmedOrders")
    public List<Pesanan> getConfirmedOrders() {
        return waroengService.getConfirmedOrders();
    }

    @PostMapping("/generateReceipt")
    public void generateReceiptToPDF() {
        waroengService.generateReceiptToPDF(waroengService.calculateTotalAmount());
    }
}