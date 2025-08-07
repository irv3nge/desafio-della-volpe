package br.com.dellavolpe.desafio.services;

import br.com.dellavolpe.desafio.exception.EnderecoNotFoundException;
import br.com.dellavolpe.desafio.integration.ViaCepResponse;
import br.com.dellavolpe.desafio.model.Endereco;
import br.com.dellavolpe.desafio.model.Usuario;
import br.com.dellavolpe.desafio.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final CepService cepService;
    private final UsuarioService usuarioService;

    public EnderecoService(EnderecoRepository enderecoRepository, CepService cepService, UsuarioService usuarioService) {
        this.enderecoRepository = enderecoRepository;
        this.cepService = cepService;
        this.usuarioService = usuarioService;
    }

    public Endereco criar(Endereco endereco, Long usuarioId) {
        if (!cepService.validarCep(endereco.getCep())) {
            throw new IllegalArgumentException("CEP inválido");
        }

        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        endereco.setUsuario(usuario);

        // Preenche dados do endereço com base no CEP
        ViaCepResponse viaCepResponse = cepService.consultarCep(endereco.getCep());
        endereco.setRua(viaCepResponse.getLogradouro());
        endereco.setBairro(viaCepResponse.getBairro());
        endereco.setCidade(viaCepResponse.getLocalidade());
        endereco.setEstado(viaCepResponse.getUf());

        return enderecoRepository.save(endereco);
    }

    public Endereco atualizar(Long id, Endereco enderecoAtualizado) {
        if (!cepService.validarCep(enderecoAtualizado.getCep())) {
            throw new IllegalArgumentException("CEP inválido");
        }

        return enderecoRepository.findById(id)
                .map(endereco -> {
                    endereco.setCep(enderecoAtualizado.getCep());
                    endereco.setNumero(enderecoAtualizado.getNumero());

                    // Atualiza outros campos com base no novo CEP
                    ViaCepResponse viaCepResponse = cepService.consultarCep(enderecoAtualizado.getCep());
                    endereco.setRua(viaCepResponse.getLogradouro());
                    endereco.setBairro(viaCepResponse.getBairro());
                    endereco.setCidade(viaCepResponse.getLocalidade());
                    endereco.setEstado(viaCepResponse.getUf());

                    return enderecoRepository.save(endereco);
                })
                .orElseThrow(() -> new EnderecoNotFoundException(id));
    }

    public void deletar(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNotFoundException(id));
        enderecoRepository.delete(endereco);
    }
}
