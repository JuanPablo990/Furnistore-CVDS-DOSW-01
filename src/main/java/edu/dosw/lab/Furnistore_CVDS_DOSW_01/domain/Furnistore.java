package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

/**
 * Clase de ejemplo para demostrar el uso del patrón Builder y Director.
 * Crea y muestra información de muebles usando configuraciones predefinidas.
 */
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
