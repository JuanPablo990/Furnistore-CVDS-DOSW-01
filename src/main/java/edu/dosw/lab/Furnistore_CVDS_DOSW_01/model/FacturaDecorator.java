package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import lombok.AllArgsConstructor;

/**
 * Clase abstracta base para todos los decoradores de factura.
 * Implementa el patrón Decorator para añadir funcionalidades a las facturas.
 */

public abstract class FacturaDecorator extends Factura {
    protected Factura facturaDecorada;

    /**
     * Constructor que recibe la factura a decorar.
     */
    public FacturaDecorator(Factura factura) {
        this.facturaDecorada = factura;
        // Copiamos los datos básicos de la factura decorada
        this.setId(factura.getId());
        this.setCliente(factura.getCliente());
        this.setFecha(factura.getFecha());
        this.setItems(factura.getItems());
        this.setSubtotal(factura.getSubtotal());
    }

    /**
     * Método abstracto que deben implementar los decoradores concretos
     * para modificar el cálculo del total.
     */
    @Override
    public abstract double calcularTotal();

    /**
     * Delega los métodos de la factura base a la factura decorada.
     */
    @Override
    public void agregarItem(ItemFactura item) {
        facturaDecorada.agregarItem(item);
        this.setSubtotal(facturaDecorada.getSubtotal());
    }

    @Override
    public void agregarMueble(Mueble mueble, int cantidad, double precioUnitario) {
        facturaDecorada.agregarMueble(mueble, cantidad, precioUnitario);
        this.setSubtotal(facturaDecorada.getSubtotal());
    }

    @Override
    public int getTotalItems() {
        return facturaDecorada.getTotalItems();
    }
}