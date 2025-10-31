package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa un cliente para el sistema de facturación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private String id;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;

    /**
     * Constructor simplificado para creación rápida.
     */
    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
}