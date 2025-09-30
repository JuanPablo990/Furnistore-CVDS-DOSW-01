package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

/**
 * Interfaz para construir objetos {@link Mueble} usando el patrón Builder.
 * Permite establecer los atributos del mueble de forma encadenada.
 */
public interface MuebleBuilder {

    MuebleBuilder setTipo(String tipo);

    MuebleBuilder setMaterial(String material);

    MuebleBuilder setEstilo(String estilo);

    MuebleBuilder setDimensiones(String dimensiones);

    Mueble build();
}
