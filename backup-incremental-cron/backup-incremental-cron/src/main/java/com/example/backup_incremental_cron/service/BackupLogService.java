package com.example.backup_incremental_cron.service;

import com.example.backup_incremental_cron.model.BackupLog;
import com.example.backup_incremental_cron.repository.BackupLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class BackupLogService {

    private static final Logger log = LoggerFactory.getLogger(BackupLogService.class);
    private static final DateTimeFormatter BACKUP_NAME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    @Value("${backup.origen}")
    private String rutaOrigen;
    @Value("${backup.destino}")
    private String rutaDestino;

    private final BackupLogRepository backupLogRepository;
    // Último hash conocido en memoria
    private String ultimoHash;

    public BackupLogService(BackupLogRepository backupLogRepository) {
        this.backupLogRepository = backupLogRepository;
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void ejecutarBackupIncremental() {
        LocalDateTime ahora = LocalDateTime.now();
        String hashActual = null;
        String rutaBackupGenerada = null;
        String hashAnterior = ultimoHash;
        String estado;
        String mensaje;

        Path origen = Paths.get(rutaOrigen);
        Path destinoDir = Paths.get(rutaDestino);
        try {
            if (!Files.exists(origen)) {
                // Si no existe el fichero origen, lo creamos vacío y registramos log
                crearFicheroOrigenSiNoExiste(origen);
                estado = "CREADO_ORIGEN";
                mensaje = "Se crea un fichero: " + origen.toAbsolutePath();
                guardarLog(ahora, hashAnterior, null, estado, mensaje, null);
                return;
            }
            hashActual = calcularHash(origen);
            if (haCambiadoContenido(hashActual)) {
                rutaBackupGenerada = crearBackup(origen, destinoDir, ahora);
                ultimoHash = hashActual;
                estado = "modificado";
                mensaje = "backup creado en: " + rutaBackupGenerada;
            } else {
                estado = "sin cambios";
                mensaje = "No se crea backup";
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            log.error("Error al realizar el backup incremental", e);
            estado = "error";
            mensaje = "Error al hacer el backup: " + e.getMessage();
            hashActual = null;
        }
        guardarLog(ahora, hashAnterior, hashActual, estado, mensaje, rutaBackupGenerada);
    }

    private void crearFicheroOrigenSiNoExiste(Path origen) throws IOException {
        Path parentDir = origen.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
            log.info("Creada carpeta de origen: {}", parentDir.toAbsolutePath());
        }
        Files.createFile(origen);
        log.info("Creado fichero: {}", origen.toAbsolutePath());
    }

    private boolean haCambiadoContenido(String hashActual) {
        return ultimoHash == null || !ultimoHash.equals(hashActual);
    }

    private String crearBackup(Path origen, Path destinoDir, LocalDateTime ahora) throws IOException {
        if (!Files.exists(destinoDir)) {
            Files.createDirectories(destinoDir);
            log.info("Creada carpeta: {}", destinoDir.toAbsolutePath());
        }
        String timestamp = ahora.format(BACKUP_NAME_FORMAT);
        String nombreBackup = "datos " + timestamp + ".txt";
        Path destino = destinoDir.resolve(nombreBackup);
        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        log.info("backup creado en: {}", destino.toAbsolutePath());
        return destino.toString();
    }

    private void guardarLog(LocalDateTime fechaEjecucion,
                            String hashAnterior,
                            String hashActual,
                            String estado,
                            String mensaje,
                            String rutaBackup) {
        BackupLog logEntry = new BackupLog(
                fechaEjecucion,
                rutaOrigen,
                rutaBackup,
                hashAnterior,
                hashActual,
                estado,
                mensaje
        );
        backupLogRepository.save(logEntry);
    }

    private String calcularHash(Path file) throws IOException, NoSuchAlgorithmException {
        byte[] contenido = Files.readAllBytes(file);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(contenido);
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
