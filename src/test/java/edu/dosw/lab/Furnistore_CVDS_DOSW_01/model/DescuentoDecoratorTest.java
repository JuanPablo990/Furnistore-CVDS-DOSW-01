package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DescuentoDecoratorTest {

    @Test
    @DisplayName("Constructor porcentual: Happy Path")
    void testConstructorPorcentualHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 0.10);

        assertEquals(0.10, decorator.getPorcentajeDescuento());
        assertTrue(decorator.isEsPorcentual());
        assertEquals(facturaMock, decorator.facturaDecorada);
    }

    @Test
    @DisplayName("Constructor porcentual: Error - porcentaje negativo")
    void testConstructorPorcentualWithNegative() {
        Factura facturaMock = mock(Factura.class);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, -0.05);

        assertEquals(-0.05, decorator.getPorcentajeDescuento());
        assertTrue(decorator.isEsPorcentual());
    }

    @Test
    @DisplayName("Constructor monto fijo: Happy Path")
    void testConstructorMontoFijoHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 100);

        assertEquals(100.0, decorator.getMontoFijoDescuento());
        assertFalse(decorator.isEsPorcentual());
    }

    @Test
    @DisplayName("Constructor monto fijo: Error - monto negativo")
    void testConstructorMontoFijoWithNegative() {
        Factura facturaMock = mock(Factura.class);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, -50);

        assertEquals(-50.0, decorator.getMontoFijoDescuento());
        assertFalse(decorator.isEsPorcentual());
    }

    @Test
    @DisplayName("calcularTotal porcentual: Happy Path")
    void testCalcularTotalPorcentualHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 0.20);

        double total = decorator.calcularTotal();

        assertEquals(800.0, total);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal porcentual: Error - descuento mayor al subtotal")
    void testCalcularTotalPorcentualWithLargeDiscount() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(100.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 1.50);

        double total = decorator.calcularTotal();

        assertEquals(0.0, total);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal monto fijo: Happy Path")
    void testCalcularTotalMontoFijoHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(500.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 100);

        double total = decorator.calcularTotal();

        assertEquals(400.0, total);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal monto fijo: Error - monto mayor al subtotal")
    void testCalcularTotalMontoFijoWithLargeDiscount() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(50.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 100);

        double total = decorator.calcularTotal();

        assertEquals(0.0, total);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal: Error - subtotal cero")
    void testCalcularTotalWithZeroSubtotal() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(0.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 0.10);

        double total = decorator.calcularTotal();

        assertEquals(0.0, total);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("getMontoDescuento porcentual: Happy Path")
    void testGetMontoDescuentoPorcentualHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 0.15);

        double montoDescuento = decorator.getMontoDescuento();

        assertEquals(150.0, montoDescuento);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("getMontoDescuento monto fijo: Happy Path")
    void testGetMontoDescuentoMontoFijoHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 200);

        double montoDescuento = decorator.getMontoDescuento();

        assertEquals(200.0, montoDescuento);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("getMontoDescuento: Error - porcentaje negativo")
    void testGetMontoDescuentoWithNegativePorcentual() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(500.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, -0.10);

        double montoDescuento = decorator.getMontoDescuento();

        assertEquals(-50.0, montoDescuento);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("setPorcentajeDescuento: Happy Path")
    void testSetPorcentajeDescuentoHappyPath() {
        Factura facturaMock = mock(Factura.class);
        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 100);

        decorator.setPorcentajeDescuento(0.25);

        assertEquals(0.25, decorator.getPorcentajeDescuento());
        assertTrue(decorator.isEsPorcentual());
    }

    @Test
    @DisplayName("setPorcentajeDescuento: Error - porcentaje negativo")
    void testSetPorcentajeDescuentoWithNegative() {
        Factura facturaMock = mock(Factura.class);
        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 0.10);

        decorator.setPorcentajeDescuento(-0.05);

        assertEquals(-0.05, decorator.getPorcentajeDescuento());
        assertTrue(decorator.isEsPorcentual());
    }

    @Test
    @DisplayName("setMontoFijoDescuento: Happy Path")
    void testSetMontoFijoDescuentoHappyPath() {
        Factura facturaMock = mock(Factura.class);
        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 0.10);

        decorator.setMontoFijoDescuento(150.0);

        assertEquals(150.0, decorator.getMontoFijoDescuento());
        assertFalse(decorator.isEsPorcentual());
    }

    @Test
    @DisplayName("setMontoFijoDescuento: Error - monto negativo")
    void testSetMontoFijoDescuentoWithNegative() {
        Factura facturaMock = mock(Factura.class);
        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 100);

        decorator.setMontoFijoDescuento(-75.0);

        assertEquals(-75.0, decorator.getMontoFijoDescuento());
        assertFalse(decorator.isEsPorcentual());
    }

    @Test
    @DisplayName("Integración: cambiar de porcentual a monto fijo")
    void testSwitchFromPorcentualToMontoFijo() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 0.20);

        assertTrue(decorator.isEsPorcentual());
        double descuento1 = decorator.getMontoDescuento();
        assertEquals(200.0, descuento1);

        decorator.setMontoFijoDescuento(300.0);

        assertFalse(decorator.isEsPorcentual());
        double descuento2 = decorator.getMontoDescuento();
        assertEquals(300.0, descuento2);

        double total = decorator.calcularTotal();
        assertEquals(700.0, total);
    }

    @Test
    @DisplayName("Integración: cambiar de monto fijo a porcentual")
    void testSwitchFromMontoFijoToPorcentual() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(800.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 150);

        assertFalse(decorator.isEsPorcentual());
        double descuento1 = decorator.getMontoDescuento();
        assertEquals(150.0, descuento1);

        decorator.setPorcentajeDescuento(0.10);

        assertTrue(decorator.isEsPorcentual());
        double descuento2 = decorator.getMontoDescuento();
        assertEquals(80.0, descuento2);

        double total = decorator.calcularTotal();
        assertEquals(720.0, total);
    }

    @Test
    @DisplayName("Delegación: verificación de llamadas a factura decorada")
    void testDelegationToFacturaDecorada() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 0.15);

        decorator.calcularTotal();
        decorator.getMontoDescuento();
        decorator.calcularTotal();

        verify(facturaMock, atLeast(2)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal: Edge case - descuento igual al subtotal")
    void testCalcularTotalWithDiscountEqualToSubtotal() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(500.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 500);

        double total = decorator.calcularTotal();

        assertEquals(0.0, total);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("getMontoDescuento: Edge case - subtotal cero con porcentaje")
    void testGetMontoDescuentoWithZeroSubtotal() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(0.0);

        DescuentoDecorator decorator = new DescuentoDecorator(facturaMock, 0.50);

        double montoDescuento = decorator.getMontoDescuento();

        assertEquals(0.0, montoDescuento);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }
}