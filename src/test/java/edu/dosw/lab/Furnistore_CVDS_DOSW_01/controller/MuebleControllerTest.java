package edu.dosw.lab.Furnistore_CVDS_DOSW_01.controller;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.Mueble;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.service.MuebleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MuebleControllerTest {

    @Mock
    private MuebleService muebleService;

    @InjectMocks
    private MuebleController muebleController;

    private Mueble sofaClasico;
    private Mueble camaModerna;
    private List<Mueble> listaMuebles;

    @BeforeEach
    void setUp() {
        sofaClasico = new Mueble("Sofá", "Madera", "Clásico", "200x90x80");
        camaModerna = new Mueble("Cama", "Metal", "Moderno", "190x140x40");
        listaMuebles = Arrays.asList(sofaClasico, camaModerna);
    }

    @Test
    void obtenerTodosLosMuebles_HappyPath() {
        when(muebleService.obtenerTodosLosMuebles()).thenReturn(listaMuebles);

        ResponseEntity<List<Mueble>> response = muebleController.obtenerTodosLosMuebles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(listaMuebles, response.getBody());
        verify(muebleService, times(1)).obtenerTodosLosMuebles();
    }

    @Test
    void obtenerTodosLosMuebles_ListaVacia() {
        when(muebleService.obtenerTodosLosMuebles()).thenReturn(List.of());

        ResponseEntity<List<Mueble>> response = muebleController.obtenerTodosLosMuebles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(muebleService, times(1)).obtenerTodosLosMuebles();
    }

    @Test
    void obtenerMueblePorTipo_HappyPath() {
        when(muebleService.obtenerMueblePorTipo("Sofá")).thenReturn(Optional.of(sofaClasico));

        ResponseEntity<Mueble> response = muebleController.obtenerMueblePorTipo("Sofá");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sofaClasico, response.getBody());
        verify(muebleService, times(1)).obtenerMueblePorTipo("Sofá");
    }

    @Test
    void obtenerMueblePorTipo_NoEncontrado() {
        when(muebleService.obtenerMueblePorTipo("Mesa")).thenReturn(Optional.empty());

        ResponseEntity<Mueble> response = muebleController.obtenerMueblePorTipo("Mesa");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(muebleService, times(1)).obtenerMueblePorTipo("Mesa");
    }

    @Test
    void crearSofaClasico_HappyPath() {
        when(muebleService.crearSofaClasico()).thenReturn(sofaClasico);

        ResponseEntity<Mueble> response = muebleController.crearSofaClasico();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sofaClasico, response.getBody());
        verify(muebleService, times(1)).crearSofaClasico();
    }

    @Test
    void crearCamaModerna_HappyPath() {
        when(muebleService.crearCamaModerna()).thenReturn(camaModerna);

        ResponseEntity<Mueble> response = muebleController.crearCamaModerna();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(camaModerna, response.getBody());
        verify(muebleService, times(1)).crearCamaModerna();
    }

    @Test
    void crearMueblePersonalizado_HappyPath() {
        Mueble mueblePersonalizado = new Mueble("Mesa", "Cristal", "Minimalista", "120x60x75");
        when(muebleService.crearMueblePersonalizado("Mesa", "Cristal", "Minimalista", "120x60x75"))
                .thenReturn(mueblePersonalizado);

        ResponseEntity<Mueble> response = muebleController.crearMueblePersonalizado(
                "Mesa", "Cristal", "Minimalista", "120x60x75");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mueblePersonalizado, response.getBody());
        verify(muebleService, times(1))
                .crearMueblePersonalizado("Mesa", "Cristal", "Minimalista", "120x60x75");
    }

    @Test
    void crearMueblePersonalizado_ConParametrosNulos() {
        Mueble mueblePersonalizado = new Mueble("Silla", null, null, "50x50x100");
        when(muebleService.crearMueblePersonalizado("Silla", null, null, "50x50x100"))
                .thenReturn(mueblePersonalizado);

        ResponseEntity<Mueble> response = muebleController.crearMueblePersonalizado(
                "Silla", null, null, "50x50x100");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mueblePersonalizado, response.getBody());
        verify(muebleService, times(1))
                .crearMueblePersonalizado("Silla", null, null, "50x50x100");
    }

    @Test
    void obtenerTotalMuebles_HappyPath() {
        when(muebleService.obtenerTotalMuebles()).thenReturn(5);

        ResponseEntity<Integer> response = muebleController.obtenerTotalMuebles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5, response.getBody());
        verify(muebleService, times(1)).obtenerTotalMuebles();
    }

    @Test
    void obtenerTotalMuebles_Cero() {
        when(muebleService.obtenerTotalMuebles()).thenReturn(0);

        ResponseEntity<Integer> response = muebleController.obtenerTotalMuebles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody());
        verify(muebleService, times(1)).obtenerTotalMuebles();
    }

    @Test
    void constructor_InyeccionDependencias() {
        MuebleService serviceMock = mock(MuebleService.class);
        MuebleController controller = new MuebleController(serviceMock);

        assertNotNull(controller);
        // Verificar que el servicio se inyecta correctamente
        // Esto se prueba indirectamente al ejecutar otros tests
    }
}