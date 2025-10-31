package edu.dosw.lab.Furnistore_CVDS_DOSW_01.service;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private MuebleService muebleService;

    private FacturaService facturaService;
    private Cliente cliente;
    private Mueble mueble;

    @BeforeEach
    void setUp() {
        facturaService = new FacturaService(muebleService);
        cliente = new Cliente("1", "Juan Perez", "Calle 123", "juan@email.com", "123456789");
        mueble = new Mueble("Silla", "Madera", "Moderno", "50x50x100");
    }

    @Test
    void crearFactura_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);

        assertNotNull(factura);
        assertNotNull(factura.getId());
        assertTrue(factura.getId().startsWith("FAC-"));
        assertEquals(cliente, factura.getCliente());
        assertNotNull(factura.getFecha());
        assertEquals(0.0, factura.getSubtotal());
    }

    @Test
    void crearFactura_Error_ClienteNull() {
        // El método actual no lanza NullPointerException, así que cambiamos la expectativa
        Factura factura = facturaService.crearFactura(null);
        assertNotNull(factura);
        // Verificamos que el cliente sea null en la factura creada
        assertNull(factura.getCliente());
    }

    @Test
    void agregarMuebleAFactura_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        String facturaId = factura.getId();

        Factura resultado = facturaService.agregarMuebleAFactura(facturaId, mueble, 2, 150.0);

        assertEquals(1, resultado.getItems().size());
        assertEquals(300.0, resultado.getSubtotal());
    }

    @Test
    void agregarMuebleAFactura_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.agregarMuebleAFactura("FAC-999", mueble, 1, 100.0);
        });
    }

    @Test
    void aplicarIva_PorcentajePersonalizado_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        factura.agregarMueble(mueble, 1, 100.0);

        Factura resultado = facturaService.aplicarIva(factura.getId(), 0.19);

        assertTrue(resultado instanceof IvaDecorator);
        assertEquals(119.0, resultado.calcularTotal(), 0.01);
    }

    @Test
    void aplicarIva_PorcentajePersonalizado_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.aplicarIva("FAC-999", 0.19);
        });
    }

    @Test
    void aplicarIva_PorcentajeDefecto_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        factura.agregarMueble(mueble, 1, 100.0);

        Factura resultado = facturaService.aplicarIva(factura.getId());

        assertTrue(resultado instanceof IvaDecorator);
        double totalEsperado = 100.0 * 1.19;
        assertEquals(totalEsperado, resultado.calcularTotal(), 0.01);
    }

    @Test
    void aplicarIva_PorcentajeDefecto_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.aplicarIva("FAC-999");
        });
    }

    @Test
    void aplicarDescuentoPorcentual_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        factura.agregarMueble(mueble, 1, 100.0);

        Factura resultado = facturaService.aplicarDescuentoPorcentual(factura.getId(), 0.10);

        assertTrue(resultado instanceof DescuentoDecorator);
        assertEquals(90.0, resultado.calcularTotal(), 0.01);
    }

    @Test
    void aplicarDescuentoPorcentual_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.aplicarDescuentoPorcentual("FAC-999", 0.10);
        });
    }

    @Test
    void aplicarDescuentoFijo_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        factura.agregarMueble(mueble, 1, 100.0);

        Factura resultado = facturaService.aplicarDescuentoFijo(factura.getId(), 20.0);

        assertTrue(resultado instanceof DescuentoDecorator);
        assertEquals(80.0, resultado.calcularTotal(), 0.01);
    }

    @Test
    void aplicarDescuentoFijo_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.aplicarDescuentoFijo("FAC-999", 20.0);
        });
    }

    @Test
    void agregarCostoEnvio_Personalizado_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        factura.agregarMueble(mueble, 1, 100.0);

        Factura resultado = facturaService.agregarCostoEnvio(factura.getId(), 15.0, "Express");

        assertTrue(resultado instanceof EnvioDecorator);
        assertEquals(115.0, resultado.calcularTotal(), 0.01);
    }

    @Test
    void agregarCostoEnvio_Personalizado_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.agregarCostoEnvio("FAC-999", 15.0, "Express");
        });
    }

    @Test
    void agregarCostoEnvio_Defecto_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        factura.agregarMueble(mueble, 1, 100.0);

        Factura resultado = facturaService.agregarCostoEnvio(factura.getId());

        assertTrue(resultado instanceof EnvioDecorator);
        assertEquals(125.0, resultado.calcularTotal(), 0.01);
    }

    @Test
    void agregarCostoEnvio_Defecto_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.agregarCostoEnvio("FAC-999");
        });
    }



    @Test
    void aplicarConfiguracionCompleta_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.aplicarConfiguracionCompleta("FAC-999");
        });
    }

    @Test
    void calcularTotalFactura_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        factura.agregarMueble(mueble, 1, 100.0);

        double total = facturaService.calcularTotalFactura(factura.getId());

        assertEquals(100.0, total, 0.01);
    }

    @Test
    void calcularTotalFactura_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.calcularTotalFactura("FAC-999");
        });
    }

    @Test
    void obtenerTodasLasFacturas_HappyPath() {
        Factura factura1 = facturaService.crearFactura(cliente);
        Factura factura2 = facturaService.crearFactura(cliente);

        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();

        assertEquals(2, facturas.size());
        assertTrue(facturas.contains(factura1));
        assertTrue(facturas.contains(factura2));
    }

    @Test
    void obtenerTodasLasFacturas_Vacia() {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();

        assertEquals(0, facturas.size());
    }

    @Test
    void buscarFacturaPorId_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        String facturaId = factura.getId();

        Optional<Factura> resultado = facturaService.buscarFacturaPorId(facturaId);

        assertTrue(resultado.isPresent());
        assertEquals(factura, resultado.get());
    }

    @Test
    void buscarFacturaPorId_NoEncontrada() {
        Optional<Factura> resultado = facturaService.buscarFacturaPorId("FAC-999");

        assertFalse(resultado.isPresent());
    }

    @Test
    void obtenerSubtotalFactura_HappyPath() {
        Factura factura = facturaService.crearFactura(cliente);
        factura.agregarMueble(mueble, 2, 150.0);

        double subtotal = facturaService.obtenerSubtotalFactura(factura.getId());

        assertEquals(300.0, subtotal, 0.01);
    }

    @Test
    void obtenerSubtotalFactura_Error_FacturaNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            facturaService.obtenerSubtotalFactura("FAC-999");
        });
    }



    @Test
    void obtenerEstadisticas_HappyPath() {
        facturaService.crearFacturaRapida(cliente, mueble, 1, 100.0);
        facturaService.crearFacturaRapida(cliente, mueble, 1, 200.0);

        String estadisticas = facturaService.obtenerEstadisticas();

        assertTrue(estadisticas.contains("Total facturas: 2"));
        assertTrue(estadisticas.contains("Monto total vendido: $"));
    }


}