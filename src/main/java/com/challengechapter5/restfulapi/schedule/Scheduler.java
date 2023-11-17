package com.challengechapter5.restfulapi.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    // Menjalankan setiap hari pada pukul 07:00:00
    @Scheduled(cron = "0 0 7 * * ?")
    public void runTask() {

        System.out.println("Ayo Masuk Kelas BEJ");
    }
}