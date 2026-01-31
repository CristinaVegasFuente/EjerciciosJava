package com.example.democron.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

//convierte la clase en un bean de Spring
@Service
public class EscrituraFicheroService {

    private static final Logger logger = LoggerFactory.getLogger(EscrituraFicheroService.class);

    //esta anotacion es como la de la clase principal!! pero condicionante
    //@Scheduled → programa la ejecución automática cada diez segundos
    @Scheduled(cron = "0/10 * * * * *")
    public void escribirFichero() {
        //abre/crea un fichero llamado Fichero.txt y escribe añadiendo (modo append)
        try (FileWriter fw = new FileWriter("Fichero.txt",true)) {
            fw.write("Holiii, soy crisss haciendo pruebas con cron escribiendo en un fichero \n");
            //Cada vez que se ejecute, sale en la consola
            System.out.println("texto escrito en el fichero");
        } catch (IOException e) {
            logger.error("No se ha escrito nada de nada en el fichero",e);
        }
    }
}
