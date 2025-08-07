package br.com.dellavolpe.desafio;

import br.com.dellavolpe.desafio.integration.ViaCepClient;
import br.com.dellavolpe.desafio.integration.ViaCepResponse;
import br.com.dellavolpe.desafio.services.CepService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CepServiceTest {
    @Mock
    private ViaCepClient viaCepClient;

    @InjectMocks
    private CepService cepService;

    @Test
    void validarCep_DeveRetornarTrue_QuandoCepValido() {
        ViaCepResponse response = new ViaCepResponse();
        response.setCep("01001000");
        response.setLogradouro("Praça da Sé");

        when(viaCepClient.buscarEnderecoPorCep(anyString())).thenReturn(response);

        assertTrue(cepService.validarCep("01001000"));
    }

    @Test
    void validarCep_DeveRetornarFalse_QuandoCepInvalido() {
        ViaCepResponse response = new ViaCepResponse();
        response.setErro("true");

        when(viaCepClient.buscarEnderecoPorCep(anyString())).thenReturn(response);

        assertFalse(cepService.validarCep("00000000"));
    }
}
