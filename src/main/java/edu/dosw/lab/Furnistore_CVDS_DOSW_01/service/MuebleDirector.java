package edu.dosw.lab.Furnistore_CVDS_DOSW_01.service;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.Mueble;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.MuebleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Director para construir objetos {@link Mueble} usando el patrón Builder.
 * Proporciona métodos para crear muebles con configuraciones específicas.
 */
@Service
public class MuebleDirector {
    private final MuebleBuilder builder;

    /**
     * Crea un director con el builder inyectado.
     * @param builder Builder para construir muebles.
     */
    @Autowired
    public MuebleDirector(MuebleBuilder builder) {
        this.builder = builder;
    }

    /**
     * Construye un sofá clásico con atributos predefinidos.
     * @return Sofá clásico construido.
     */
    public Mueble construirSofaClasico() {
        return builder.setTipo("Sofá")
                .setMaterial("Cuero")
                .setEstilo("Clásico")
                .setDimensiones("200x90x100 cm")
                .build();
    }

    /**
     * Construye una cama moderna con atributos predefinidos.
     * @return Cama moderna construida.
     */
    public Mueble construirCamaModerna() {
        return builder.setTipo("Cama")
                .setMaterial("Madera")
                .setEstilo("Moderno")
                .setDimensiones("200x160 cm")
                .build();
    }

    /**
     * Construye un mueble personalizado con los parámetros especificados.
     * @return Mueble personalizado construido.
     */
    public Mueble construirMueblePersonalizado(String tipo, String material, String estilo, String dimensiones) {
        return builder.setTipo(tipo)
                .setMaterial(material)
                .setEstilo(estilo)
                .setDimensiones(dimensiones)
                .build();
    }
}