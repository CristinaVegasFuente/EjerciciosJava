package com.example.democron.service;


import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class LimpiezaFicheroService {

    private static final Logger logger = LoggerFactory.getLogger(LimpiezaFicheroService.class);

    //condicion cada 30 segundos
    @Scheduled(cron = "0/30 * * * * *")
    public void limpiarFichero(){
        //false machaca lo que había escrito y deja el fichero vacio
        try (FileWriter fw = new FileWriter("Fichero.txt", false)){
            fw.write("");
            logger.warn("Fichero borrado con cronnnn");
        }catch (IOException e) {
            logger.error("no se ha borrao naaa", e);
        }
    }
}
