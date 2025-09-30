package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

public class ConcreteMuebleBuilder implements MuebleBuilder {
    private Mueble mueble;

    public ConcreteMuebleBuilder() {
        this.mueble = new Mueble();
    }

    @Override
    public MuebleBuilder setTipo(String tipo) {
        mueble.setTipo(tipo);
        return this;
    }

    @Override
    public MuebleBuilder setMaterial(String material) {
        mueble.setMaterial(material);
        return this;
    }

    @Override
    public MuebleBuilder setEstilo(String estilo) {
        mueble.setEstilo(estilo);
        return this;
    }

    @Override
    public MuebleBuilder setDimensiones(String dimensiones) {
        mueble.setDimensiones(dimensiones);
        return this;
    }

    @Override
    public Mueble build() {
        return mueble;
    }
}
