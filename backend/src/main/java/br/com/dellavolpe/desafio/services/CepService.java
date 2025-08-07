package br.com.dellavolpe.desafio.services;


import br.com.dellavolpe.desafio.integration.ViaCepClient;
import br.com.dellavolpe.desafio.integration.ViaCepResponse;
import org.springframework.stereotype.Service;

@Service
public class CepService {
    private final ViaCepClient viaCepClient;

    public CepService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    public boolean validarCep(String cep) {
        ViaCepResponse response = viaCepClient.buscarEnderecoPorCep(cep);
        return response != null && response.getErro() == null;
    }

    public ViaCepResponse consultarCep(String cep) {
        return viaCepClient.buscarEnderecoPorCep(cep);
    }
}
