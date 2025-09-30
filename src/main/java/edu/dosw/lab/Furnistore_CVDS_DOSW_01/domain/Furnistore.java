package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

public class Furnistore {
    public static void ejecutable(String[] args) {
        MuebleBuilder builder = new ConcreteMuebleBuilder();
        MuebleDirector director = new MuebleDirector(builder);

        Mueble sofa = director.construirSofaClasico();
        sofa.showInfo();

        Mueble cama = director.construirCamaModerna();
        cama.showInfo();
    }
}
