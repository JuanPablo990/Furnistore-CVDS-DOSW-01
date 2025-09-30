package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

/**
 * Director para construir objetos {@link Mueble} usando el patrón Builder.
 * Proporciona métodos para crear muebles con configuraciones específicas.
 */
public class MuebleDirector {
    private MuebleBuilder builder;

    /**
     * Crea un director con el builder especificado.
     * @param builder Builder para construir muebles.
     */
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
}
