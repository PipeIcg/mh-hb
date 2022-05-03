package com.example.homebanking.services.implementations;

import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public ClientDTO getClient(Long id) {
        ClientDTO c1 = new ClientDTO(clientRepository.findById(id).orElse(null));
        return c1;
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
}
