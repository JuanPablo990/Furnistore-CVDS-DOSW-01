package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

class FacturaDecoratorTest {

    @Test
    @DisplayName("Constructor: Happy Path - copia todos los datos correctamente")
    void testConstructorHappyPath() {
        Factura facturaMock = mock(Factura.class);
        Cliente clienteMock = mock(Cliente.class);
        List<ItemFactura> itemsMock = Arrays.asList(mock(ItemFactura.class));
        Date fechaMock = new Date();

        when(facturaMock.getId()).thenReturn("FAC-001");
        when(facturaMock.getCliente()).thenReturn(clienteMock);
        when(facturaMock.getFecha()).thenReturn(fechaMock);
        when(facturaMock.getItems()).thenReturn(itemsMock);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);

        assertEquals("FAC-001", decorator.getId());
        assertEquals(clienteMock, decorator.getCliente());
        assertEquals(fechaMock, decorator.getFecha());
        assertEquals(itemsMock, decorator.getItems());
        assertEquals(1000.0, decorator.getSubtotal());
        assertEquals(facturaMock, decorator.facturaDecorada);
    }

    @Test
    @DisplayName("Constructor: Error - factura null")
    void testConstructorWithNullFactura() {
        assertThrows(NullPointerException.class, () -> {
            IvaDecorator decorator = new IvaDecorator(null, 0.19);
        });
    }

    @Test
    @DisplayName("agregarItem: Happy Path - delega correctamente y actualiza subtotal")
    void testAgregarItemHappyPath() {
        Factura facturaMock = mock(Factura.class);
        ItemFactura itemMock = mock(ItemFactura.class);

        when(facturaMock.getSubtotal()).thenReturn(500.0);

        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);
        decorator.agregarItem(itemMock);

        verify(facturaMock, times(1)).agregarItem(itemMock);
        verify(facturaMock, atLeast(1)).getSubtotal();
        assertEquals(500.0, decorator.getSubtotal());
    }

    @Test
    @DisplayName("agregarItem: Error - item null")
    void testAgregarItemWithNull() {
        Factura facturaMock = mock(Factura.class);
        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);

        decorator.agregarItem(null);

        verify(facturaMock, times(1)).agregarItem(null);
    }

    @Test
    @DisplayName("agregarMueble: Happy Path - delega correctamente y actualiza subtotal")
    void testAgregarMuebleHappyPath() {
        Factura facturaMock = mock(Factura.class);
        Mueble muebleMock = mock(Mueble.class);

        when(facturaMock.getSubtotal()).thenReturn(750.0);

        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);
        decorator.agregarMueble(muebleMock, 2, 250.0);

        verify(facturaMock, times(1)).agregarMueble(muebleMock, 2, 250.0);
        verify(facturaMock, atLeast(1)).getSubtotal();
        assertEquals(750.0, decorator.getSubtotal());
    }

    @Test
    @DisplayName("agregarMueble: Error - mueble null")
    void testAgregarMuebleWithNull() {
        Factura facturaMock = mock(Factura.class);
        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);

        decorator.agregarMueble(null, 1, 100.0);

        verify(facturaMock, times(1)).agregarMueble(null, 1, 100.0);
    }

    @Test
    @DisplayName("agregarMueble: Error - cantidad negativa")
    void testAgregarMuebleWithNegativeCantidad() {
        Factura facturaMock = mock(Factura.class);
        Mueble muebleMock = mock(Mueble.class);
        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);

        decorator.agregarMueble(muebleMock, -1, 100.0);

        verify(facturaMock, times(1)).agregarMueble(muebleMock, -1, 100.0);
    }

    @Test
    @DisplayName("getTotalItems: Happy Path - delega correctamente")
    void testGetTotalItemsHappyPath() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getTotalItems()).thenReturn(5);

        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);
        int totalItems = decorator.getTotalItems();

        assertEquals(5, totalItems);
        verify(facturaMock, times(1)).getTotalItems();
    }

    @Test
    @DisplayName("getTotalItems: Error - factura devuelve cero items")
    void testGetTotalItemsWithZero() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getTotalItems()).thenReturn(0);

        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);
        int totalItems = decorator.getTotalItems();

        assertEquals(0, totalItems);
        verify(facturaMock, times(1)).getTotalItems();
    }

    @Test
    @DisplayName("Integración: múltiples operaciones mantienen estado consistente")
    void testMultipleOperationsConsistency() {
        Factura facturaMock = mock(Factura.class);
        Mueble muebleMock = mock(Mueble.class);
        ItemFactura itemMock = mock(ItemFactura.class);

        // Configuración corregida - solo una llamada a getTotalItems
        when(facturaMock.getSubtotal()).thenReturn(1000.0, 1500.0, 2000.0);
        when(facturaMock.getTotalItems()).thenReturn(5); // Solo un valor

        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);

        decorator.agregarMueble(muebleMock, 1, 500.0);
        assertEquals(1500.0, decorator.getSubtotal());

        decorator.agregarItem(itemMock);
        assertEquals(2000.0, decorator.getSubtotal());

        int totalItems = decorator.getTotalItems();
        assertEquals(5, totalItems);

        verify(facturaMock, times(1)).agregarMueble(muebleMock, 1, 500.0);
        verify(facturaMock, times(1)).agregarItem(itemMock);
        verify(facturaMock, times(1)).getTotalItems();
    }

    @Test
    @DisplayName("Herencia: método abstracto calcularTotal debe ser implementado")
    void testAbstractMethodImplementation() {
        Factura facturaMock = mock(Factura.class);
        when(facturaMock.getSubtotal()).thenReturn(1000.0);

        IvaDecorator decorator = new IvaDecorator(facturaMock, 0.19);

        assertDoesNotThrow(() -> {
            double total = decorator.calcularTotal();
            assertEquals(1190.0, total, 0.001);
        });
    }

    @Test
    @DisplayName("Delegación: verificación de llamadas encadenadas")
    void testDelegationChain() {
        Factura facturaMock = mock(Factura.class);
        FacturaDecorator decorator1 = new IvaDecorator(facturaMock, 0.19);
        FacturaDecorator decorator2 = new DescuentoDecorator(decorator1, 0.10);

        when(facturaMock.getSubtotal()).thenReturn(1000.0);
        when(facturaMock.getTotalItems()).thenReturn(2);

        decorator2.agregarItem(mock(ItemFactura.class));
        int totalItems = decorator2.getTotalItems();

        verify(facturaMock, times(1)).agregarItem(any(ItemFactura.class));
        verify(facturaMock, times(1)).getTotalItems();
        assertEquals(2, totalItems);
    }
}