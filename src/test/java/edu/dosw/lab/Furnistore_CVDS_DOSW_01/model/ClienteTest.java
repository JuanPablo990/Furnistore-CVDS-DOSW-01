package edu.dosw.lab.Furnistore_CVDS_DOSW_01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteTest {

    @Test
    @DisplayName("Constructor por defecto: Happy Path")
    void testNoArgsConstructorHappyPath() {
        Cliente cliente = new Cliente();

        assertNull(cliente.getId());
        assertNull(cliente.getNombre());
        assertNull(cliente.getDireccion());
        assertNull(cliente.getEmail());
        assertNull(cliente.getTelefono());
    }

    @Test
    @DisplayName("Constructor por defecto: Error - valores por defecto null")
    void testNoArgsConstructorWithNullValues() {
        Cliente cliente = new Cliente();

        // Verificar que todos los campos son null por defecto
        assertNull(cliente.getId());
        assertNull(cliente.getNombre());
        assertNull(cliente.getDireccion());
        assertNull(cliente.getEmail());
        assertNull(cliente.getTelefono());
    }

    @Test
    @DisplayName("Constructor completo: Happy Path - todos los parámetros")
    void testAllArgsConstructorHappyPath() {
        Cliente cliente = new Cliente("C001", "Juan Pérez", "Calle 123", "juan@email.com", "123456789");

        assertEquals("C001", cliente.getId());
        assertEquals("Juan Pérez", cliente.getNombre());
        assertEquals("Calle 123", cliente.getDireccion());
        assertEquals("juan@email.com", cliente.getEmail());
        assertEquals("123456789", cliente.getTelefono());
    }

    @Test
    @DisplayName("Constructor completo: Error - algunos parámetros null")
    void testAllArgsConstructorWithSomeNulls() {
        Cliente cliente = new Cliente("C002", null, "Avenida Principal", null, "987654321");

        assertEquals("C002", cliente.getId());
        assertNull(cliente.getNombre());
        assertEquals("Avenida Principal", cliente.getDireccion());
        assertNull(cliente.getEmail());
        assertEquals("987654321", cliente.getTelefono());
    }

    @Test
    @DisplayName("Constructor simplificado: Happy Path - nombre y email")
    void testSimplifiedConstructorHappyPath() {
        Cliente cliente = new Cliente("María García", "maria@email.com");

        assertNull(cliente.getId());
        assertEquals("María García", cliente.getNombre());
        assertNull(cliente.getDireccion());
        assertEquals("maria@email.com", cliente.getEmail());
        assertNull(cliente.getTelefono());
    }

    @Test
    @DisplayName("Constructor simplificado: Error - nombre null")
    void testSimplifiedConstructorWithNullNombre() {
        Cliente cliente = new Cliente(null, "test@email.com");

        assertNull(cliente.getId());
        assertNull(cliente.getNombre());
        assertNull(cliente.getDireccion());
        assertEquals("test@email.com", cliente.getEmail());
        assertNull(cliente.getTelefono());
    }

    @Test
    @DisplayName("Constructor simplificado: Error - email null")
    void testSimplifiedConstructorWithNullEmail() {
        Cliente cliente = new Cliente("Carlos López", null);

        assertNull(cliente.getId());
        assertEquals("Carlos López", cliente.getNombre());
        assertNull(cliente.getDireccion());
        assertNull(cliente.getEmail());
        assertNull(cliente.getTelefono());
    }

    @Test
    @DisplayName("Setters: Happy Path - todos los campos")
    void testSettersHappyPath() {
        Cliente cliente = new Cliente();

        cliente.setId("C003");
        cliente.setNombre("Ana Rodríguez");
        cliente.setDireccion("Plaza Central 456");
        cliente.setEmail("ana@empresa.com");
        cliente.setTelefono("555-1234");

        assertEquals("C003", cliente.getId());
        assertEquals("Ana Rodríguez", cliente.getNombre());
        assertEquals("Plaza Central 456", cliente.getDireccion());
        assertEquals("ana@empresa.com", cliente.getEmail());
        assertEquals("555-1234", cliente.getTelefono());
    }

    @Test
    @DisplayName("Setters: Error - establecer valores null")
    void testSettersWithNullValues() {
        Cliente cliente = new Cliente("Temp", "temp@email.com");

        cliente.setId(null);
        cliente.setNombre(null);
        cliente.setDireccion(null);
        cliente.setEmail(null);
        cliente.setTelefono(null);

        assertNull(cliente.getId());
        assertNull(cliente.getNombre());
        assertNull(cliente.getDireccion());
        assertNull(cliente.getEmail());
        assertNull(cliente.getTelefono());
    }

    @Test
    @DisplayName("Setters: Error - establecer valores vacíos")
    void testSettersWithEmptyValues() {
        Cliente cliente = new Cliente();

        cliente.setId("");
        cliente.setNombre("");
        cliente.setDireccion("");
        cliente.setEmail("");
        cliente.setTelefono("");

        assertEquals("", cliente.getId());
        assertEquals("", cliente.getNombre());
        assertEquals("", cliente.getDireccion());
        assertEquals("", cliente.getEmail());
        assertEquals("", cliente.getTelefono());
    }

    @Test
    @DisplayName("Equals y HashCode: Happy Path - mismos valores")
    void testEqualsAndHashCodeHappyPath() {
        Cliente cliente1 = new Cliente("C001", "Juan", "Dir 1", "juan@email.com", "123");
        Cliente cliente2 = new Cliente("C001", "Juan", "Dir 1", "juan@email.com", "123");

        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }

    @Test
    @DisplayName("Equals y HashCode: Error - diferentes valores")
    void testEqualsAndHashCodeWithDifferentValues() {
        Cliente cliente1 = new Cliente("C001", "Juan", "Dir 1", "juan@email.com", "123");
        Cliente cliente2 = new Cliente("C002", "Pedro", "Dir 2", "pedro@email.com", "456");

        assertNotEquals(cliente1, cliente2);
        assertNotEquals(cliente1.hashCode(), cliente2.hashCode());
    }

    @Test
    @DisplayName("ToString: Happy Path - contiene todos los campos")
    void testToStringHappyPath() {
        Cliente cliente = new Cliente("C001", "Juan Pérez", "Calle 123", "juan@email.com", "123456789");
        String toString = cliente.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("C001"));
        assertTrue(toString.contains("Juan Pérez"));
        assertTrue(toString.contains("Calle 123"));
        assertTrue(toString.contains("juan@email.com"));
        assertTrue(toString.contains("123456789"));
    }

    @Test
    @DisplayName("ToString: Error - con valores null")
    void testToStringWithNullValues() {
        Cliente cliente = new Cliente(null, null, null, null, null);
        String toString = cliente.toString();

        assertNotNull(toString);

        assertDoesNotThrow(cliente::toString);
    }

    @Test
    @DisplayName("Integración: uso completo del objeto Cliente")
    void testClienteIntegration() {

        Cliente cliente = new Cliente("Roberto Silva", "roberto@email.com");


        cliente.setId("C004");
        cliente.setDireccion("Boulevard Norte 789");
        cliente.setTelefono("555-9876");


        assertEquals("C004", cliente.getId());
        assertEquals("Roberto Silva", cliente.getNombre());
        assertEquals("Boulevard Norte 789", cliente.getDireccion());
        assertEquals("roberto@email.com", cliente.getEmail());
        assertEquals("555-9876", cliente.getTelefono());

        String toString = cliente.toString();
        assertTrue(toString.contains("Roberto Silva"));
        assertTrue(toString.contains("roberto@email.com"));


        Cliente mismoCliente = new Cliente("C004", "Roberto Silva", "Boulevard Norte 789", "roberto@email.com", "555-9876");
        assertEquals(cliente, mismoCliente);
        assertEquals(cliente.hashCode(), mismoCliente.hashCode());
    }

    @Test
    @DisplayName("Integración: modificación secuencial de datos")
    void testSequentialDataModification() {
        Cliente cliente = new Cliente("Nombre Inicial", "inicial@email.com");


        cliente.setNombre("Nombre Modificado");
        cliente.setEmail("modificado@email.com");
        assertEquals("Nombre Modificado", cliente.getNombre());
        assertEquals("modificado@email.com", cliente.getEmail());


        cliente.setNombre("Segundo Nombre");
        cliente.setEmail("segundo@email.com");
        assertEquals("Segundo Nombre", cliente.getNombre());
        assertEquals("segundo@email.com", cliente.getEmail());


        cliente.setNombre(null);
        cliente.setEmail(null);
        assertNull(cliente.getNombre());
        assertNull(cliente.getEmail());
    }
}