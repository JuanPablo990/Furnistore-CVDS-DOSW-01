package edu.dosw.lab.Furnistore_CVDS_DOSW_01.controller;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.*;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.ClienteService;
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
public class ActividadesControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private MuebleService muebleService;

    @Mock
    private FacturaService facturaService;

    @InjectMocks
    private ActividadesController actividadesController;

    @Test
    public void testRegistrarClienteHappy() {
        Cliente cliente = new Cliente("Juan Perez", "juan@test.com");
        when(clienteService.registrarCliente(cliente)).thenReturn(cliente);

        ResponseEntity<Cliente> response = actividadesController.registrarCliente(cliente);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Juan Perez", response.getBody().getNombre());
        verify(clienteService).registrarCliente(cliente);
    }

    @Test
    public void testRegistrarClienteError() {
        Cliente cliente = new Cliente("Juan Perez", "juan@test.com");
        when(clienteService.registrarCliente(cliente)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Cliente> response = actividadesController.registrarCliente(cliente);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testConsultarCatalogoHappy() {
        List<Mueble> muebles = Arrays.asList(
                new Mueble("Sofá", "Cuero", "Clásico", "200x90x100 cm"),
                new Mueble("Cama", "Madera", "Moderno", "200x160 cm")
        );
        when(muebleService.obtenerTodosLosMuebles()).thenReturn(muebles);

        ResponseEntity<List<Mueble>> response = actividadesController.consultarCatalogo(null, null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(muebleService).obtenerTodosLosMuebles();
    }

    @Test
    public void testConsultarCatalogoConFiltrosHappy() {
        List<Mueble> muebles = Collections.singletonList(
                new Mueble("Sofá", "Cuero", "Clásico", "200x90x100 cm")
        );
        when(muebleService.filtrarMuebles("Sofá", "Cuero", "Clásico")).thenReturn(muebles);

        ResponseEntity<List<Mueble>> response = actividadesController.consultarCatalogo("Sofá", "Cuero", "Clásico");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(muebleService).filtrarMuebles("Sofá", "Cuero", "Clásico");
    }

    @Test
    public void testConsultarCatalogoError() {
        when(muebleService.obtenerTodosLosMuebles()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<List<Mueble>> response = actividadesController.consultarCatalogo(null, null, null);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testCrearOrdenHappy() {
        // Configurar mocks
        Cliente cliente = new Cliente("Maria Garcia", "maria@test.com");
        Mueble mueble = new Mueble("Sofá", "Cuero", "Clásico", "200x90x100 cm");
        Factura factura = new Factura(cliente);
        factura.setId("123");

        when(clienteService.registrarCliente(any(Cliente.class))).thenReturn(cliente);
        when(muebleService.obtenerMueblePorTipo("Sofá")).thenReturn(Optional.of(mueble));
        when(facturaService.crearFactura(cliente)).thenReturn(factura);
        when(facturaService.aplicarConfiguracionCompleta("123")).thenReturn(factura);
        when(facturaService.calcularTotalFactura("123")).thenReturn(1500.0);

        // Preparar request
        Map<String, Object> ordenRequest = new HashMap<>();
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put("nombre", "Maria Garcia");
        clienteMap.put("email", "maria@test.com");

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("muebleTipo", "Sofá");
        item.put("cantidad", 1);
        item.put("precioUnitario", 1000.0);
        items.add(item);

        ordenRequest.put("cliente", clienteMap);
        ordenRequest.put("items", items);

        ResponseEntity<Map<String, Object>> response = actividadesController.crearOrden(ordenRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("123", response.getBody().get("ordenId"));
        assertEquals("Orden creada exitosamente", response.getBody().get("mensaje"));
        verify(facturaService).agregarMuebleAFactura("123", mueble, 1, 1000.0);
    }

    @Test
    public void testCrearOrdenErrorMuebleNoEncontrado() {
        Cliente cliente = new Cliente("Maria Garcia", "maria@test.com");
        when(clienteService.registrarCliente(any(Cliente.class))).thenReturn(cliente);
        when(muebleService.obtenerMueblePorTipo("Sofá")).thenReturn(Optional.empty());

        Map<String, Object> ordenRequest = new HashMap<>();
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put("nombre", "Maria Garcia");
        clienteMap.put("email", "maria@test.com");

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("muebleTipo", "Sofá");
        item.put("cantidad", 1);
        item.put("precioUnitario", 1000.0);
        items.add(item);

        ordenRequest.put("cliente", clienteMap);
        ordenRequest.put("items", items);

        ResponseEntity<Map<String, Object>> response = actividadesController.crearOrden(ordenRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test
    public void testCrearOrdenErrorGeneral() {
        when(clienteService.registrarCliente(any(Cliente.class))).thenThrow(new RuntimeException("Error general"));

        Map<String, Object> ordenRequest = new HashMap<>();
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put("nombre", "Maria Garcia");
        clienteMap.put("email", "maria@test.com");
        ordenRequest.put("cliente", clienteMap);
        ordenRequest.put("items", new ArrayList<>());

        ResponseEntity<Map<String, Object>> response = actividadesController.crearOrden(ordenRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test
    public void testConsultarOrdenHappy() {
        Cliente cliente = new Cliente("Carlos Lopez", "carlos@test.com");
        Factura factura = new Factura(cliente);
        factura.setId("123");
        factura.setSubtotal(1000.0);

        List<ItemFactura> items = new ArrayList<>();
        factura.setItems(items);

        when(facturaService.buscarFacturaPorId("123")).thenReturn(Optional.of(factura));
        when(facturaService.calcularTotalFactura("123")).thenReturn(1200.0);

        ResponseEntity<Map<String, Object>> response = actividadesController.consultarOrden("123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("123", response.getBody().get("ordenId"));
        assertEquals(1000.0, response.getBody().get("subtotal"));
        assertEquals(1200.0, response.getBody().get("total"));
        verify(facturaService).buscarFacturaPorId("123");
    }

    @Test
    public void testConsultarOrdenNotFound() {
        when(facturaService.buscarFacturaPorId("999")).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = actividadesController.consultarOrden("999");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testConsultarOrdenError() {
        when(facturaService.buscarFacturaPorId("123")).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Map<String, Object>> response = actividadesController.consultarOrden("123");

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test
    public void testCrearOrdenConMultiplesItemsHappy() {
        Cliente cliente = new Cliente("Ana Torres", "ana@test.com");
        Mueble sofa = new Mueble("Sofá", "Cuero", "Clásico", "200x90x100 cm");
        Mueble cama = new Mueble("Cama", "Madera", "Moderno", "200x160 cm");
        Factura factura = new Factura(cliente);
        factura.setId("456");

        when(clienteService.registrarCliente(any(Cliente.class))).thenReturn(cliente);
        when(muebleService.obtenerMueblePorTipo("Sofá")).thenReturn(Optional.of(sofa));
        when(muebleService.obtenerMueblePorTipo("Cama")).thenReturn(Optional.of(cama));
        when(facturaService.crearFactura(cliente)).thenReturn(factura);
        when(facturaService.aplicarConfiguracionCompleta("456")).thenReturn(factura);
        when(facturaService.calcularTotalFactura("456")).thenReturn(2500.0);

        Map<String, Object> ordenRequest = new HashMap<>();
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put("nombre", "Ana Torres");
        clienteMap.put("email", "ana@test.com");

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("muebleTipo", "Sofá");
        item1.put("cantidad", 1);
        item1.put("precioUnitario", 1500.0);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("muebleTipo", "Cama");
        item2.put("cantidad", 1);
        item2.put("precioUnitario", 1000.0);

        items.add(item1);
        items.add(item2);

        ordenRequest.put("cliente", clienteMap);
        ordenRequest.put("items", items);

        ResponseEntity<Map<String, Object>> response = actividadesController.crearOrden(ordenRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(facturaService).agregarMuebleAFactura("456", sofa, 1, 1500.0);
        verify(facturaService).agregarMuebleAFactura("456", cama, 1, 1000.0);
    }

    @Test
    public void testConsultarCatalogoConFiltrosParcialesHappy() {
        List<Mueble> muebles = Arrays.asList(
                new Mueble("Sofá", "Cuero", "Clásico", "200x90x100 cm"),
                new Mueble("Sofá", "Tela", "Moderno", "180x85x95 cm")
        );
        when(muebleService.filtrarMuebles("Sofá", null, null)).thenReturn(muebles);

        ResponseEntity<List<Mueble>> response = actividadesController.consultarCatalogo("Sofá", null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(muebleService).filtrarMuebles("Sofá", null, null);
    }
}