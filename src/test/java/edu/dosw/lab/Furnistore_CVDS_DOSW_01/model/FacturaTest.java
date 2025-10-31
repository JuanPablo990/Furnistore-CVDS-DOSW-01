package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

class FacturaTest {

    private Factura factura;
    private Cliente clienteMock;
    private Mueble muebleMock;
    private ItemFactura itemMock;

    @BeforeEach
    void setUp() {
        clienteMock = mock(Cliente.class);
        muebleMock = mock(Mueble.class);
        itemMock = mock(ItemFactura.class);

        factura = new Factura(clienteMock);
    }

    @Test
    @DisplayName("Constructor con cliente: Happy Path")
    void testConstructorWithClienteHappyPath() {
        assertEquals(clienteMock, factura.getCliente());
        assertNotNull(factura.getFecha());
        assertNotNull(factura.getItems());
        assertTrue(factura.getItems().isEmpty());
        assertEquals(0.0, factura.getSubtotal());
        assertEquals(0.0, factura.getTotal());
    }

    @Test
    @DisplayName("Constructor con cliente: Error - cliente null")
    void testConstructorWithNullCliente() {
        Factura facturaNullCliente = new Factura(null);

        assertNull(facturaNullCliente.getCliente());
        assertNotNull(facturaNullCliente.getFecha());
        assertNotNull(facturaNullCliente.getItems());
    }

    @Test
    @DisplayName("Constructor por defecto: Happy Path")
    void testNoArgsConstructorHappyPath() {
        Factura facturaVacia = new Factura();

        assertNull(facturaVacia.getCliente());
        assertNull(facturaVacia.getFecha());
        assertNull(facturaVacia.getItems());
        assertEquals(0.0, facturaVacia.getSubtotal());
        assertEquals(0.0, facturaVacia.getTotal());
    }

    @Test
    @DisplayName("agregarItem: Happy Path - item válido")
    void testAgregarItemHappyPath() {
        when(itemMock.calcularTotalItem()).thenReturn(250.0);
        when(itemMock.getCantidad()).thenReturn(2);

        factura.agregarItem(itemMock);

        assertEquals(1, factura.getItems().size());
        assertEquals(itemMock, factura.getItems().get(0));
        assertEquals(250.0, factura.getSubtotal());
        assertEquals(250.0, factura.getTotal());
        verify(itemMock, times(1)).calcularTotalItem();
    }

    @Test
    @DisplayName("agregarItem: Error - item null")
    void testAgregarItemWithNull() {
        assertThrows(NullPointerException.class, () -> {
            factura.agregarItem(null);
        });
    }

    @Test
    @DisplayName("agregarMueble: Happy Path - parámetros válidos")
    void testAgregarMuebleHappyPath() {
        factura.agregarMueble(muebleMock, 3, 100.0);

        assertEquals(1, factura.getItems().size());
        ItemFactura itemCreado = factura.getItems().get(0);
        assertEquals(muebleMock, itemCreado.getProducto());
        assertEquals(3, itemCreado.getCantidad());
        assertEquals(100.0, itemCreado.getPrecioUnitario());
        assertEquals(300.0, factura.getSubtotal());
    }

    @Test
    @DisplayName("agregarMueble: Error - cantidad cero")
    void testAgregarMuebleWithZeroCantidad() {
        factura.agregarMueble(muebleMock, 0, 50.0);

        assertEquals(1, factura.getItems().size());
        ItemFactura itemCreado = factura.getItems().get(0);
        assertEquals(0, itemCreado.getCantidad());
        assertEquals(0.0, factura.getSubtotal());
    }

    @Test
    @DisplayName("getTotalItems: Happy Path - múltiples items")
    void testGetTotalItemsHappyPath() {
        ItemFactura item1 = mock(ItemFactura.class);
        ItemFactura item2 = mock(ItemFactura.class);

        when(item1.getCantidad()).thenReturn(2);
        when(item2.getCantidad()).thenReturn(3);
        when(item1.calcularTotalItem()).thenReturn(200.0);
        when(item2.calcularTotalItem()).thenReturn(300.0);

        factura.agregarItem(item1);
        factura.agregarItem(item2);

        int totalItems = factura.getTotalItems();

        assertEquals(5, totalItems);
        verify(item1, times(1)).getCantidad();
        verify(item2, times(1)).getCantidad();
    }

    @Test
    @DisplayName("getTotalItems: Error - sin items")
    void testGetTotalItemsWithNoItems() {
        int totalItems = factura.getTotalItems();

        assertEquals(0, totalItems);
    }

