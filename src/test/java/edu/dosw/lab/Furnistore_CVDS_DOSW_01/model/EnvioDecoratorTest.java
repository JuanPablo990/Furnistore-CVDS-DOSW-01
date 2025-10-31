package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnvioDecoratorTest {

    @Test
    @DisplayName("Constructor simple: Happy Path")
    void testConstructorSimpleHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 50.0);

        assertEquals(50.0, decorator.getCostoEnvio());
        assertEquals("Estándar", decorator.getTipoEnvio());
        assertEquals(facturaMock, decorator.facturaDecorada);
    }

    @Test
    @DisplayName("Constructor simple: Error - costo envio negativo")
    void testConstructorSimpleWithNegativeCosto() {
        Factura facturaMock = mock(Factura.class);

        EnvioDecorator decorator = new EnvioDecorator(facturaMock, -20.0);

        assertEquals(-20.0, decorator.getCostoEnvio());
        assertEquals("Estándar", decorator.getTipoEnvio());
    }

    @Test
    @DisplayName("Constructor completo: Happy Path")
    void testConstructorCompletoHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 75.0, "Express");

        assertEquals(75.0, decorator.getCostoEnvio());
        assertEquals("Express", decorator.getTipoEnvio());
    }

    @Test
    @DisplayName("Constructor completo: Error - tipo envio null")
    void testConstructorCompletoWithNullTipo() {
        Factura facturaMock = mock(Factura.class);

        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 60.0, null);

        assertEquals(60.0, decorator.getCostoEnvio());
        assertNull(decorator.getTipoEnvio());
    }

    @Test
    @DisplayName("calcularTotal: Happy Path - subtotal positivo")
    void testCalcularTotalHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 100.0);

        double total = decorator.calcularTotal();

        assertEquals(1100.0, total);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal: Error - subtotal cero")
    void testCalcularTotalWithZeroSubtotal() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(0.0);

        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 50.0);

        double total = decorator.calcularTotal();

        assertEquals(50.0, total);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal: Error - costo envio negativo")
    void testCalcularTotalWithNegativeCosto() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(500.0);

        EnvioDecorator decorator = new EnvioDecorator(facturaMock, -100.0);

        double total = decorator.calcularTotal();

        assertEquals(400.0, total);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("getCostoEnvio: Happy Path")
    void testGetCostoEnvioHappyPath() {
        Factura facturaMock = mock(Factura.class);
        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 80.0);

        double costo = decorator.getCostoEnvio();

        assertEquals(80.0, costo);
    }

    @Test
    @DisplayName("getCostoEnvio: Error - valor cero")
    void testGetCostoEnvioWithZero() {
        Factura facturaMock = mock(Factura.class);
        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 0.0);

        double costo = decorator.getCostoEnvio();

        assertEquals(0.0, costo);
    }

    @Test
    @DisplayName("setCostoEnvio: Happy Path")
    void testSetCostoEnvioHappyPath() {
        Factura facturaMock = mock(Factura.class);
        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 50.0);

        decorator.setCostoEnvio(120.0);

        assertEquals(120.0, decorator.getCostoEnvio());
    }

    @Test
    @DisplayName("setCostoEnvio: Error - valor negativo")
    void testSetCostoEnvioWithNegative() {
        Factura facturaMock = mock(Factura.class);
        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 50.0);

        decorator.setCostoEnvio(-30.0);

        assertEquals(-30.0, decorator.getCostoEnvio());
    }

    @Test
    @DisplayName("getTipoEnvio: Happy Path")
    void testGetTipoEnvioHappyPath() {
        Factura facturaMock = mock(Factura.class);
        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 50.0, "Económico");

        String tipo = decorator.getTipoEnvio();

        assertEquals("Económico", tipo);
    }

    @Test
    @DisplayName("getTipoEnvio: Error - tipo vacío")
    void testGetTipoEnvioWithEmpty() {
        Factura facturaMock = mock(Factura.class);
        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 50.0, "");

        String tipo = decorator.getTipoEnvio();

        assertEquals("", tipo);
    }

    @Test
    @DisplayName("setTipoEnvio: Happy Path")
    void testSetTipoEnvioHappyPath() {
        Factura facturaMock = mock(Factura.class);
        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 50.0);

        decorator.setTipoEnvio("Urgente");

        assertEquals("Urgente", decorator.getTipoEnvio());
    }

    @Test
    @DisplayName("setTipoEnvio: Error - tipo null")
    void testSetTipoEnvioWithNull() {
        Factura facturaMock = mock(Factura.class);
        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 50.0);

        decorator.setTipoEnvio(null);

        assertNull(decorator.getTipoEnvio());
    }

    @Test
    @DisplayName("Integración: calcularTotal después de cambiar costo")
    void testCalcularTotalAfterChangingCosto() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(800.0);

        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 60.0);

        double total1 = decorator.calcularTotal();
        assertEquals(860.0, total1);

        decorator.setCostoEnvio(90.0);
        double total2 = decorator.calcularTotal();
        assertEquals(890.0, total2);

        verify(facturaMock, atLeast(2)).getSubtotal();
    }

    @Test
    @DisplayName("Integración: múltiples cambios de tipo envio")
    void testMultipleTipoEnvioChanges() {
        Factura facturaMock = mock(Factura.class);
        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 40.0, "Inicial");

        assertEquals("Inicial", decorator.getTipoEnvio());

        decorator.setTipoEnvio("Modificado");
        assertEquals("Modificado", decorator.getTipoEnvio());

        decorator.setTipoEnvio("Final");
        assertEquals("Final", decorator.getTipoEnvio());
    }

    @Test
    @DisplayName("Delegación: verificación de llamadas a factura decorada")
    void testDelegationToFacturaDecorada() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(700.0);

        EnvioDecorator decorator = new EnvioDecorator(facturaMock, 80.0);

        decorator.calcularTotal();
        decorator.calcularTotal();

        verify(facturaMock, atLeast(2)).getSubtotal();
    }
}