package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemFacturaTest {

    @Test
    @DisplayName("calcularTotalItem: Happy Path - valores positivos")
    void testCalcularTotalItemHappyPath() {
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura item = new ItemFactura(muebleMock, 5, 100.0);

        double total = item.calcularTotalItem();

        assertEquals(500.0, total, 0.001);
    }

    @Test
    @DisplayName("calcularTotalItem: Error - cantidad cero")
    void testCalcularTotalItemWithZeroCantidad() {
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura item = new ItemFactura(muebleMock, 0, 100.0);

        double total = item.calcularTotalItem();

        assertEquals(0.0, total, 0.001);
    }

    @Test
    @DisplayName("calcularTotalItem: Error - precio unitario negativo")
    void testCalcularTotalItemWithNegativePrecio() {
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura item = new ItemFactura(muebleMock, 3, -50.0);

        double total = item.calcularTotalItem();

        assertEquals(-150.0, total, 0.001);
    }

    @Test
    @DisplayName("Constructor completo: Happy Path")
    void testConstructorAllArgsHappyPath() {
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura item = new ItemFactura(muebleMock, 2, 75.5);

        assertEquals(muebleMock, item.getProducto());
        assertEquals(2, item.getCantidad());
        assertEquals(75.5, item.getPrecioUnitario(), 0.001);
    }

    @Test
    @DisplayName("Constructor completo: Error - cantidad negativa")
    void testConstructorAllArgsWithNegativeCantidad() {
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura item = new ItemFactura(muebleMock, -1, 50.0);

        assertEquals(-1, item.getCantidad());
        assertEquals(50.0, item.getPrecioUnitario(), 0.001);
    }

    @Test
    @DisplayName("Constructor simplificado: Happy Path")
    void testConstructorSimplificadoHappyPath() {
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura item = new ItemFactura(muebleMock, 4);

        assertEquals(muebleMock, item.getProducto());
        assertEquals(4, item.getCantidad());
        assertEquals(0.0, item.getPrecioUnitario(), 0.001);
    }

    @Test
    @DisplayName("Constructor simplificado: Error - cantidad cero")
    void testConstructorSimplificadoWithZeroCantidad() {
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura item = new ItemFactura(muebleMock, 0);

        assertEquals(0, item.getCantidad());
        assertEquals(0.0, item.getPrecioUnitario(), 0.001);
    }

    @Test
    @DisplayName("Setters: Happy Path")
    void testSettersHappyPath() {
        ItemFactura item = new ItemFactura();
        Mueble muebleMock = mock(Mueble.class);

        item.setProducto(muebleMock);
        item.setCantidad(10);
        item.setPrecioUnitario(99.99);

        assertEquals(muebleMock, item.getProducto());
        assertEquals(10, item.getCantidad());
        assertEquals(99.99, item.getPrecioUnitario(), 0.001);
    }

    @Test
    @DisplayName("Setters: Error - valores extremos")
    void testSettersWithExtremeValues() {
        ItemFactura item = new ItemFactura();
        Mueble muebleMock = mock(Mueble.class);

        item.setProducto(muebleMock);
        item.setCantidad(Integer.MAX_VALUE);
        item.setPrecioUnitario(Double.MAX_VALUE);

        assertEquals(Integer.MAX_VALUE, item.getCantidad());
        assertEquals(Double.MAX_VALUE, item.getPrecioUnitario(), 0.001);

        double total = item.calcularTotalItem();
        assertTrue(Double.isInfinite(total));
    }

    @Test
    @DisplayName("calcularTotalItem: Error - overflow por valores grandes")
    void testCalcularTotalItemWithLargeValues() {
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura item = new ItemFactura(muebleMock, Integer.MAX_VALUE, Double.MAX_VALUE);

        double total = item.calcularTotalItem();

        assertTrue(Double.isInfinite(total));
    }

    @Test
    @DisplayName("Constructor por defecto: Happy Path")
    void testNoArgsConstructorHappyPath() {
        ItemFactura item = new ItemFactura();

        assertNull(item.getProducto());
        assertEquals(0, item.getCantidad());
        assertEquals(0.0, item.getPrecioUnitario(), 0.001);
    }

    @Test
    @DisplayName("Integración: calcularTotalItem después de setters")
    void testCalcularTotalItemAfterSetters() {
        ItemFactura item = new ItemFactura();
        Mueble muebleMock = mock(Mueble.class);

        item.setProducto(muebleMock);
        item.setCantidad(3);
        item.setPrecioUnitario(25.0);

        double total = item.calcularTotalItem();
        assertEquals(75.0, total, 0.001);
    }

    @Test
    @DisplayName("Integración: múltiples cálculos consecutivos")
    void testMultipleCalculations() {
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura item = new ItemFactura(muebleMock, 2, 30.0);

        double total1 = item.calcularTotalItem();
        item.setCantidad(4);
        double total2 = item.calcularTotalItem();
        item.setPrecioUnitario(40.0);
        double total3 = item.calcularTotalItem();

        assertEquals(60.0, total1, 0.001);
        assertEquals(120.0, total2, 0.001);
        assertEquals(160.0, total3, 0.001);
    }
}