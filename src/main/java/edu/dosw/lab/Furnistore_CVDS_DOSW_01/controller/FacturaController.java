package edu.dosw.lab.Furnistore_CVDS_DOSW_01.controller;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.*;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.FacturaService;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.MuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar operaciones de facturación.
 * Expone endpoints para crear facturas, aplicar impuestos, descuentos y calcular totales.
 */
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final MuebleService muebleService;

    @Autowired
    public FacturaController(FacturaService facturaService, MuebleService muebleService) {
        this.facturaService = facturaService;
        this.muebleService = muebleService;
    }

    /**
     * Crea una nueva factura para un cliente.
     */
    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Cliente cliente) {
        try {
            Factura factura = facturaService.crearFactura(cliente);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtiene todas las facturas existentes.
     */
    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodasLasFacturas() {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
        return ResponseEntity.ok(facturas);
    }

    /**
     * Obtiene una factura específica por su ID.
     */
    @GetMapping("/{facturaId}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable String facturaId) {
        return facturaService.buscarFacturaPorId(facturaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Agrega un mueble a una factura existente.
     */
    @PostMapping("/{facturaId}/items")
    public ResponseEntity<Factura> agregarMuebleAFactura(
            @PathVariable String facturaId,
            @RequestBody Map<String, Object> request) {

        try {
            String muebleTipo = (String) request.get("muebleTipo");
            int cantidad = (Integer) request.get("cantidad");
            double precioUnitario = (Double) request.get("precioUnitario");

            // Buscar el mueble por tipo (esto es un ejemplo - podrías necesitar un ID real)
            Mueble mueble = muebleService.obtenerMueblePorTipo(muebleTipo)
                    .orElseThrow(() -> new RuntimeException("Mueble no encontrado: " + muebleTipo));

            Factura factura = facturaService.agregarMuebleAFactura(facturaId, mueble, cantidad, precioUnitario);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Aplica IVA a una factura (con porcentaje por defecto).
     */
    @PostMapping("/{facturaId}/aplicar-iva")
    public ResponseEntity<Factura> aplicarIva(@PathVariable String facturaId) {
        try {
            Factura factura = facturaService.aplicarIva(facturaId);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Aplica IVA a una factura con un porcentaje específico.
     */
    @PostMapping("/{facturaId}/aplicar-iva/{porcentaje}")
    public ResponseEntity<Factura> aplicarIvaConPorcentaje(
            @PathVariable String facturaId,
            @PathVariable double porcentaje) {
        try {
            Factura factura = facturaService.aplicarIva(facturaId, porcentaje);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Aplica un descuento porcentual a una factura.
     */
    @PostMapping("/{facturaId}/aplicar-descuento/porcentual")
    public ResponseEntity<Factura> aplicarDescuentoPorcentual(
            @PathVariable String facturaId,
            @RequestBody Map<String, Double> request) {

        try {
            double porcentajeDescuento = request.get("porcentaje");
            Factura factura = facturaService.aplicarDescuentoPorcentual(facturaId, porcentajeDescuento);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Aplica un descuento de monto fijo a una factura.
     */
    @PostMapping("/{facturaId}/aplicar-descuento/fijo")
    public ResponseEntity<Factura> aplicarDescuentoFijo(
            @PathVariable String facturaId,
            @RequestBody Map<String, Double> request) {

        try {
            double montoDescuento = request.get("monto");
            Factura factura = facturaService.aplicarDescuentoFijo(facturaId, montoDescuento);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Agrega costo de envío a una factura (con costo por defecto).
     */
    @PostMapping("/{facturaId}/agregar-envio")
    public ResponseEntity<Factura> agregarCostoEnvio(@PathVariable String facturaId) {
        try {
            Factura factura = facturaService.agregarCostoEnvio(facturaId);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Agrega costo de envío con monto y tipo específicos.
     */
    @PostMapping("/{facturaId}/agregar-envio/personalizado")
    public ResponseEntity<Factura> agregarCostoEnvioPersonalizado(
            @PathVariable String facturaId,
            @RequestBody Map<String, Object> request) {

        try {
            double costoEnvio = (Double) request.get("costoEnvio");
            String tipoEnvio = (String) request.get("tipoEnvio");

            Factura factura = facturaService.agregarCostoEnvio(facturaId, costoEnvio, tipoEnvio);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Aplica la configuración completa (IVA + envío) a una factura.
     */
    @PostMapping("/{facturaId}/aplicar-configuracion-completa")
    public ResponseEntity<Factura> aplicarConfiguracionCompleta(@PathVariable String facturaId) {
        try {
            Factura factura = facturaService.aplicarConfiguracionCompleta(facturaId);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene el total calculado de una factura.
     */
    @GetMapping("/{facturaId}/total")
    public ResponseEntity<Map<String, Double>> obtenerTotalFactura(@PathVariable String facturaId) {
        try {
            double total = facturaService.calcularTotalFactura(facturaId);
            return ResponseEntity.ok(Map.of("total", total));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene el subtotal de una factura (sin impuestos ni descuentos).
     */
    @GetMapping("/{facturaId}/subtotal")
    public ResponseEntity<Map<String, Double>> obtenerSubtotalFactura(@PathVariable String facturaId) {
        try {
            double subtotal = facturaService.obtenerSubtotalFactura(facturaId);
            return ResponseEntity.ok(Map.of("subtotal", subtotal));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea una factura rápida con un mueble y aplicación automática de impuestos.
     */
    @PostMapping("/factura-rapida")
    public ResponseEntity<Factura> crearFacturaRapida(@RequestBody Map<String, Object> request) {
        try {
            // Extraer datos del request
            Map<String, Object> clienteMap = (Map<String, Object>) request.get("cliente");
            Cliente cliente = new Cliente(
                    (String) clienteMap.get("nombre"),
                    (String) clienteMap.get("email")
            );

            String muebleTipo = (String) request.get("muebleTipo");
            int cantidad = (Integer) request.get("cantidad");
            double precioUnitario = (Double) request.get("precioUnitario");

            Mueble mueble = muebleService.obtenerMueblePorTipo(muebleTipo)
                    .orElseThrow(() -> new RuntimeException("Mueble no encontrado: " + muebleTipo));

            Factura factura = facturaService.crearFacturaRapida(cliente, mueble, cantidad, precioUnitario);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtiene estadísticas generales de facturación.
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, String>> obtenerEstadisticas() {
        try {
            String estadisticas = facturaService.obtenerEstadisticas();
            return ResponseEntity.ok(Map.of("estadisticas", estadisticas));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}