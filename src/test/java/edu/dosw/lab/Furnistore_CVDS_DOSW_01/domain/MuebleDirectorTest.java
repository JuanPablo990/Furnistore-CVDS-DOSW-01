package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MuebleDirectorTest {

    @Test
    void testConstruirSofaClasico() {

        MuebleBuilder builder = new ConcreteMuebleBuilder();
        MuebleDirector director = new MuebleDirector(builder);

        Mueble sofa = director.construirSofaClasico();

        assertNotNull(sofa, "El sofá clásico no debe ser nulo");
        String descripcion = sofa.getDescripcion();
        assertTrue(descripcion.contains("Tipo=Sofá"), "El tipo debe ser 'Sofá'");
        assertTrue(descripcion.contains("Material=Cuero"), "El material debe ser 'Cuero'");
    }
}