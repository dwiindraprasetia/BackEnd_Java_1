package com.challengechapter5.restfulapi.model.repository;

import com.challengechapter5.restfulapi.model.entity.Pesanan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PesananRepository extends JpaRepository<Pesanan, Long> {
}