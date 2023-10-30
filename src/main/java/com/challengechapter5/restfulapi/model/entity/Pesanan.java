package com.challengechapter5.restfulapi.model.entity;

import jakarta.persistence.Table;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "pesanan")
@Data
public class Pesanan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private MenuWaroeng menu;

    @Min(1)
    private int quantity;

    public Pesanan(MenuWaroeng selectedMenu, int quantity) {
    }

    public double getTotalPrice() {
        return menu.getPrice() * quantity;
    }
}