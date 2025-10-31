package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

/**
 * Decorador que aplica descuentos a una factura.
 * Soporta tanto descuentos porcentuales como montos fijos.
 */
public class DescuentoDecorator extends FacturaDecorator {
    private double porcentajeDescuento;
    private double montoFijoDescuento;
    private boolean esPorcentual;

    /**
     * Constructor para descuento porcentual.
     * @param factura Factura a la que se aplicará el descuento
     * @param porcentajeDescuento Porcentaje de descuento (ej: 0.10 para 10%)
     */
    public DescuentoDecorator(Factura factura, double porcentajeDescuento) {
        super(factura);
        this.porcentajeDescuento = porcentajeDescuento;
        this.esPorcentual = true;
    }

    /**
     * Constructor para descuento de monto fijo.
     * @param factura Factura a la que se aplicará el descuento
     * @param montoFijoDescuento Monto fijo de descuento
     */
    public DescuentoDecorator(Factura factura, int montoFijoDescuento) {
        super(factura);
        this.montoFijoDescuento = montoFijoDescuento;
        this.esPorcentual = false;
    }

    /**
     * Calcula el total aplicando el descuento correspondiente.
     * @return Total con descuento aplicado
     */
    @Override
    public double calcularTotal() {
        double subtotal = facturaDecorada.getSubtotal();
        double descuento;

        if (esPorcentual) {
            descuento = subtotal * porcentajeDescuento;
        } else {
            descuento = montoFijoDescuento;
        }

        // Asegurar que el descuento no sea mayor al subtotal
        return Math.max(0, subtotal - descuento);
    }

    /**
     * Obtiene el monto del descuento aplicado.
     * @return Monto del descuento
     */
    public double getMontoDescuento() {
        double subtotal = facturaDecorada.getSubtotal();
        if (esPorcentual) {
            return subtotal * porcentajeDescuento;
        } else {
            return montoFijoDescuento;
        }
    }

    // Getters y setters
    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
        this.esPorcentual = true;
    }

    public double getMontoFijoDescuento() {
        return montoFijoDescuento;
    }

    public void setMontoFijoDescuento(double montoFijoDescuento) {
        this.montoFijoDescuento = montoFijoDescuento;
        this.esPorcentual = false;
    }

    public boolean isEsPorcentual() {
        return esPorcentual;
    }
}