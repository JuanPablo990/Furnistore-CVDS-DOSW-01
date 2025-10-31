package edu.dosw.lab.Furnistore_CVDS_DOSW_01.service;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Servicio para gestionar operaciones de facturación.
 * Integra el patrón Decorator para aplicar IVA, descuentos y costos de envío.
 */
@Service
public class FacturaService {
    private final List<Factura> facturas = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);
    private final MuebleService muebleService;

    // Configuración por defecto (podría ir en application.properties)
    private static final double IVA_POR_DEFECTO = 0.19; // 19%
    private static final double COSTO_ENVIO_POR_DEFECTO = 25.0;

    @Autowired
    public FacturaService(MuebleService muebleService) {
        this.muebleService = muebleService;
    }

    /**
     * Crea una nueva factura básica para un cliente.
     */
    public Factura crearFactura(Cliente cliente) {
        Factura factura = new Factura(cliente);
        factura.setId("FAC-" + contadorId.getAndIncrement());
        facturas.add(factura);
        return factura;
    }

    /**
     * Agrega un mueble a una factura existente.
     */
    public Factura agregarMuebleAFactura(String facturaId, Mueble mueble, int cantidad, double precioUnitario) {
        Optional<Factura> facturaOpt = buscarFacturaPorId(facturaId);
        if (facturaOpt.isPresent()) {
            Factura factura = facturaOpt.get();
            factura.agregarMueble(mueble, cantidad, precioUnitario);
            return factura;
        }
        throw new RuntimeException("Factura no encontrada: " + facturaId);
    }

    /**
     * Aplica IVA a una factura usando el patrón Decorator.
     */
    public Factura aplicarIva(String facturaId, double porcentajeIva) {
        Optional<Factura> facturaOpt = buscarFacturaPorId(facturaId);
        if (facturaOpt.isPresent()) {
            Factura facturaOriginal = facturaOpt.get();
            Factura facturaConIva = new IvaDecorator(facturaOriginal, porcentajeIva);

            // Reemplazar la factura original con la decorada
            reemplazarFactura(facturaId, facturaConIva);
            return facturaConIva;
        }
        throw new RuntimeException("Factura no encontrada: " + facturaId);
    }

    /**
     * Aplica IVA con el porcentaje por defecto.
     */
    public Factura aplicarIva(String facturaId) {
        return aplicarIva(facturaId, IVA_POR_DEFECTO);
    }

    /**
     * Aplica un descuento porcentual a una factura.
     */
    public Factura aplicarDescuentoPorcentual(String facturaId, double porcentajeDescuento) {
        Optional<Factura> facturaOpt = buscarFacturaPorId(facturaId);
        if (facturaOpt.isPresent()) {
            Factura facturaOriginal = facturaOpt.get();
            Factura facturaConDescuento = new DescuentoDecorator(facturaOriginal, porcentajeDescuento);

            reemplazarFactura(facturaId, facturaConDescuento);
            return facturaConDescuento;
        }
        throw new RuntimeException("Factura no encontrada: " + facturaId);
    }

    /**
     * Aplica un descuento de monto fijo a una factura.
     */
    public Factura aplicarDescuentoFijo(String facturaId, double montoDescuento) {
        Optional<Factura> facturaOpt = buscarFacturaPorId(facturaId);
        if (facturaOpt.isPresent()) {
            Factura facturaOriginal = facturaOpt.get();
            Factura facturaConDescuento = new DescuentoDecorator(facturaOriginal, (int) montoDescuento);

            reemplazarFactura(facturaId, facturaConDescuento);
            return facturaConDescuento;
        }
        throw new RuntimeException("Factura no encontrada: " + facturaId);
    }

    /**
     * Agrega costo de envío a una factura.
     */
    public Factura agregarCostoEnvio(String facturaId, double costoEnvio, String tipoEnvio) {
        Optional<Factura> facturaOpt = buscarFacturaPorId(facturaId);
        if (facturaOpt.isPresent()) {
            Factura facturaOriginal = facturaOpt.get();
            Factura facturaConEnvio = new EnvioDecorator(facturaOriginal, costoEnvio, tipoEnvio);

            reemplazarFactura(facturaId, facturaConEnvio);
            return facturaConEnvio;
        }
        throw new RuntimeException("Factura no encontrada: " + facturaId);
    }

    /**
     * Agrega costo de envío con el valor por defecto.
     */
    public Factura agregarCostoEnvio(String facturaId) {
        return agregarCostoEnvio(facturaId, COSTO_ENVIO_POR_DEFECTO, "Estándar");
    }

    /**
     * Aplica todos los impuestos y cargos por defecto (IVA + envío).
     */
    public Factura aplicarConfiguracionCompleta(String facturaId) {
        Factura factura = aplicarIva(facturaId);
        factura = agregarCostoEnvio(facturaId);
        return factura;
    }

    /**
     * Calcula el total final de una factura (considerando todos los decoradores aplicados).
     */
    public double calcularTotalFactura(String facturaId) {
        Optional<Factura> facturaOpt = buscarFacturaPorId(facturaId);
        if (facturaOpt.isPresent()) {
            return facturaOpt.get().calcularTotal();
        }
        throw new RuntimeException("Factura no encontrada: " + facturaId);
    }

    /**
     * Obtiene todas las facturas existentes.
     */
    public List<Factura> obtenerTodasLasFacturas() {
        return new ArrayList<>(facturas);
    }

    /**
     * Busca una factura por su ID.
     */
    public Optional<Factura> buscarFacturaPorId(String id) {
        return facturas.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst();
    }

    /**
     * Obtiene el subtotal de una factura (sin impuestos ni descuentos).
     */
    public double obtenerSubtotalFactura(String facturaId) {
        Optional<Factura> facturaOpt = buscarFacturaPorId(facturaId);
        if (facturaOpt.isPresent()) {
            return facturaOpt.get().getSubtotal();
        }
        throw new RuntimeException("Factura no encontrada: " + facturaId);
    }

    /**
     * Reemplaza una factura en la lista por una nueva versión.
     */
    private void reemplazarFactura(String facturaId, Factura nuevaFactura) {
        for (int i = 0; i < facturas.size(); i++) {
            if (facturas.get(i).getId().equals(facturaId)) {
                facturas.set(i, nuevaFactura);
                return;
            }
        }
    }

    /**
     * Crea una factura rápida con un mueble específico.
     */
    public Factura crearFacturaRapida(Cliente cliente, Mueble mueble, int cantidad, double precioUnitario) {
        Factura factura = crearFactura(cliente);
        factura.agregarMueble(mueble, cantidad, precioUnitario);
        return aplicarConfiguracionCompleta(factura.getId());
    }

    /**
     * Obtiene estadísticas de facturación.
     */
    public String obtenerEstadisticas() {
        long totalFacturas = facturas.size();
        double totalVendido = facturas.stream()
                .mapToDouble(Factura::calcularTotal)
                .sum();

        return String.format("Total facturas: %d, Monto total vendido: $%.2f",
                totalFacturas, totalVendido);
    }
}