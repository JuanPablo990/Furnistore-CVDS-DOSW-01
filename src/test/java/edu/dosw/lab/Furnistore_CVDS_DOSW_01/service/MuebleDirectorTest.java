package edu.dosw.lab.Furnistore_CVDS_DOSW_01.service;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.Mueble;
import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.MuebleBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MuebleDirectorTest {

    @Mock
    private MuebleBuilder builder;

    @Mock
    private Mueble muebleMock;

    @InjectMocks
    private MuebleDirector director;

    @Test
    public void testConstruirSofaClasicoHappy() {
        when(builder.setTipo("Sofá")).thenReturn(builder);
        when(builder.setMaterial("Cuero")).thenReturn(builder);
        when(builder.setEstilo("Clásico")).thenReturn(builder);
        when(builder.setDimensiones("200x90x100 cm")).thenReturn(builder);
        when(builder.build()).thenReturn(muebleMock);

        Mueble resultado = director.construirSofaClasico();

        assertNotNull(resultado);
        verify(builder).setTipo("Sofá");
        verify(builder).setMaterial("Cuero");
        verify(builder).setEstilo("Clásico");
        verify(builder).setDimensiones("200x90x100 cm");
        verify(builder).build();
    }

    @Test
    public void testConstruirSofaClasicoError() {
        when(builder.setTipo("Sofá")).thenReturn(builder);
        when(builder.setMaterial("Cuero")).thenReturn(builder);
        when(builder.setEstilo("Clásico")).thenReturn(builder);
        when(builder.setDimensiones("200x90x100 cm")).thenReturn(builder);
        when(builder.build()).thenReturn(null);

        Mueble resultado = director.construirSofaClasico();

        assertNull(resultado);
    }

    @Test
    public void testConstruirCamaModernaHappy() {
        when(builder.setTipo("Cama")).thenReturn(builder);
        when(builder.setMaterial("Madera")).thenReturn(builder);
        when(builder.setEstilo("Moderno")).thenReturn(builder);
        when(builder.setDimensiones("200x160 cm")).thenReturn(builder);
        when(builder.build()).thenReturn(muebleMock);

        Mueble resultado = director.construirCamaModerna();

        assertNotNull(resultado);
        verify(builder).setTipo("Cama");
        verify(builder).setMaterial("Madera");
        verify(builder).setEstilo("Moderno");
        verify(builder).setDimensiones("200x160 cm");
        verify(builder).build();
    }

    @Test
    public void testConstruirCamaModernaError() {
        when(builder.setTipo("Cama")).thenReturn(builder);
        when(builder.setMaterial("Madera")).thenReturn(builder);
        when(builder.setEstilo("Moderno")).thenThrow(new RuntimeException("Error al establecer estilo"));

        assertThrows(RuntimeException.class, () -> {
            director.construirCamaModerna();
        });
    }

    @Test
    public void testConstruirMueblePersonalizadoHappy() {
        when(builder.setTipo("Mesa")).thenReturn(builder);
        when(builder.setMaterial("Cristal")).thenReturn(builder);
        when(builder.setEstilo("Contemporáneo")).thenReturn(builder);
        when(builder.setDimensiones("150x80x75 cm")).thenReturn(builder);
        when(builder.build()).thenReturn(muebleMock);

        Mueble resultado = director.construirMueblePersonalizado("Mesa", "Cristal", "Contemporáneo", "150x80x75 cm");

        assertNotNull(resultado);
        verify(builder).setTipo("Mesa");
        verify(builder).setMaterial("Cristal");
        verify(builder).setEstilo("Contemporáneo");
        verify(builder).setDimensiones("150x80x75 cm");
        verify(builder).build();
    }

    @Test
    public void testConstruirMueblePersonalizadoError() {
        when(builder.setTipo("Mesa")).thenReturn(builder);
        when(builder.setMaterial("Cristal")).thenReturn(builder);
        when(builder.setEstilo("Contemporáneo")).thenReturn(builder);
        when(builder.setDimensiones("150x80x75 cm")).thenReturn(builder);
        when(builder.build()).thenThrow(new IllegalArgumentException("Dimensiones inválidas"));

        assertThrows(IllegalArgumentException.class, () -> {
            director.construirMueblePersonalizado("Mesa", "Cristal", "Contemporáneo", "150x80x75 cm");
        });
    }
}