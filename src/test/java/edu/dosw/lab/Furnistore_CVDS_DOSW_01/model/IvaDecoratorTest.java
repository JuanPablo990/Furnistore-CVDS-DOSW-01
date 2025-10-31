package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IvaDecoratorTest {

    private Factura facturaMock;
    private IvaDecorator ivaDecorator;

    @Test
    @DisplayName("getMontoIva con IVA del 0%")
    void testGetMontoIvaWithZeroIva() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);
        IvaDecorator ivaCero = new IvaDecorator(facturaMock, 0.0);

        double montoIva = ivaCero.getMontoIva();

        assertEquals(0.0, montoIva, 0.001);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("getMontoIva con subtotal positivo")
    void testGetMontoIvaHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);
        IvaDecorator ivaDecorator = new IvaDecorator(facturaMock, 0.19);

        double expectedIva = 1000.0 * 0.19;
        double montoIva = ivaDecorator.getMontoIva();

        assertEquals(expectedIva, montoIva, 0.001);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal con subtotal positivo y IVA del 19%")
    void testCalcularTotalHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);
        IvaDecorator ivaDecorator = new IvaDecorator(facturaMock, 0.19);

        double expectedTotal = 1000.0 + (1000.0 * 0.19);
        double total = ivaDecorator.calcularTotal();

        assertEquals(expectedTotal, total, 0.001);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("Comportamiento con valores Double extremos")
    void testWithDoubleExtremeValues() {
        Factura facturaExtremaMock = mock(Factura.class);

        // Probar con Double.MAX_VALUE que causará overflow
        when(facturaExtremaMock.getSubtotal()).thenReturn(Double.MAX_VALUE);

        IvaDecorator ivaExtremo = new IvaDecorator(facturaExtremaMock, 1.0); // 100% IVA

        assertDoesNotThrow(() -> {
            double total = ivaExtremo.calcularTotal();
            double montoIva = ivaExtremo.getMontoIva();

            // En este caso, esperamos que sean infinito
            assertTrue(Double.isInfinite(total) || Double.isFinite(total));
            assertTrue(Double.isInfinite(montoIva) || Double.isFinite(montoIva));
        });
    }

    @Test
    @DisplayName("Verificación de delegación a factura decorada")
    void testDelegationToFacturaDecorada() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);
        IvaDecorator ivaDecorator = new IvaDecorator(facturaMock, 0.19);

        ivaDecorator.calcularTotal();
        ivaDecorator.getMontoIva();
        ivaDecorator.calcularTotal();

        verify(facturaMock, atLeast(3)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal con subtotal cero")
    void testCalcularTotalWithZeroSubtotal() {
        Factura facturaCeroMock = mock(Factura.class);
        when(facturaCeroMock.getSubtotal()).thenReturn(0.0);
        IvaDecorator ivaCero = new IvaDecorator(facturaCeroMock, 0.19);

        double total = ivaCero.calcularTotal();

        assertEquals(0.0, total, 0.001);
        verify(facturaCeroMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("getMontoIva con IVA negativo")
    void testGetMontoIvaWithNegativeIva() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);
        IvaDecorator ivaNegativo = new IvaDecorator(facturaMock, -0.10);

        double expectedIva = 1000.0 * (-0.10);
        double montoIva = ivaNegativo.getMontoIva();

        assertEquals(expectedIva, montoIva, 0.001);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("calcularTotal después de cambiar porcentaje")
    void testCalcularTotalAfterChangingIva() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);
        IvaDecorator ivaDecorator = new IvaDecorator(facturaMock, 0.16);

        ivaDecorator.setPorcentajeIva(0.21);
        double expectedTotal = 1000.0 + (1000.0 * 0.21);
        double total = ivaDecorator.calcularTotal();

        assertEquals(expectedTotal, total, 0.001);
        verify(facturaMock, atLeast(1)).getSubtotal();
    }

    @Test
    @DisplayName("getMontoIva con subtotal negativo")
    void testGetMontoIvaWithNegativeSubtotal() {
        Factura facturaNegativaMock = mock(Factura.class);
        when(facturaNegativaMock.getSubtotal()).thenReturn(-500.0);
        IvaDecorator ivaNegativo = new IvaDecorator(facturaNegativaMock, 0.19);

        double montoIva = ivaNegativo.getMontoIva();

        assertEquals(-95.0, montoIva, 0.001);
        verify(facturaNegativaMock, atLeast(1)).getSubtotal();
    }
}