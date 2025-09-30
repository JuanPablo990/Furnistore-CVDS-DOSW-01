package edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MuebleTest {

    @Test
    void testGetDescripcionCompleta() {

        Mueble mueble = new Mueble();
        mueble.setTipo("Silla");
        mueble.setMaterial("Madera");
        mueble.setEstilo("Rústico");
        mueble.setDimensiones("50x50x100 cm");

        String descripcion = mueble.getDescripcion();

        String expected = "Mueble: Tipo=Silla, Material=Madera, Estilo=Rústico, Dimensiones=50x50x100 cm";
        assertEquals(expected, descripcion, "La descripción debe contener todos los atributos correctamente formateados");
    }
}