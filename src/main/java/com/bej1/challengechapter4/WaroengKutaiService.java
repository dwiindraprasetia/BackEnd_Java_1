package com.bej1.challengechapter4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

@Service
public class WaroengKutaiService {

    private static final Logger logger = LoggerFactory.getLogger(WaroengKutaiService.class);

    private final MenuWaroengRepository menuWaroengRepository;
    private final PesananRepository pesananRepository;

    private List<MenuWaroeng> menuList;
    private List<Pesanan> orderList;

    @Autowired
    public WaroengKutaiService(MenuWaroengRepository menuWaroengRepository, PesananRepository pesananRepository) {
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
    
        public void generateReceipt(double totalAmount) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("struk_pembayaran.txt"))) {
                writer.write("== Struk Pembayaran ==");
                writer.newLine();
                writer.newLine();
    
                orderList.stream()
                        .map(order -> order.toString())
                        .forEach(orderStr -> {
                            try {
                                writer.write(orderStr);
                                writer.newLine();
                            } catch (IOException e) {
                                logger.error("Terjadi kesalahan saat mencetak struk pembayaran.", e);
                                e.printStackTrace();
                            }
                        });
    
                writer.newLine();
                writer.write("Total Pembayaran: Rp " + totalAmount);
            } catch (IOException e) {
                logger.error("Terjadi kesalahan saat mencetak struk pembayaran.", e);
                System.out.println("Terjadi kesalahan saat mencetak struk pembayaran.");
            }

            logger.info("Mencetak struk pembayaran selesai.");
        }
}