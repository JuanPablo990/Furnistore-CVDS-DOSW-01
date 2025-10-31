package edu.dosw.lab.Furnistore_CVDS_DOSW_01.service;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

class ClienteServiceTest {

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService();
    }

    @Test
    @DisplayName("registrarCliente: Happy Path")
    void testRegistrarClienteHappyPath() {
        Cliente clienteMock = mock(Cliente.class);
        when(clienteMock.getNombre()).thenReturn("Juan Pérez");
        when(clienteMock.getEmail()).thenReturn("juan@email.com");

        Cliente resultado = clienteService.registrarCliente(clienteMock);

        assertNotNull(resultado);
        verify(clienteMock, times(1)).setId(startsWith("CLI-"));
        assertEquals(1, clienteService.obtenerTodosLosClientes().size());
    }

    @Test
    @DisplayName("registrarCliente: Error - cliente null")
    void testRegistrarClienteWithNull() {
        assertThrows(NullPointerException.class, () -> {
            clienteService.registrarCliente(null);
        });
    }

    @Test
    @DisplayName("registrarCliente: Error - cliente sin nombre")
    void testRegistrarClienteWithoutNombre() {
        Cliente clienteMock = mock(Cliente.class);
        when(clienteMock.getNombre()).thenReturn(null);
        when(clienteMock.getEmail()).thenReturn("test@email.com");

        Cliente resultado = clienteService.registrarCliente(clienteMock);

        assertNotNull(resultado);
        verify(clienteMock, times(1)).setId(startsWith("CLI-"));
    }

    @Test
    @DisplayName("buscarClientePorId: Happy Path - cliente encontrado")
    void testBuscarClientePorIdHappyPath() {
        Cliente clienteMock = mock(Cliente.class);
        when(clienteMock.getNombre()).thenReturn("María García");

        clienteService.registrarCliente(clienteMock);

        // Configurar el ID después del registro
        when(clienteMock.getId()).thenReturn("CLI-1");
        Optional<Cliente> resultado = clienteService.buscarClientePorId("CLI-1");

        assertTrue(resultado.isPresent());
        assertEquals("CLI-1", resultado.get().getId());
        assertEquals("María García", resultado.get().getNombre());
    }

    @Test
    @DisplayName("buscarClientePorId: Error - cliente no encontrado")
    void testBuscarClientePorIdNotFound() {
        Optional<Cliente> resultado = clienteService.buscarClientePorId("CLI-999");

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("buscarClientePorId: Error - id null")
    void testBuscarClientePorIdWithNullId() {
        Optional<Cliente> resultado = clienteService.buscarClientePorId(null);

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("buscarClientePorId: Error - id vacío")
    void testBuscarClientePorIdWithEmptyId() {
        Optional<Cliente> resultado = clienteService.buscarClientePorId("");

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("obtenerTodosLosClientes: Happy Path - con clientes")
    void testObtenerTodosLosClientesHappyPath() {
        Cliente cliente1 = mock(Cliente.class);
        Cliente cliente2 = mock(Cliente.class);

        when(cliente1.getNombre()).thenReturn("Cliente 1");
        when(cliente2.getNombre()).thenReturn("Cliente 2");

        clienteService.registrarCliente(cliente1);
        clienteService.registrarCliente(cliente2);

        List<Cliente> resultado = clienteService.obtenerTodosLosClientes();

        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("obtenerTodosLosClientes: Error - sin clientes")
    void testObtenerTodosLosClientesEmpty() {
        List<Cliente> resultado = clienteService.obtenerTodosLosClientes();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    @DisplayName("IDs secuenciales: Happy Path")
    void testSequentialIds() {
        Cliente cliente1 = mock(Cliente.class);
        Cliente cliente2 = mock(Cliente.class);
        Cliente cliente3 = mock(Cliente.class);

        when(cliente1.getNombre()).thenReturn("Primero");
        when(cliente2.getNombre()).thenReturn("Segundo");
        when(cliente3.getNombre()).thenReturn("Tercero");

        clienteService.registrarCliente(cliente1);
        clienteService.registrarCliente(cliente2);
        clienteService.registrarCliente(cliente3);

        verify(cliente1, times(1)).setId("CLI-1");
        verify(cliente2, times(1)).setId("CLI-2");
        verify(cliente3, times(1)).setId("CLI-3");
    }

    @Test
    @DisplayName("Lista copia: Verificar que retorna copia defensiva")
    void testListReturnsCopy() {
        Cliente cliente1 = mock(Cliente.class);
        when(cliente1.getNombre()).thenReturn("Test");

        clienteService.registrarCliente(cliente1);

        List<Cliente> clientes1 = clienteService.obtenerTodosLosClientes();
        List<Cliente> clientes2 = clienteService.obtenerTodosLosClientes();


        assertNotSame(clientes1, clientes2);
        assertEquals(1, clientes1.size());
        assertEquals(1, clientes2.size());
    }

    @Test
    @DisplayName("Spring Service: verificar anotación")
    void testSpringServiceAnnotation() {
        ClienteService service = new ClienteService();

        assertNotNull(service);

        Class<?> serviceClass = service.getClass();
        assertTrue(serviceClass.isAnnotationPresent(org.springframework.stereotype.Service.class));
    }

    @Test
    @DisplayName("Registro masivo: Happy Path")
    void testMassiveRegistration() {
        for (int i = 0; i < 10; i++) {
            Cliente cliente = mock(Cliente.class);
            when(cliente.getNombre()).thenReturn("Cliente " + i);
            clienteService.registrarCliente(cliente);
        }

        List<Cliente> resultado = clienteService.obtenerTodosLosClientes();
        assertEquals(10, resultado.size());

        }

    @Test
    @DisplayName("Cliente real: Integración completa")
    void testWithRealCliente() {
        Cliente clienteReal = new Cliente("Pedro Gómez", "pedro@email.com");

        Cliente resultado = clienteService.registrarCliente(clienteReal);

        assertNotNull(resultado.getId());
        assertTrue(resultado.getId().startsWith("CLI-"));

        Optional<Cliente> encontrado = clienteService.buscarClientePorId(resultado.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Pedro Gómez", encontrado.get().getNombre());
        assertEquals("pedro@email.com", encontrado.get().getEmail());
    }

    @Test
    @DisplayName("Múltiples búsquedas: Happy Path")
    void testMultipleSearches() {
        Cliente cliente1 = new Cliente("Cliente Uno", "uno@email.com");
        Cliente cliente2 = new Cliente("Cliente Dos", "dos@email.com");

        clienteService.registrarCliente(cliente1);
        clienteService.registrarCliente(cliente2);

        Optional<Cliente> resultado1 = clienteService.buscarClientePorId(cliente1.getId());
        Optional<Cliente> resultado2 = clienteService.buscarClientePorId(cliente2.getId());
        Optional<Cliente> resultado3 = clienteService.buscarClientePorId("CLI-999");

        assertTrue(resultado1.isPresent());
        assertTrue(resultado2.isPresent());
        assertFalse(resultado3.isPresent());

        assertEquals("Cliente Uno", resultado1.get().getNombre());
        assertEquals("Cliente Dos", resultado2.get().getNombre());
    }
}