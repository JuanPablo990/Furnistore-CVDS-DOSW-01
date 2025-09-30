package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

/**
 * Implementación concreta del patrón Builder para la clase Mueble.
 * Permite construir objetos Mueble configurando sus atributos de forma encadenada.
 */
public class ConcreteMuebleBuilder implements MuebleBuilder {
    private String tipo;
    private String material;
    private String estilo;
    private String dimensiones;

    /**
     * Establece el tipo de mueble.
     * @param tipo tipo de mueble
     * @return el builder actual para encadenar métodos
     */
    @Override
    public MuebleBuilder setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    /**
     * Establece el material del mueble.
     * @param material material del mueble
     * @return el builder actual para encadenar métodos
     */
    @Override
    public MuebleBuilder setMaterial(String material) {
        this.material = material;
        return this;
    }

    /**
     * Establece el estilo del mueble.
     * @param estilo estilo del mueble
     * @return el builder actual para encadenar métodos
     */
    @Override
    public MuebleBuilder setEstilo(String estilo) {
        this.estilo = estilo;
        return this;
    }

    /**
     * Establece las dimensiones del mueble.
     * @param dimensiones dimensiones del mueble
     * @return el builder actual para encadenar métodos
     */
    @Override
    public MuebleBuilder setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
        return this;
    }

    /**
     * Construye y retorna el objeto Mueble con los atributos configurados.
     * @return instancia de Mueble
     */
    @Override
    public Mueble build() {
        Mueble mueble = new Mueble();
        mueble.setTipo(tipo);
        mueble.setMaterial(material);
        mueble.setEstilo(estilo);
        mueble.setDimensiones(dimensiones);
        return mueble;
    }
}
