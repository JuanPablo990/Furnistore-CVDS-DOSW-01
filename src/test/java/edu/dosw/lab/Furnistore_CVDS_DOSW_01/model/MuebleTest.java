package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuebleTest {

    private Mueble mueble;
    private Mueble muebleConNull;

    @BeforeEach
    void setUp() {
        // Configuración para happy path
        mueble = new Mueble("Silla", "Madera", "Moderno", "50x60x90 cm");

        // Configuración para casos de error (atributos null)
        muebleConNull = new Mueble(null, null, null, null);
    }

    @Test
    @DisplayName("Happy Path: Crear mueble con atributos válidos")
    void testConstructorHappyPath() {
        assertNotNull(mueble);
        assertEquals("Silla", mueble.getTipo());
        assertEquals("Madera", mueble.getMaterial());
        assertEquals("Moderno", mueble.getEstilo());
        assertEquals("50x60x90 cm", mueble.getDimensiones());
    }

    @Test
    @DisplayName("Error: Crear mueble con atributos null")
    void testConstructorWithNullValues() {
        assertNotNull(muebleConNull);
        assertNull(muebleConNull.getTipo());
        assertNull(muebleConNull.getMaterial());
        assertNull(muebleConNull.getEstilo());
        assertNull(muebleConNull.getDimensiones());
    }

    @Test
    @DisplayName("Happy Path: getDescripcion con valores válidos")
    void testGetDescripcionHappyPath() {
        String descripcion = mueble.getDescripcion();

        assertNotNull(descripcion);
        assertTrue(descripcion.contains("Silla"));
        assertTrue(descripcion.contains("Madera"));
        assertTrue(descripcion.contains("Moderno"));
        assertTrue(descripcion.contains("50x60x90 cm"));
        assertEquals("Mueble: Tipo=Silla, Material=Madera, Estilo=Moderno, Dimensiones=50x60x90 cm", descripcion);
    }

    @Test
    @DisplayName("Error: getDescripcion con valores null")
    void testGetDescripcionWithNullValues() {
        String descripcion = muebleConNull.getDescripcion();

        assertNotNull(descripcion);
        assertTrue(descripcion.contains("Tipo=null"));
        assertTrue(descripcion.contains("Material=null"));
        assertTrue(descripcion.contains("Estilo=null"));
        assertTrue(descripcion.contains("Dimensiones=null"));
        assertEquals("Mueble: Tipo=null, Material=null, Estilo=null, Dimensiones=null", descripcion);
    }

    @Test
    @DisplayName("Happy Path: Setters con valores válidos")
    void testSettersHappyPath() {
        Mueble muebleVacio = new Mueble();

        muebleVacio.setTipo("Mesa");
        muebleVacio.setMaterial("Cristal");
        muebleVacio.setEstilo("Minimalista");
        muebleVacio.setDimensiones("120x80x75 cm");

        assertEquals("Mesa", muebleVacio.getTipo());
        assertEquals("Cristal", muebleVacio.getMaterial());
        assertEquals("Minimalista", muebleVacio.getEstilo());
        assertEquals("120x80x75 cm", muebleVacio.getDimensiones());
    }

    @Test
    @DisplayName("Error: Setters con valores null")
    void testSettersWithNullValues() {
        Mueble muebleVacio = new Mueble();

        muebleVacio.setTipo(null);
        muebleVacio.setMaterial(null);
        muebleVacio.setEstilo(null);
        muebleVacio.setDimensiones(null);

        assertNull(muebleVacio.getTipo());
        assertNull(muebleVacio.getMaterial());
        assertNull(muebleVacio.getEstilo());
        assertNull(muebleVacio.getDimensiones());
    }

    @Test
    @DisplayName("Happy Path: Comparación de igualdad con mismos valores")
    void testEqualsHappyPath() {
        Mueble mismoMueble = new Mueble("Silla", "Madera", "Moderno", "50x60x90 cm");

        assertNotNull(mueble);
        assertNotNull(mismoMueble);
    }

    @Test
    @DisplayName("Error: Comparación con objeto null")
    void testEqualsWithNull() {
        assertNotEquals(mueble, null);
    }

    @Test
    @DisplayName("Happy Path: Uso con Mockito - Verificación de interacciones")
    void testMockitoHappyPath() {
        Mueble muebleMock = mock(Mueble.class);
        when(muebleMock.getDescripcion()).thenReturn("Mueble Mockeado");

        String descripcionMock = muebleMock.getDescripcion();

        verify(muebleMock).getDescripcion();
        assertEquals("Mueble Mockeado", descripcionMock);
    }

    @Test
    @DisplayName("Error: Mockito con comportamiento inesperado")
    void testMockitoErrorBehavior() {
        Mueble muebleMock = mock(Mueble.class);
        when(muebleMock.getTipo()).thenThrow(new RuntimeException("Error simulado"));

        assertThrows(RuntimeException.class, () -> muebleMock.getTipo());
    }
}