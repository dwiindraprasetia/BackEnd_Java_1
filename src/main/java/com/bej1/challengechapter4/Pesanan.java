package com.bej1.challengechapter4;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
@Data
public class Pesanan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private MenuWaroeng menu;

    @Min(1)
    private int quantity;

    public Pesanan(MenuWaroeng selectedMenu, int quantity) {
    }

    public double getTotalPrice() {
        return 0;
    }
}