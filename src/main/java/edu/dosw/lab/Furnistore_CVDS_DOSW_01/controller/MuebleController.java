package edu.dosw.lab.Furnistore_CVDS_DOSW_01.controller;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.Mueble;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.MuebleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar operaciones de muebles.
 * Expone endpoints API para interactuar con el catálogo de muebles.
 */
@RestController
@RequestMapping("/api/muebles")
public class MuebleController {

    private final MuebleService muebleService;

    @Autowired
    public MuebleController(MuebleService muebleService) {
        this.muebleService = muebleService;
    }

    /**
     * Obtiene todos los muebles del catálogo.
     * @return Lista de todos los muebles.
     */
    @GetMapping
    public ResponseEntity<List<Mueble>> obtenerTodosLosMuebles() {
        List<Mueble> muebles = muebleService.obtenerTodosLosMuebles();
        return ResponseEntity.ok(muebles);
    }

    /**
     * Obtiene un mueble por su tipo.
     * @param tipo Tipo de mueble a buscar.
     * @return Mueble encontrado o 404 si no existe.
     */
    @GetMapping("/{tipo}")
    public ResponseEntity<Mueble> obtenerMueblePorTipo(@PathVariable String tipo) {
        Optional<Mueble> mueble = muebleService.obtenerMueblePorTipo(tipo);
        return mueble.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo sofá clásico.
     * @return Sofá clásico creado.
     */
    @PostMapping("/sofa-clasico")
    public ResponseEntity<Mueble> crearSofaClasico() {
        Mueble sofa = muebleService.crearSofaClasico();
        return ResponseEntity.ok(sofa);
    }

    /**
     * Crea una nueva cama moderna.
     * @return Cama moderna creada.
     */
    @PostMapping("/cama-moderna")
    public ResponseEntity<Mueble> crearCamaModerna() {
        Mueble cama = muebleService.crearCamaModerna();
        return ResponseEntity.ok(cama);
    }

    /**
     * Crea un mueble personalizado.
     * @param tipo Tipo de mueble.
     * @param material Material del mueble.
     * @param estilo Estilo del mueble.
     * @param dimensiones Dimensiones del mueble.
     * @return Mueble personalizado creado.
     */
    @PostMapping("/personalizado")
    public ResponseEntity<Mueble> crearMueblePersonalizado(
            @RequestParam String tipo,
            @RequestParam String material,
            @RequestParam String estilo,
            @RequestParam String dimensiones) {

        Mueble mueble = muebleService.crearMueblePersonalizado(tipo, material, estilo, dimensiones);
        return ResponseEntity.ok(mueble);
    }

    /**
     * Obtiene el número total de muebles en el catálogo.
     * @return Cantidad de muebles.
     */
    @GetMapping("/total")
    public ResponseEntity<Integer> obtenerTotalMuebles() {
        int total = muebleService.obtenerTotalMuebles();
        return ResponseEntity.ok(total);
    }
}