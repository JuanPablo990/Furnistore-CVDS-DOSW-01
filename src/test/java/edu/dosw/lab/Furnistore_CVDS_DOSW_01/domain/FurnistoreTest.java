package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FurnistoreTest {

    @Test
    void testFlujoCompletoFurnistore() {
        MuebleBuilder builder = new ConcreteMuebleBuilder();
        MuebleDirector director = new MuebleDirector(builder);

        Mueble sofa = director.construirSofaClasico();
        Mueble cama = director.construirCamaModerna();

        assertNotNull(sofa);
        assertNotNull(cama);
        assertNotSame(sofa, cama);

        assertTrue(sofa.getDescripcion().contains("Sofá"));
        assertTrue(sofa.getDescripcion().contains("Cuero"));
        assertTrue(cama.getDescripcion().contains("Cama"));
        assertTrue(cama.getDescripcion().contains("Madera"));
    }

    @Test
    void testEjecutableNoLanzaExcepcion() {
        String[] args = {};
        assertDoesNotThrow(() -> Furnistore.ejecutable(args));
    }
}