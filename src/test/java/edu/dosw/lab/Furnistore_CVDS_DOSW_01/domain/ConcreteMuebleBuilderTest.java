package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConcreteMuebleBuilderTest {

    @Test
    void testBuildMuebleCompleto() {

        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();


        Mueble mueble = builder.setTipo("Silla")
                .setMaterial("Madera")
                .setEstilo("Clásico")
                .setDimensiones("50x50x100 cm")
                .build();

        assertNotNull(mueble, "El mueble construido no debe ser nulo");
        String descripcion = mueble.getDescripcion();
        assertTrue(descripcion.contains("Tipo=Silla"), "Debe contener el tipo correcto");
    }
}