package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

/**
 * Decorador que añade costos de envío a una factura.
 */
public class EnvioDecorator extends FacturaDecorator {
    private double costoEnvio;
    private String tipoEnvio;

    /**
     * Constructor que recibe la factura y el costo de envío.
     * @param factura Factura a la que se añadirá el costo de envío
     * @param costoEnvio Costo del envío
     */
    public EnvioDecorator(Factura factura, double costoEnvio) {
        super(factura);
        this.costoEnvio = costoEnvio;
        this.tipoEnvio = "Estándar";
    }

    /**
     * Constructor con tipo de envío específico.
     * @param factura Factura a la que se añadirá el costo de envío
     * @param costoEnvio Costo del envío
     * @param tipoEnvio Tipo de envío (ej: "Express", "Económico")
     */
    public EnvioDecorator(Factura factura, double costoEnvio, String tipoEnvio) {
        super(factura);
        this.costoEnvio = costoEnvio;
        this.tipoEnvio = tipoEnvio;
    }

    /**
     * Calcula el total añadiendo el costo de envío al subtotal.
     * @return Total con costo de envío incluido
     */
    @Override
    public double calcularTotal() {
        return facturaDecorada.getSubtotal() + costoEnvio;
    }


    public double getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(double costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }
}