package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

/**
 * Decorador que aplica el Impuesto al Valor Agregado (IVA) a una factura.
 */
public class IvaDecorator extends FacturaDecorator {
    private double porcentajeIva;

    /**
     * Constructor que recibe la factura a decorar y el porcentaje de IVA.
     * @param factura Factura a la que se aplicará el IVA
     * @param porcentajeIva Porcentaje de IVA a aplicar (ej: 0.19 para 19%)
     */
    public IvaDecorator(Factura factura, double porcentajeIva) {
        super(factura);
        this.porcentajeIva = porcentajeIva;
    }

    /**
     * Calcula el total aplicando el IVA al subtotal de la factura decorada.
     * @return Total con IVA aplicado
     */
    @Override
    public double calcularTotal() {
        double subtotal = facturaDecorada.getSubtotal();
        double iva = subtotal * porcentajeIva;
        return subtotal + iva;
    }

    /**
     * Obtiene el monto del IVA aplicado.
     * @return Monto del IVA
     */
    public double getMontoIva() {
        return facturaDecorada.getSubtotal() * porcentajeIva;
    }

    public double getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(double porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }
}