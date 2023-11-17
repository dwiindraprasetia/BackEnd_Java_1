package com.challengechapter5.restfulapi.microservice;

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
import java.util.stream.IntStream;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, timeout = 120)
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
                new MenuWaroeng("Rabok Jukut", 13000),
                new MenuWaroeng("Gangan Labu", 10000)
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

    public List<Pesanan> getConfirmedOrders() {
        logger.info("Mengambil daftar pesanan yang dikonfirmasi.");
        return orderList;
    }

    public void clearOrderList() {
        logger.info("Menghapus daftar pesanan.");
        orderList.clear();
    }
}