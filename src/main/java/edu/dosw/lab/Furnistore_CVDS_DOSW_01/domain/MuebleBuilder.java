package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

public interface MuebleBuilder {
    MuebleBuilder setTipo(String tipo);
    MuebleBuilder setMaterial(String material);
    MuebleBuilder setEstilo(String estilo);
    MuebleBuilder setDimensiones(String dimensiones);
    Mueble build();
}