    @Test
    @DisplayName("calcularTotal: Happy Path - con items")
    void testCalcularTotalHappyPath() {
        ItemFactura item1 = mock(ItemFactura.class);
        ItemFactura item2 = mock(ItemFactura.class);

        when(item1.calcularTotalItem()).thenReturn(150.0);
        when(item2.calcularTotalItem()).thenReturn(200.0);

        factura.agregarItem(item1);
        factura.agregarItem(item2);

        double total = factura.calcularTotal();

        assertEquals(350.0, total);
        assertEquals(350.0, factura.getTotal());
    }

    @Test
    @DisplayName("calcularTotal: Error - items con precios negativos")
    void testCalcularTotalWithNegativePrices() {
        ItemFactura itemNegativo = mock(ItemFactura.class);

        when(itemNegativo.calcularTotalItem()).thenReturn(-100.0);

        factura.agregarItem(itemNegativo);

        double total = factura.calcularTotal();

        assertEquals(-100.0, total);
    }

    @Test
    @DisplayName("recalcularTotales: Happy Path - actualización automática")
    void testRecalcularTotalesAutomatically() {
        ItemFactura item1 = mock(ItemFactura.class);
        ItemFactura item2 = mock(ItemFactura.class);

        when(item1.calcularTotalItem()).thenReturn(100.0);
        when(item2.calcularTotalItem()).thenReturn(200.0);

        // agregarItem llama a recalcularTotales automáticamente
        factura.agregarItem(item1);
        assertEquals(100.0, factura.getSubtotal());
        assertEquals(100.0, factura.getTotal());

        factura.agregarItem(item2);
        assertEquals(300.0, factura.getSubtotal());
        assertEquals(300.0, factura.getTotal());
    }

    @Test
    @DisplayName("Setters: Happy Path - actualización de propiedades")
    void testSettersHappyPath() {
        Factura facturaVacia = new Factura();
        Cliente nuevoCliente = mock(Cliente.class);
        Date nuevaFecha = new Date();
        List<ItemFactura> nuevosItems = Arrays.asList(mock(ItemFactura.class));

        facturaVacia.setId("FAC-001");
        facturaVacia.setCliente(nuevoCliente);
        facturaVacia.setFecha(nuevaFecha);
        facturaVacia.setItems(nuevosItems);
        facturaVacia.setSubtotal(500.0);
        facturaVacia.setTotal(600.0);

        assertEquals("FAC-001", facturaVacia.getId());
        assertEquals(nuevoCliente, facturaVacia.getCliente());
        assertEquals(nuevaFecha, facturaVacia.getFecha());
        assertEquals(nuevosItems, facturaVacia.getItems());
        assertEquals(500.0, facturaVacia.getSubtotal());
        assertEquals(600.0, facturaVacia.getTotal());
    }

    @Test
    @DisplayName("Integración: múltiples operaciones secuenciales")
    void testMultipleOperationsIntegration() {
        Mueble mueble1 = mock(Mueble.class);
        Mueble mueble2 = mock(Mueble.class);
        ItemFactura itemExtra = mock(ItemFactura.class);

        when(itemExtra.calcularTotalItem()).thenReturn(400.0);
        when(itemExtra.getCantidad()).thenReturn(4);

        // Agregar muebles
        factura.agregarMueble(mueble1, 2, 100.0);
        assertEquals(200.0, factura.getSubtotal());
        assertEquals(2, factura.getTotalItems());

        factura.agregarMueble(mueble2, 1, 150.0);
        assertEquals(350.0, factura.getSubtotal());
        assertEquals(3, factura.getTotalItems());

        // Agregar item directo
        factura.agregarItem(itemExtra);
        assertEquals(750.0, factura.getSubtotal());
        assertEquals(7, factura.getTotalItems());

        // Verificar calcularTotal
        double total = factura.calcularTotal();
        assertEquals(750.0, total);
    }

    @Test
    @DisplayName("Error: agregarMueble con precio unitario negativo")
    void testAgregarMuebleWithNegativePrice() {
        factura.agregarMueble(muebleMock, 2, -50.0);

        assertEquals(1, factura.getItems().size());
        ItemFactura itemCreado = factura.getItems().get(0);
        assertEquals(-50.0, itemCreado.getPrecioUnitario());
        assertEquals(-100.0, factura.getSubtotal());
    }

    @Test
    @DisplayName("Happy Path: fecha se establece automáticamente en constructor")
    void testFechaAutoSetInConstructor() {
        Date antes = new Date();
        Factura nuevaFactura = new Factura(clienteMock);
        Date despues = new Date();

        Date fechaFactura = nuevaFactura.getFecha();
        assertTrue(fechaFactura.equals(antes) || fechaFactura.after(antes));
        assertTrue(fechaFactura.equals(despues) || fechaFactura.before(despues));
    }
}