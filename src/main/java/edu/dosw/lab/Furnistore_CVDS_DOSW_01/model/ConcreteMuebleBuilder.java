package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.springframework.stereotype.Component;

/**
 * Implementación concreta del patrón Builder para la clase Mueble.
 * Permite construir objetos Mueble configurando sus atributos de forma encadenada.
 */
@Component
public class ConcreteMuebleBuilder implements MuebleBuilder {
    private String tipo;
    private String material;
    private String estilo;
    private String dimensiones;

    @Override
    public MuebleBuilder setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    @Override
    public MuebleBuilder setMaterial(String material) {
        this.material = material;
        return this;
    }

    @Override
    public MuebleBuilder setEstilo(String estilo) {
        this.estilo = estilo;
        return this;
    }

    @Override
    public MuebleBuilder setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
        return this;
    }

    @Override
    public Mueble build() {
        return new Mueble(tipo, material, estilo, dimensiones);
    }
}