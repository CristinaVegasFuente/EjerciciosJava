package com.example.backup_incremental_cron.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "backup_log")
public class BackupLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaEjecucion;
    private String rutaOrigen;
    private String rutaBackup;
    private String hashAnterior;
    private String hashNuevo;
    private String estado;
    private String mensaje;

    public BackupLog() {
    }

    public BackupLog(LocalDateTime fechaEjecucion, String rutaOrigen, String rutaBackup,
                     String hashAnterior, String hashNuevo, String estado, String mensaje) {
        this.fechaEjecucion = fechaEjecucion;
        this.rutaOrigen = rutaOrigen;
        this.rutaBackup = rutaBackup;
        this.hashAnterior = hashAnterior;
        this.hashNuevo = hashNuevo;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaEjecucion() {
        return fechaEjecucion;
    }
    public void setFechaEjecucion(LocalDateTime fechaEjecucion) {
        //el atributo de la clase (el que está guardado dentro del objeto)
        //el parámetro que recibes por el metodo
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getRutaOrigen() {
        return rutaOrigen;
    }
    public void setRutaOrigen(String rutaOrigen) {
        //el atributo de la clase (el que está guardado dentro del objeto)
        //el parámetro que recibes por el metodo
        this.rutaOrigen = rutaOrigen;
    }

    public String getRutaBackup() {
        return rutaBackup;
    }
    public void setRutaBackup(String rutaBackup) {
        //el atributo de la clase (el que está guardado dentro del objeto)
        //el parámetro que recibes por el metodo
        this.rutaBackup = rutaBackup;
    }

    public String getHashAnterior() {
        return hashAnterior;
    }

    public void setHashAnterior(String hashAnterior) {
        this.hashAnterior = hashAnterior;
    }

    public String getHashNuevo() {
        return hashNuevo;
    }

    public void setHashNuevo(String hashNuevo) {
        this.hashNuevo = hashNuevo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
