package com.example.democron.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class CopiaFicheroService {

    private static final Logger logger = LoggerFactory.getLogger(CopiaFicheroService.class);

    @Scheduled(cron = "0 * * * * *")
    public void copiaFichero() {
        Path origen = Paths.get("Fichero.txt");
        Path destino = Paths.get("Backup.txt");

        //si el fichero existe, lo copio, si falla, se loguea y si no existe, avisa
        if (Files.exists(origen)) {
            try {
                Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
                logger.info("copia realizada a backup.txt");
            } catch (IOException e) {
                logger.error("no se copio el ficheto", e);
            }
        } else {
            logger.warn("el fichero no existe");
        }
    }
}
