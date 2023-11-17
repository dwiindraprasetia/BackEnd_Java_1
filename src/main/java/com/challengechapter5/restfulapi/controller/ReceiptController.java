package com.challengechapter5.restfulapi.controller;

import com.challengechapter5.restfulapi.microservice.ReceiptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/generateReceipt")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void generateReceiptToPDF() {
        receiptService.generateReceiptToPDF(receiptService.calculateTotalAmount());
    }
}