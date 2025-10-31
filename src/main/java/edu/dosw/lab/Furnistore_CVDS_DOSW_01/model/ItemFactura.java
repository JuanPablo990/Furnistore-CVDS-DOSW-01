package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa un ítem en la factura con producto, cantidad y precio.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFactura {
    private Mueble producto;
    private int cantidad;
    private double precioUnitario;

    /**
     * Calcula el total para este ítem (cantidad × precio unitario).
     */
    public double calcularTotalItem() {
        return cantidad * precioUnitario;
    }

    /**
     * Constructor simplificado que asume el precio del mueble.
     */
    public ItemFactura(Mueble producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = 0.0; // Placeholder - necesitarás agregar precio a Mueble
    }
}