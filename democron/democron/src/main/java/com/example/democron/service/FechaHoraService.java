package com.example.democron.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//convierte la clase en un bean de Spring
@Service
public class FechaHoraService {

    private static final Logger logger = LoggerFactory.getLogger(EscrituraFicheroService.class);

    @Scheduled(cron = "0/15 * * * * *")
    public void escribirFechaHora() {

        LocalDateTime ahora = LocalDateTime.now();
        String fechaHora = ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (FileWriter fw = new FileWriter("FechaHora.txt", true)) {
            fw.write("registro de " + fechaHora + "\n");
            logger.info("Fecha y hora escrita: " + fechaHora);
        } catch (IOException e) {
            logger.error("Error al escribir fecha y hora", e);
        }
    }
}