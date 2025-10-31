package edu.dosw.lab.Furnistore_CVDS_DOSW_01.service;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MuebleServiceTest {

    @Mock
    private MuebleDirector muebleDirector;

    private MuebleService muebleService;
    private Mueble sofaClasico;
    private Mueble camaModerna;

    @BeforeEach
    void setUp() {
        // Configurar muebles de prueba
        sofaClasico = new Mueble("Sofá", "Madera", "Clásico", "200x90x80");
        camaModerna = new Mueble("Cama", "Metal", "Moderno", "190x140x40");

        // Configurar comportamiento del mock ANTES de crear el servicio
        when(muebleDirector.construirSofaClasico()).thenReturn(sofaClasico);
        when(muebleDirector.construirCamaModerna()).thenReturn(camaModerna);

        // Crear el servicio después de configurar los mocks
        muebleService = new MuebleService(muebleDirector);
    }

    @Test
    void obtenerTodosLosMuebles_HappyPath() {
        List<Mueble> resultado = muebleService.obtenerTodosLosMuebles();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(sofaClasico));
        assertTrue(resultado.contains(camaModerna));
    }


    @Test
    void obtenerMueblePorTipo_HappyPath() {
        Optional<Mueble> resultado = muebleService.obtenerMueblePorTipo("Sofá");

        assertTrue(resultado.isPresent());
        assertEquals(sofaClasico, resultado.get());
    }

    @Test
    void obtenerMueblePorTipo_NoEncontrado() {
        Optional<Mueble> resultado = muebleService.obtenerMueblePorTipo("Mesa");

        assertFalse(resultado.isPresent());
    }

    @Test
    void obtenerMueblePorTipo_CaseInsensitive() {
        Optional<Mueble> resultado1 = muebleService.obtenerMueblePorTipo("SOFÁ");
        Optional<Mueble> resultado2 = muebleService.obtenerMueblePorTipo("sofá");
        Optional<Mueble> resultado3 = muebleService.obtenerMueblePorTipo("Sofá");

        assertTrue(resultado1.isPresent());
        assertTrue(resultado2.isPresent());
        assertTrue(resultado3.isPresent());
        assertEquals(sofaClasico, resultado1.get());
    }

    @Test
    void crearSofaClasico_HappyPath() {
        Mueble nuevoSofa = new Mueble("Sofá", "Cuero", "Clásico", "220x95x85");
        when(muebleDirector.construirSofaClasico()).thenReturn(nuevoSofa);

        int tamañoInicial = muebleService.obtenerTodosLosMuebles().size();
        Mueble resultado = muebleService.crearSofaClasico();

        assertNotNull(resultado);
        assertEquals(nuevoSofa, resultado);

        List<Mueble> catalogo = muebleService.obtenerTodosLosMuebles();
        assertEquals(tamañoInicial + 1, catalogo.size());
        assertTrue(catalogo.contains(nuevoSofa));
    }

    @Test
    void crearCamaModerna_HappyPath() {
        Mueble nuevaCama = new Mueble("Cama", "Madera", "Moderno", "200x150x45");
        when(muebleDirector.construirCamaModerna()).thenReturn(nuevaCama);

        int tamañoInicial = muebleService.obtenerTodosLosMuebles().size();
        Mueble resultado = muebleService.crearCamaModerna();

        assertNotNull(resultado);
        assertEquals(nuevaCama, resultado);

        List<Mueble> catalogo = muebleService.obtenerTodosLosMuebles();
        assertEquals(tamañoInicial + 1, catalogo.size());
        assertTrue(catalogo.contains(nuevaCama));
    }

    @Test
    void crearMueblePersonalizado_HappyPath() {
        Mueble mueblePersonalizado = new Mueble("Mesa", "Cristal", "Minimalista", "120x60x75");
        when(muebleDirector.construirMueblePersonalizado("Mesa", "Cristal", "Minimalista", "120x60x75"))
                .thenReturn(mueblePersonalizado);

        int tamañoInicial = muebleService.obtenerTodosLosMuebles().size();
        Mueble resultado = muebleService.crearMueblePersonalizado("Mesa", "Cristal", "Minimalista", "120x60x75");

        assertNotNull(resultado);
        assertEquals(mueblePersonalizado, resultado);

        List<Mueble> catalogo = muebleService.obtenerTodosLosMuebles();
        assertEquals(tamañoInicial + 1, catalogo.size());
        assertTrue(catalogo.contains(mueblePersonalizado));
    }

    @Test
    void obtenerTotalMuebles_HappyPath() {
        int total = muebleService.obtenerTotalMuebles();

        assertEquals(2, total);
    }


    @Test
    void filtrarMuebles_PorTipo_HappyPath() {
        List<Mueble> resultado = muebleService.filtrarMuebles("Sofá", null, null);

        assertEquals(1, resultado.size());
        assertEquals(sofaClasico, resultado.get(0));
    }

    @Test
    void filtrarMuebles_PorMaterial_HappyPath() {
        List<Mueble> resultado = muebleService.filtrarMuebles(null, "Madera", null);

        assertEquals(1, resultado.size());
        assertEquals(sofaClasico, resultado.get(0));
    }

    @Test
    void filtrarMuebles_PorEstilo_HappyPath() {
        List<Mueble> resultado = muebleService.filtrarMuebles(null, null, "Moderno");

        assertEquals(1, resultado.size());
        assertEquals(camaModerna, resultado.get(0));
    }

    @Test
    void filtrarMuebles_MultiplesCriterios_HappyPath() {
        List<Mueble> resultado = muebleService.filtrarMuebles("Cama", "Metal", "Moderno");

        assertEquals(1, resultado.size());
        assertEquals(camaModerna, resultado.get(0));
    }

    @Test
    void filtrarMuebles_SinCriterios() {
        List<Mueble> resultado = muebleService.filtrarMuebles(null, null, null);

        assertEquals(2, resultado.size());
    }

    @Test
    void filtrarMuebles_CriterioNoEncontrado() {
        List<Mueble> resultado = muebleService.filtrarMuebles("Mesa", null, null);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void filtrarMuebles_CaseInsensitive() {
        List<Mueble> resultado = muebleService.filtrarMuebles("SOFÁ", "MADERA", "CLÁSICO");

        assertEquals(1, resultado.size());
        assertEquals(sofaClasico, resultado.get(0));
    }

    @Test
    void inicializarCatalogo_AlCrearServicio() {
        // Verificar que al crear el servicio se inicializa el catálogo
        verify(muebleDirector, times(1)).construirSofaClasico();
        verify(muebleDirector, times(1)).construirCamaModerna();

        List<Mueble> catalogo = muebleService.obtenerTodosLosMuebles();
        assertEquals(2, catalogo.size());
    }
}