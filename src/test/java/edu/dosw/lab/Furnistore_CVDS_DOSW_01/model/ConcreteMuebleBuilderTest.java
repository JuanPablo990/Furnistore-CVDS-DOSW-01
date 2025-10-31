package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConcreteMuebleBuilderTest {

    @Test
    @DisplayName("setTipo: Happy Path")
    void testSetTipoHappyPath() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        MuebleBuilder result = builder.setTipo("Silla");

        assertSame(builder, result);
    }

    @Test
    @DisplayName("setTipo: Error - tipo null")
    void testSetTipoWithNull() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        MuebleBuilder result = builder.setTipo(null);

        assertSame(builder, result);
    }

    @Test
    @DisplayName("setMaterial: Happy Path")
    void testSetMaterialHappyPath() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        MuebleBuilder result = builder.setMaterial("Madera");

        assertSame(builder, result);
    }

    @Test
    @DisplayName("setMaterial: Error - material null")
    void testSetMaterialWithNull() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        MuebleBuilder result = builder.setMaterial(null);

        assertSame(builder, result);
    }

    @Test
    @DisplayName("setEstilo: Happy Path")
    void testSetEstiloHappyPath() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        MuebleBuilder result = builder.setEstilo("Moderno");

        assertSame(builder, result);
    }

    @Test
    @DisplayName("setEstilo: Error - estilo null")
    void testSetEstiloWithNull() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        MuebleBuilder result = builder.setEstilo(null);

        assertSame(builder, result);
    }

    @Test
    @DisplayName("setDimensiones: Happy Path")
    void testSetDimensionesHappyPath() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        MuebleBuilder result = builder.setDimensiones("50x60x90 cm");

        assertSame(builder, result);
    }

    @Test
    @DisplayName("setDimensiones: Error - dimensiones null")
    void testSetDimensionesWithNull() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        MuebleBuilder result = builder.setDimensiones(null);

        assertSame(builder, result);
    }

    @Test
    @DisplayName("build: Happy Path - todos los atributos")
    void testBuildHappyPath() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        Mueble mueble = builder.setTipo("Mesa")
                .setMaterial("Cristal")
                .setEstilo("Minimalista")
                .setDimensiones("120x80x75 cm")
                .build();

        assertNotNull(mueble);
        assertEquals("Mesa", mueble.getTipo());
        assertEquals("Cristal", mueble.getMaterial());
        assertEquals("Minimalista", mueble.getEstilo());
        assertEquals("120x80x75 cm", mueble.getDimensiones());
    }

    @Test
    @DisplayName("build: Error - todos los atributos null")
    void testBuildWithAllNulls() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        Mueble mueble = builder.build();

        assertNotNull(mueble);
        assertNull(mueble.getTipo());
        assertNull(mueble.getMaterial());
        assertNull(mueble.getEstilo());
        assertNull(mueble.getDimensiones());
    }

    @Test
    @DisplayName("build: Error - algunos atributos null")
    void testBuildWithSomeNulls() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        Mueble mueble = builder.setTipo("Sillón")
                .setMaterial(null)
                .setEstilo("Clásico")
                .setDimensiones(null)
                .build();

        assertNotNull(mueble);
        assertEquals("Sillón", mueble.getTipo());
        assertNull(mueble.getMaterial());
        assertEquals("Clásico", mueble.getEstilo());
        assertNull(mueble.getDimensiones());
    }

    @Test
    @DisplayName("Integración: encadenamiento completo")
    void testMethodChainingIntegration() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        MuebleBuilder chainResult = builder.setTipo("Cama")
                .setMaterial("Madera de roble")
                .setEstilo("Rústico")
                .setDimensiones("200x180x100 cm");

        assertSame(builder, chainResult);

        Mueble mueble = builder.build();

        assertNotNull(mueble);
        assertEquals("Cama", mueble.getTipo());
        assertEquals("Madera de roble", mueble.getMaterial());
        assertEquals("Rústico", mueble.getEstilo());
        assertEquals("200x180x100 cm", mueble.getDimensiones());
    }

    @Test
    @DisplayName("Integración: múltiples builds con mismo builder")
    void testMultipleBuildsSameBuilder() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        Mueble mueble1 = builder.setTipo("Silla").setMaterial("Plástico").build();
        Mueble mueble2 = builder.setTipo("Mesa").setMaterial("Madera").build();

        assertNotNull(mueble1);
        assertNotNull(mueble2);
        assertNotSame(mueble1, mueble2);

        assertEquals("Silla", mueble1.getTipo());
        assertEquals("Plástico", mueble1.getMaterial());

        assertEquals("Mesa", mueble2.getTipo());
        assertEquals("Madera", mueble2.getMaterial());
    }

    @Test
    @DisplayName("Integración: build sin setters")
    void testBuildWithoutSetters() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        Mueble mueble = builder.build();

        assertNotNull(mueble);
        assertNull(mueble.getTipo());
        assertNull(mueble.getMaterial());
        assertNull(mueble.getEstilo());
        assertNull(mueble.getDimensiones());
    }

    @Test
    @DisplayName("Integración: valores vacíos en atributos")
    void testBuildWithEmptyValues() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        Mueble mueble = builder.setTipo("")
                .setMaterial("")
                .setEstilo("")
                .setDimensiones("")
                .build();

        assertNotNull(mueble);
        assertEquals("", mueble.getTipo());
        assertEquals("", mueble.getMaterial());
        assertEquals("", mueble.getEstilo());
        assertEquals("", mueble.getDimensiones());
    }

    @Test
    @DisplayName("Component: verificar anotación Spring")
    void testSpringComponentAnnotation() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        assertNotNull(builder);

        Class<?> builderClass = builder.getClass();
        assertTrue(builderClass.isAnnotationPresent(org.springframework.stereotype.Component.class));
    }

    @Test
    @DisplayName("Implementación: verificar interfaz MuebleBuilder")
    void testImplementsMuebleBuilder() {
        ConcreteMuebleBuilder builder = new ConcreteMuebleBuilder();

        assertTrue(builder instanceof MuebleBuilder);
    }
}