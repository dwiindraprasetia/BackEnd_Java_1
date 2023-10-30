package com.challengechapter5.restfulapi.service;

import com.challengechapter5.restfulapi.model.entity.MenuWaroeng;
import com.challengechapter5.restfulapi.model.entity.Pesanan;
import com.challengechapter5.restfulapi.model.repository.MenuWaroengRepository;
import com.challengechapter5.restfulapi.model.repository.PesananRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.*;
import java.io.IOException;
import java.util.stream.IntStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

@Service
public class WaroengService {

    private static final Logger logger = LoggerFactory.getLogger(WaroengService.class);

    private final MenuWaroengRepository menuWaroengRepository;
    private final PesananRepository pesananRepository;

    private List<MenuWaroeng> menuList;
    private List<Pesanan> orderList;

    @Autowired
    public WaroengService(MenuWaroengRepository menuWaroengRepository, PesananRepository pesananRepository) {
        this.menuWaroengRepository = menuWaroengRepository;
        this.pesananRepository = pesananRepository;
        menuList = Arrays.asList(
                new MenuWaroeng("Nasik Kuning", 15000),
                new MenuWaroeng("Nasik Kebuli", 10000),
                new MenuWaroeng("Rabok Jukut", 13000)
        );

        orderList = new ArrayList<>();
    }

    public List<MenuWaroeng> getMenuList() {
        return menuList;
    }

    public List<Pesanan> getOrderList() {
        return orderList;
    }

    public void displayMenu() {
        IntStream.range(0, menuList.size())
                .forEach(i -> System.out.println((i + 1) + ". " + menuList.get(i).getName()));
    }

    public void placeOrder(int menuItem, int quantity) {
        if (menuItem < 1 || menuItem > menuList.size()) {
            logger.error("Pilihan tidak valid: {}", menuItem);
            throw new IllegalArgumentException("Pilihan tidak valid.");
        }

        MenuWaroeng selectedMenu = menuList.get(menuItem - 1);
        orderList.add(new Pesanan(selectedMenu, quantity));
        logger.info("Pesanan {} x{} berhasil ditambahkan.", selectedMenu.getName(), quantity);
    }

//    CATATAN UNTUK DIRI SENDIRI
//    Kenapa function untuk menghitung total pesanan ada didalam entity???
//    Seharusnya ada didalam service ga sih???
    public double calculateTotalAmount() {
        logger.info("Menghitung total pembayaran: Rp {}", orderList);
        return orderList.stream()
                .mapToDouble(Pesanan::getTotalPrice)
                .sum();
    }

    public List<Pesanan> getConfirmedOrders() {
        logger.info("Mengambil daftar pesanan yang dikonfirmasi.");
        return orderList;
    }

    public void clearOrderList() {
        logger.info("Menghapus daftar pesanan.");
        orderList.clear();
    }

//    Menjadikan output sebagai PDF
    public void generateReceiptToPDF(double totalAmount) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
    
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("== Struk Pembayaran ==");
                contentStream.newLine();
                contentStream.showText("Total Pembayaran: Rp " + totalAmount);
                contentStream.newLine();
                contentStream.newLine();
    
                for (Pesanan order : orderList) {
                    contentStream.showText(order.toString());
                    contentStream.newLine();
                }
    
                contentStream.endText();
            }
    
            document.save("struk_pembayaran.pdf");
            document.close();
    
            logger.info("Struk pembayaran telah berhasil dibuat dalam format PDF.");
        } catch (IOException e) {
            logger.error("Terjadi kesalahan saat membuat struk pembayaran PDF.", e);
            e.printStackTrace();
        }
    }
}