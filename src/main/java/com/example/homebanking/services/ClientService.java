package com.example.homebanking.services;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {

    public List<ClientDTO> getClients();
    public Client saveClient (Client client);
    public ClientDTO getClient(Long id);
    public Client findByEmail(String email);
}
