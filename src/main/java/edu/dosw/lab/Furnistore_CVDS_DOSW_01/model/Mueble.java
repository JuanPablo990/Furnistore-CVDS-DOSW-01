package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa un mueble con sus características principales.
 * Usa Lombok para reducir código boilerplate.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mueble {
    private String tipo;
    private String material;
    private String estilo;
    private String dimensiones;

    /**
     * Devuelve una descripción completa del mueble.
     * @return Descripción con todos los atributos del mueble.
     */
    public String getDescripcion() {
        return String.format("Mueble: Tipo=%s, Material=%s, Estilo=%s, Dimensiones=%s",
                tipo, material, estilo, dimensiones);
    }
}