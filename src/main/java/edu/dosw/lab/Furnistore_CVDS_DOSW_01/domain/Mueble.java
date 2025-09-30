package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representa un mueble con sus características principales.
 * Permite establecer y obtener los atributos, y mostrar la información por log.
 */
public class Mueble {
    private static final Logger log = LoggerFactory.getLogger(Mueble.class);

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

    /**
     * Establece el tipo de mueble.
     * @param tipo Tipo de mueble.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece el material del mueble.
     * @param material Material del mueble.
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Establece el estilo del mueble.
     * @param estilo Estilo del mueble.
     */
    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    /**
     * Establece las dimensiones del mueble.
     * @param dimensiones Dimensiones del mueble.
     */
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    /**
     * Muestra la información del mueble en el log.
     */
    public void showInfo() {
        log.info(getDescripcion());
    }
}
