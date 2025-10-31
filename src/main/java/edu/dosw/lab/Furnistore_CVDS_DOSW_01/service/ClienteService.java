package edu.dosw.lab.Furnistore_CVDS_DOSW_01.service;

import edu.dosw.lab.Furnistore_CVDS_DOSW_01.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClienteService {
    private final List<Cliente> clientes = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    /**
     * Registra un nuevo cliente en el sistema
     */
    public Cliente registrarCliente(Cliente cliente) {
        cliente.setId("CLI-" + contadorId.getAndIncrement());
        clientes.add(cliente);
        return cliente;
    }

    /**
     * Busca un cliente por su ID
     */
    public Optional<Cliente> buscarClientePorId(String id) {
        return clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    /**
     * Obtiene todos los clientes registrados
     */
    public List<Cliente> obtenerTodosLosClientes() {
        return new ArrayList<>(clientes);
    }
}