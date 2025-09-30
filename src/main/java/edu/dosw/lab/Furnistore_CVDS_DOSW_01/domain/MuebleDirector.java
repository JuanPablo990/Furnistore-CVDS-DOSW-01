package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

public class MuebleDirector {
    private MuebleBuilder builder;

    public MuebleDirector(MuebleBuilder builder) {
        this.builder = builder;
    }

    public Mueble construirSofaClasico() {
        return builder.setTipo("Sofá")
                .setMaterial("Cuero")
                .setEstilo("Clásico")
                .setDimensiones("200x90x100 cm")
                .build();
    }

    public Mueble construirCamaModerna() {
        return builder.setTipo("Cama")
                .setMaterial("Madera")
                .setEstilo("Moderno")
                .setDimensiones("200x160 cm")
                .build();
    }
}
