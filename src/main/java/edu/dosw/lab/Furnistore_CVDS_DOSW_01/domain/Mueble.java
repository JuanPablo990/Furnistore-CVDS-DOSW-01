package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mueble {
    private static final Logger log = LoggerFactory.getLogger(Mueble.class);

    private String tipo;
    private String material;
    private String estilo;
    private String dimensiones;

    public String getDescripcion() {
        return String.format("Mueble: Tipo=%s, Material=%s, Estilo=%s, Dimensiones=%s",
                tipo, material, estilo, dimensiones);
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public void showInfo() {
        log.info(getDescripcion());
    }
}
