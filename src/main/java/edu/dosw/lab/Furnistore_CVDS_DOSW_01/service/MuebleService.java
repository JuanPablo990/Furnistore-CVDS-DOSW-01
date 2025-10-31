package edu.dosw.lab.Furnistore_CVDS_DOSW_01.service;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.Mueble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar operaciones relacionadas con muebles.
 * Proporciona métodos de negocio para la gestión de muebles.
 */
@Service
public class MuebleService {
    private final MuebleDirector muebleDirector;
    private final List<Mueble> catalogoMuebles = new ArrayList<>();

    @Autowired
    public MuebleService(MuebleDirector muebleDirector) {
        this.muebleDirector = muebleDirector;
        inicializarCatalogo();
    }

    /**
     * Inicializa el catálogo con algunos muebles predefinidos.
     */
    private void inicializarCatalogo() {
        catalogoMuebles.add(muebleDirector.construirSofaClasico());
        catalogoMuebles.add(muebleDirector.construirCamaModerna());
    }

    /**
     * Obtiene todos los muebles del catálogo.
     * @return Lista de todos los muebles.
     */
    public List<Mueble> obtenerTodosLosMuebles() {
        return new ArrayList<>(catalogoMuebles);
    }

    /**
     * Obtiene un mueble por su tipo.
     * @param tipo Tipo de mueble a buscar.
     * @return Optional con el mueble encontrado.
     */
    public Optional<Mueble> obtenerMueblePorTipo(String tipo) {
        return catalogoMuebles.stream()
                .filter(mueble -> mueble.getTipo().equalsIgnoreCase(tipo))
                .findFirst();
    }

    /**
     * Crea y agrega un nuevo sofá clásico al catálogo.
     * @return Sofá clásico creado.
     */
    public Mueble crearSofaClasico() {
        Mueble sofa = muebleDirector.construirSofaClasico();
        catalogoMuebles.add(sofa);
        return sofa;
    }

    /**
     * Crea y agrega una nueva cama moderna al catálogo.
     * @return Cama moderna creada.
     */
    public Mueble crearCamaModerna() {
        Mueble cama = muebleDirector.construirCamaModerna();
        catalogoMuebles.add(cama);
        return cama;
    }

    /**
     * Crea y agrega un mueble personalizado al catálogo.
     * @return Mueble personalizado creado.
     */
    public Mueble crearMueblePersonalizado(String tipo, String material, String estilo, String dimensiones) {
        Mueble mueble = muebleDirector.construirMueblePersonalizado(tipo, material, estilo, dimensiones);
        catalogoMuebles.add(mueble);
        return mueble;
    }

    /**
     * Obtiene el número total de muebles en el catálogo.
     * @return Cantidad de muebles.
     */
    public int obtenerTotalMuebles() {
        return catalogoMuebles.size();
    }

    /**
     * Filtra muebles por tipo, material y estilo
     */
    public List<Mueble> filtrarMuebles(String tipo, String material, String estilo) {
        return catalogoMuebles.stream()
                .filter(mueble -> tipo == null || mueble.getTipo().equalsIgnoreCase(tipo))
                .filter(mueble -> material == null || mueble.getMaterial().equalsIgnoreCase(material))
                .filter(mueble -> estilo == null || mueble.getEstilo().equalsIgnoreCase(estilo))
                .collect(Collectors.toList());
    }
}