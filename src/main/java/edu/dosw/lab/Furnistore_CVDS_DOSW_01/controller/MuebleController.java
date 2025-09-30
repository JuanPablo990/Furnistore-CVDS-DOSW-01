package edu.dosw.lab.Furnistore_CVDS_DOSW_01.controller;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain.ConcreteMuebleBuilder;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain.Mueble;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain.MuebleBuilder;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.domain.MuebleDirector;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/muebles")
public class MuebleController {

    @PostMapping("/sofa-clasico")
    public Mueble construirSofaClasico() {
        MuebleBuilder builder = new ConcreteMuebleBuilder();
        MuebleDirector director = new MuebleDirector(builder);
        return director.construirSofaClasico();
    }

    @PostMapping("/cama-moderna")
    public Mueble construirCamaModerna() {
        MuebleBuilder builder = new ConcreteMuebleBuilder();
        MuebleDirector director = new MuebleDirector(builder);
        return director.construirCamaModerna();
    }

    @PostMapping("/personalizado")
    public Mueble construirMueblePersonalizado(
            @RequestParam String tipo,
            @RequestParam String material,
            @RequestParam String estilo,
            @RequestParam String dimensiones) {
        MuebleBuilder builder = new ConcreteMuebleBuilder();
        return builder.setTipo(tipo)
                .setMaterial(material)
                .setEstilo(estilo)
                .setDimensiones(dimensiones)
                .build();
    }

    @GetMapping("/info")
    public String getInfo() {
        return "Furnistore API - Sistema de construcción de muebles";
    }
}