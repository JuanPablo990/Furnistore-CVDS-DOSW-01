package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Representa una factura básica con cliente, ítems y cálculos de total.
 */
@Data
@NoArgsConstructor
public class Factura {
    private String id;
    private Cliente cliente;
    private Date fecha;
    private List<ItemFactura> items;
    private double subtotal;
    private double total;

    public Factura(Cliente cliente) {
        this.cliente = cliente;
        this.fecha = new Date();
        this.items = new ArrayList<>();
        this.subtotal = 0.0;
        this.total = 0.0;
    }

    /**
     * Agrega un ítem a la factura y actualiza los totales.
     */
    public void agregarItem(ItemFactura item) {
        this.items.add(item);
        recalcularTotales();
    }

    /**
     * Agrega un mueble como ítem con cantidad específica.
     */
    public void agregarMueble(Mueble mueble, int cantidad, double precioUnitario) {
        ItemFactura item = new ItemFactura(mueble, cantidad, precioUnitario);
        agregarItem(item);
    }

    /**
     * Recalcula subtotal y total basado en los ítems.
     */
    private void recalcularTotales() {
        this.subtotal = items.stream()
                .mapToDouble(ItemFactura::calcularTotalItem)
                .sum();
        this.total = this.subtotal; // Base para decoradores
    }

    /**
     * Obtiene el número total de ítems en la factura.
     */
    public int getTotalItems() {
        return items.stream()
                .mapToInt(ItemFactura::getCantidad)
                .sum();
    }

    /**
     * Método que será decorado para cálculos especiales.
     */
    public double calcularTotal() {
        return this.total;
    }
}