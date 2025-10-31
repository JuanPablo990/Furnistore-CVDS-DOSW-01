package edu.dosw.lab.Furnistore_CVDS_DOSW_01.controller;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.*;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.FacturaService;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.MuebleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacturaControllerTest {

    @Mock
    private FacturaService facturaService;

    @Mock
    private MuebleService muebleService;

    @InjectMocks
    private FacturaController facturaController;

    @Test
    public void testCrearFacturaHappy() {
        Cliente cliente = new Cliente("Juan Perez", "juan@test.com");
        Factura factura = new Factura(cliente);
        when(facturaService.crearFactura(cliente)).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.crearFactura(cliente);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).crearFactura(cliente);
    }

    @Test
    public void testCrearFacturaError() {
        Cliente cliente = new Cliente("Juan Perez", "juan@test.com");
        when(facturaService.crearFactura(cliente)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Factura> response = facturaController.crearFactura(cliente);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testObtenerTodasLasFacturasHappy() {
        List<Factura> facturas = Arrays.asList(
                new Factura(new Cliente("Cliente1", "cliente1@test.com")),
                new Factura(new Cliente("Cliente2", "cliente2@test.com"))
        );
        when(facturaService.obtenerTodasLasFacturas()).thenReturn(facturas);

        ResponseEntity<List<Factura>> response = facturaController.obtenerTodasLasFacturas();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(facturaService).obtenerTodasLasFacturas();
    }

    @Test
    public void testObtenerFacturaHappy() {
        Factura factura = new Factura(new Cliente("Cliente1", "cliente1@test.com"));
        when(facturaService.buscarFacturaPorId("123")).thenReturn(Optional.of(factura));

        ResponseEntity<Factura> response = facturaController.obtenerFactura("123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).buscarFacturaPorId("123");
    }

    @Test
    public void testObtenerFacturaNotFound() {
        when(facturaService.buscarFacturaPorId("999")).thenReturn(Optional.empty());

        ResponseEntity<Factura> response = facturaController.obtenerFactura("999");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAgregarMuebleAFacturaHappy() {
        Mueble mueble = new Mueble("Sofá", "Cuero", "Clásico", "200x90x100 cm");
        Factura factura = new Factura(new Cliente("Cliente1", "cliente1@test.com"));

        Map<String, Object> request = new HashMap<>();
        request.put("muebleTipo", "Sofá");
        request.put("cantidad", 2);
        request.put("precioUnitario", 1500.0);

        when(muebleService.obtenerMueblePorTipo("Sofá")).thenReturn(Optional.of(mueble));
        when(facturaService.agregarMuebleAFactura("123", mueble, 2, 1500.0)).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.agregarMuebleAFactura("123", request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(muebleService).obtenerMueblePorTipo("Sofá");
        verify(facturaService).agregarMuebleAFactura("123", mueble, 2, 1500.0);
    }

    @Test
    public void testAgregarMuebleAFacturaErrorMuebleNoEncontrado() {
        Map<String, Object> request = new HashMap<>();
        request.put("muebleTipo", "NoExiste");
        request.put("cantidad", 1);
        request.put("precioUnitario", 1000.0);

        when(muebleService.obtenerMueblePorTipo("NoExiste")).thenReturn(Optional.empty());

        ResponseEntity<Factura> response = facturaController.agregarMuebleAFactura("123", request);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAgregarMuebleAFacturaErrorGeneral() {
        Map<String, Object> request = new HashMap<>();
        request.put("muebleTipo", "Sofá");
        request.put("cantidad", 1);
        request.put("precioUnitario", 1000.0);

        when(muebleService.obtenerMueblePorTipo("Sofá")).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Factura> response = facturaController.agregarMuebleAFactura("123", request);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAplicarIvaHappy() {
        Factura factura = new Factura(new Cliente("Cliente1", "cliente1@test.com"));
        when(facturaService.aplicarIva("123")).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.aplicarIva("123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).aplicarIva("123");
    }

    @Test
    public void testAplicarIvaNotFound() {
        when(facturaService.aplicarIva("999")).thenThrow(new RuntimeException("No encontrado"));

        ResponseEntity<Factura> response = facturaController.aplicarIva("999");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAplicarIvaConPorcentajeHappy() {
        Factura factura = new Factura(new Cliente("Cliente1", "cliente1@test.com"));
        when(facturaService.aplicarIva("123", 0.19)).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.aplicarIvaConPorcentaje("123", 0.19);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).aplicarIva("123", 0.19);
    }

    @Test
    public void testAplicarIvaConPorcentajeNotFound() {
        when(facturaService.aplicarIva("999", 0.19)).thenThrow(new RuntimeException("No encontrado"));

        ResponseEntity<Factura> response = facturaController.aplicarIvaConPorcentaje("999", 0.19);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAplicarDescuentoPorcentualHappy() {
        Factura factura = new Factura(new Cliente("Cliente1", "cliente1@test.com"));
        Map<String, Double> request = new HashMap<>();
        request.put("porcentaje", 0.10);

        when(facturaService.aplicarDescuentoPorcentual("123", 0.10)).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.aplicarDescuentoPorcentual("123", request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).aplicarDescuentoPorcentual("123", 0.10);
    }

    @Test
    public void testAplicarDescuentoPorcentualNotFound() {
        Map<String, Double> request = new HashMap<>();
        request.put("porcentaje", 0.10);

        when(facturaService.aplicarDescuentoPorcentual("999", 0.10)).thenThrow(new RuntimeException("No encontrado"));

        ResponseEntity<Factura> response = facturaController.aplicarDescuentoPorcentual("999", request);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAplicarDescuentoFijoHappy() {
        Factura factura = new Factura(new Cliente("Cliente1", "cliente1@test.com"));
        Map<String, Double> request = new HashMap<>();
        request.put("monto", 50.0);

        when(facturaService.aplicarDescuentoFijo("123", 50.0)).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.aplicarDescuentoFijo("123", request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).aplicarDescuentoFijo("123", 50.0);
    }

    @Test
    public void testAplicarDescuentoFijoNotFound() {
        Map<String, Double> request = new HashMap<>();
        request.put("monto", 50.0);

        when(facturaService.aplicarDescuentoFijo("999", 50.0)).thenThrow(new RuntimeException("No encontrado"));

        ResponseEntity<Factura> response = facturaController.aplicarDescuentoFijo("999", request);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAgregarCostoEnvioHappy() {
        Factura factura = new Factura(new Cliente("Cliente1", "cliente1@test.com"));
        when(facturaService.agregarCostoEnvio("123")).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.agregarCostoEnvio("123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).agregarCostoEnvio("123");
    }

    @Test
    public void testAgregarCostoEnvioNotFound() {
        when(facturaService.agregarCostoEnvio("999")).thenThrow(new RuntimeException("No encontrado"));

        ResponseEntity<Factura> response = facturaController.agregarCostoEnvio("999");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAgregarCostoEnvioPersonalizadoHappy() {
        Factura factura = new Factura(new Cliente("Cliente1", "cliente1@test.com"));
        Map<String, Object> request = new HashMap<>();
        request.put("costoEnvio", 25.0);
        request.put("tipoEnvio", "Express");

        when(facturaService.agregarCostoEnvio("123", 25.0, "Express")).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.agregarCostoEnvioPersonalizado("123", request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).agregarCostoEnvio("123", 25.0, "Express");
    }

    @Test
    public void testAgregarCostoEnvioPersonalizadoNotFound() {
        Map<String, Object> request = new HashMap<>();
        request.put("costoEnvio", 25.0);
        request.put("tipoEnvio", "Express");

        when(facturaService.agregarCostoEnvio("999", 25.0, "Express")).thenThrow(new RuntimeException("No encontrado"));

        ResponseEntity<Factura> response = facturaController.agregarCostoEnvioPersonalizado("999", request);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAplicarConfiguracionCompletaHappy() {
        Factura factura = new Factura(new Cliente("Cliente1", "cliente1@test.com"));
        when(facturaService.aplicarConfiguracionCompleta("123")).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.aplicarConfiguracionCompleta("123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).aplicarConfiguracionCompleta("123");
    }

    @Test
    public void testAplicarConfiguracionCompletaNotFound() {
        when(facturaService.aplicarConfiguracionCompleta("999")).thenThrow(new RuntimeException("No encontrado"));

        ResponseEntity<Factura> response = facturaController.aplicarConfiguracionCompleta("999");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testObtenerTotalFacturaHappy() {
        when(facturaService.calcularTotalFactura("123")).thenReturn(1200.0);

        ResponseEntity<Map<String, Double>> response = facturaController.obtenerTotalFactura("123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1200.0, response.getBody().get("total"));
        verify(facturaService).calcularTotalFactura("123");
    }

    @Test
    public void testObtenerTotalFacturaNotFound() {
        when(facturaService.calcularTotalFactura("999")).thenThrow(new RuntimeException("No encontrado"));

        ResponseEntity<Map<String, Double>> response = facturaController.obtenerTotalFactura("999");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testObtenerSubtotalFacturaHappy() {
        when(facturaService.obtenerSubtotalFactura("123")).thenReturn(1000.0);

        ResponseEntity<Map<String, Double>> response = facturaController.obtenerSubtotalFactura("123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1000.0, response.getBody().get("subtotal"));
        verify(facturaService).obtenerSubtotalFactura("123");
    }

    @Test
    public void testObtenerSubtotalFacturaNotFound() {
        when(facturaService.obtenerSubtotalFactura("999")).thenThrow(new RuntimeException("No encontrado"));

        ResponseEntity<Map<String, Double>> response = facturaController.obtenerSubtotalFactura("999");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testCrearFacturaRapidaHappy() {
        Cliente cliente = new Cliente("Maria Garcia", "maria@test.com");
        Mueble mueble = new Mueble("Sofá", "Cuero", "Clásico", "200x90x100 cm");
        Factura factura = new Factura(cliente);

        Map<String, Object> request = new HashMap<>();
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put("nombre", "Maria Garcia");
        clienteMap.put("email", "maria@test.com");

        request.put("cliente", clienteMap);
        request.put("muebleTipo", "Sofá");
        request.put("cantidad", 1);
        request.put("precioUnitario", 1500.0);

        when(muebleService.obtenerMueblePorTipo("Sofá")).thenReturn(Optional.of(mueble));
        when(facturaService.crearFacturaRapida(any(Cliente.class), eq(mueble), eq(1), eq(1500.0))).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.crearFacturaRapida(request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(muebleService).obtenerMueblePorTipo("Sofá");
        verify(facturaService).crearFacturaRapida(any(Cliente.class), eq(mueble), eq(1), eq(1500.0));
    }

    @Test
    public void testCrearFacturaRapidaErrorMuebleNoEncontrado() {
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put("nombre", "Maria Garcia");
        clienteMap.put("email", "maria@test.com");

        request.put("cliente", clienteMap);
        request.put("muebleTipo", "NoExiste");
        request.put("cantidad", 1);
        request.put("precioUnitario", 1500.0);

        when(muebleService.obtenerMueblePorTipo("NoExiste")).thenReturn(Optional.empty());

        ResponseEntity<Factura> response = facturaController.crearFacturaRapida(request);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testCrearFacturaRapidaErrorGeneral() {
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put("nombre", "Maria Garcia");
        clienteMap.put("email", "maria@test.com");

        request.put("cliente", clienteMap);
        request.put("muebleTipo", "Sofá");
        request.put("cantidad", 1);
        request.put("precioUnitario", 1500.0);

        when(muebleService.obtenerMueblePorTipo("Sofá")).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Factura> response = facturaController.crearFacturaRapida(request);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testObtenerEstadisticasHappy() {
        when(facturaService.obtenerEstadisticas()).thenReturn("Estadísticas: 10 facturas, total $5000");

        ResponseEntity<Map<String, String>> response = facturaController.obtenerEstadisticas();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("estadisticas"));
        verify(facturaService).obtenerEstadisticas();
    }

    @Test
    public void testObtenerEstadisticasError() {
        when(facturaService.obtenerEstadisticas()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Map<String, String>> response = facturaController.obtenerEstadisticas();

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}