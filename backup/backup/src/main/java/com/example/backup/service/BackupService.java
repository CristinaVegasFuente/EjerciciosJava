package com.example.backup.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BackupService {

    private static final Logger log = LoggerFactory.getLogger(BackupService.class);
    private static final DateTimeFormatter BACKUP_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    @Value("${backup.origen}")
    private String rutaOrigen;
    @Value("${backup.destino}")
    private String rutaDestino;

    private Long ultimoTamanyo = -1L;
    private FileTime ultimaFechaModificacion = null;

    @Scheduled(cron = "0/20 * * * * *")
    public void ejecutarBackup(){
        try{
            Path origen = Paths.get(rutaOrigen);
            Path destinoDir = Paths.get(rutaDestino);
            if (!Files.exists(origen)) {
                crearFicheroOrigen(origen);
                return;
            }
            if(haCambiado(origen)) {
                crearBackup(origen, destinoDir);
            } else {
                log.info("Sin modificacion no hay backup");
            }
        }catch (IOException e) {
            log.error(("errorrrrrrrrr" + e));
        }
    }

    private void crearFicheroOrigen(Path origen) throws IOException {
        Path parent = origen.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
            log.info("creada carpeta origen: ");
        }
        Files.createFile(origen);
        log.info("Creado archivo vacio");
        ultimoTamanyo = Files.size(origen);
        ultimaFechaModificacion = Files.getLastModifiedTime(origen);
    }

    private boolean haCambiado(Path origen) throws IOException {
        Long nuevoTamanyo = Files.size(origen);
        FileTime nuevaFecha = Files.getLastModifiedTime(origen);
        Boolean cambiado = (nuevoTamanyo != ultimoTamanyo) || (ultimaFechaModificacion != null && !nuevaFecha.equals(ultimaFechaModificacion));
        ultimoTamanyo = nuevoTamanyo;
        ultimaFechaModificacion = nuevaFecha;
        return cambiado;
    }

    private void crearBackup (Path origen, Path destinoDir) throws IOException {
        if (!Files.exists(destinoDir)){
            Files.createDirectories(destinoDir);
            log.info("Creada carpeta destino");
        }
        String timestamp = LocalDateTime.now().format(BACKUP_NAME_FORMAT);
        String nombreBackup = "datos " + timestamp + ".txt";
        Path destino= destinoDir.resolve(nombreBackup);
        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        log.info("backup creado");
    }
}
