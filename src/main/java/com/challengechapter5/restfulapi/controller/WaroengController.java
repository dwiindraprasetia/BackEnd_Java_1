package com.challengechapter5.restfulapi.controller;

import com.challengechapter5.restfulapi.model.entity.MenuWaroeng;
import com.challengechapter5.restfulapi.model.entity.Pesanan;
import com.challengechapter5.restfulapi.service.WaroengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Pesanan> getOrderList() {
        return waroengService.getOrderList();
    }

    @PostMapping("/order")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void placeOrder(int menuItem, int quantity) {
        waroengService.placeOrder(menuItem, quantity);
    }

    @GetMapping("/totalAmount")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public double calculateTotalAmount() {
        return waroengService.calculateTotalAmount();
    }

    @GetMapping("/confirmedOrders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Pesanan> getConfirmedOrders() {
        return waroengService.getConfirmedOrders();
    }

    @PostMapping("/generateReceipt")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void generateReceiptToPDF() {
        waroengService.generateReceiptToPDF(waroengService.calculateTotalAmount());
    }
}