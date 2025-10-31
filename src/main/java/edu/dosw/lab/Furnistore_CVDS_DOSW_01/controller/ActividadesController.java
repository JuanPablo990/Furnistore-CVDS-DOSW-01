package edu.dosw.lab.Furnistore_CVDS_DOSW_01.controller;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.*;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.ClienteService;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.FacturaService;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.MuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador que implementa los endpoints específicos de las actividades
 * Integra clientes, muebles y facturación en un solo controlador
 */
@RestController
public class ActividadesController {

    private final ClienteService clienteService;
    private final MuebleService muebleService;
    private final FacturaService facturaService;

    @Autowired
    public ActividadesController(ClienteService clienteService,
                                 MuebleService muebleService,
                                 FacturaService facturaService) {
        this.clienteService = clienteService;
        this.muebleService = muebleService;
        this.facturaService = facturaService;
    }

    /**
     * POST /clientes → registro de clientes
     * Registra un nuevo cliente en el sistema
     */
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.registrarCliente(cliente);
            return ResponseEntity.ok(nuevoCliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /muebles → consulta de catálogo con filtros
     * Consulta el catálogo de muebles con filtros opcionales
     */
    @GetMapping("/muebles")
    public ResponseEntity<List<Mueble>> consultarCatalogo(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String estilo) {

        try {
            List<Mueble> muebles;

            // Aplicar filtros si están presentes
            if (tipo != null || material != null || estilo != null) {
                muebles = muebleService.filtrarMuebles(tipo, material, estilo);
            } else {
                muebles = muebleService.obtenerTodosLosMuebles();
            }

            return ResponseEntity.ok(muebles);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * POST /ordenes → creación de órdenes y facturas
     * Crea una orden completa que incluye facturación automática
     */
    @PostMapping("/ordenes")
    public ResponseEntity<Map<String, Object>> crearOrden(@RequestBody Map<String, Object> ordenRequest) {
        try {
            // Extraer datos del cliente
            Map<String, Object> clienteMap = (Map<String, Object>) ordenRequest.get("cliente");
            Cliente cliente = new Cliente(
                    (String) clienteMap.get("nombre"),
                    (String) clienteMap.get("email")
            );

            // Registrar el cliente
            Cliente clienteRegistrado = clienteService.registrarCliente(cliente);

            // Extraer items de la orden
            List<Map<String, Object>> items = (List<Map<String, Object>>) ordenRequest.get("items");

            // Crear factura para el cliente
            Factura factura = facturaService.crearFactura(clienteRegistrado);

            // Agregar cada item a la factura
            for (Map<String, Object> item : items) {
                String muebleTipo = (String) item.get("muebleTipo");
                int cantidad = (Integer) item.get("cantidad");
                double precioUnitario = (Double) item.get("precioUnitario");

                // Buscar el mueble por tipo
                Optional<Mueble> muebleOpt = muebleService.obtenerMueblePorTipo(muebleTipo);
                if (muebleOpt.isPresent()) {
                    facturaService.agregarMuebleAFactura(factura.getId(), muebleOpt.get(), cantidad, precioUnitario);
                } else {
                    throw new RuntimeException("Mueble no encontrado: " + muebleTipo);
                }
            }

            // Aplicar configuración completa (IVA + envío)
            Factura facturaFinal = facturaService.aplicarConfiguracionCompleta(factura.getId());

            // Calcular total final
            double total = facturaService.calcularTotalFactura(factura.getId());

            // Preparar respuesta
            Map<String, Object> respuesta = Map.of(
                    "ordenId", factura.getId(),
                    "cliente", clienteRegistrado,
                    "factura", facturaFinal,
                    "total", total,
                    "mensaje", "Orden creada exitosamente"
            );

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /ordenes/{id} → consulta de facturas
     * Consulta una orden/factura específica por su ID
     */
    @GetMapping("/ordenes/{id}")
    public ResponseEntity<Map<String, Object>> consultarOrden(@PathVariable String id) {
        try {
            Optional<Factura> facturaOpt = facturaService.buscarFacturaPorId(id);

            if (facturaOpt.isPresent()) {
                Factura factura = facturaOpt.get();
                double total = facturaService.calcularTotalFactura(id);

                Map<String, Object> respuesta = Map.of(
                        "ordenId", factura.getId(),
                        "cliente", factura.getCliente(),
                        "fecha", factura.getFecha(),
                        "items", factura.getItems(),
                        "subtotal", factura.getSubtotal(),
                        "total", total,
                        "totalItems", factura.getTotalItems()
                );

                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}