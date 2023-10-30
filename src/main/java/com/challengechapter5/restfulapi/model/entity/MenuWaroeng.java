package com.challengechapter5.restfulapi.model.entity;

import jakarta.persistence.Table;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "menu_waroeng")
@Data
public class MenuWaroeng implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    private int price;

    public MenuWaroeng(String nasikKuning, int i) {
    }
}