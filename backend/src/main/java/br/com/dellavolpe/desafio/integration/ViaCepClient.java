package br.com.dellavolpe.desafio.integration;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClient {
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";

    private final RestTemplate restTemplate;

    public ViaCepClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ViaCepResponse buscarEnderecoPorCep(String cep) {
        String url = String.format(VIA_CEP_URL, cep);
        return restTemplate.getForObject(url, ViaCepResponse.class);
    }
}
