package com.challengechapter5.restfulapi.microservice;


import com.challengechapter5.restfulapi.model.entity.Pesanan;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, timeout = 120)
public class ReceiptService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptService.class);

    private List<Pesanan> orderList;

    //    CATATAN UNTUK DIRI SENDIRI
    //    Kenapa function untuk menghitung total pesanan ada didalam entity???
    //    Seharusnya ada didalam service ga sih???
    public double calculateTotalAmount() {
        logger.info("Menghitung total pembayaran: Rp {}", orderList);
        return orderList.stream()
                .mapToDouble(Pesanan::getTotalPrice)
                .sum();
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