package br.com.dellavolpe.desafio.services;

import br.com.dellavolpe.desafio.exception.UsuarioNotFoundException;
import br.com.dellavolpe.desafio.integration.ViaCepResponse;
import br.com.dellavolpe.desafio.model.Endereco;
import br.com.dellavolpe.desafio.model.Usuario;
import br.com.dellavolpe.desafio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final CepService cepService;


    private final UsuarioRepository usuarioRepository;

    public UsuarioService(CepService cepService, UsuarioRepository usuarioRepository) {
        this.cepService = cepService;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    public Usuario criar(Usuario usuario) {
        if (usuario.getEnderecos() != null && !usuario.getEnderecos().isEmpty()) {
            for (Endereco endereco : usuario.getEnderecos()) {
                // Validação simples de CEP
                String cep = endereco.getCep();
                if (cep == null || cep.length() != 8) {
                    throw new IllegalArgumentException("CEP inválido: " + cep);
                }

                // Preencher dados do endereço com base no CEP
                ViaCepResponse viaCep = cepService.consultarCep(cep);
                if (viaCep == null || viaCep.getErro() != null) {
                    throw new IllegalArgumentException("CEP não encontrado: " + cep);
                }

                endereco.setRua(viaCep.getLogradouro());
                endereco.setBairro(viaCep.getBairro());
                endereco.setCidade(viaCep.getLocalidade());
                endereco.setEstado(viaCep.getUf());

                // Vincula o usuário ao endereço
                endereco.setUsuario(usuario);
            }
        }

        return usuarioRepository.save(usuario);
    }


    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    usuario.setTelefone(usuarioAtualizado.getTelefone());

                    // Remove todos os endereços antigos
                    usuario.getEnderecos().clear();

                    // Adiciona os novos endereços (com o vínculo correto)
                    if (usuarioAtualizado.getEnderecos() != null) {
                        for (Endereco novoEndereco : usuarioAtualizado.getEnderecos()) {
                            // Validação do CEP
                            String cep = novoEndereco.getCep();
                            if (cep == null || cep.length() != 8) {
                                throw new IllegalArgumentException("CEP inválido: " + cep);
                            }

                            ViaCepResponse viaCep = cepService.consultarCep(cep);
                            if (viaCep == null || viaCep.getErro() != null) {
                                throw new IllegalArgumentException("CEP não encontrado: " + cep);
                            }

                            novoEndereco.setRua(viaCep.getLogradouro());
                            novoEndereco.setBairro(viaCep.getBairro());
                            novoEndereco.setCidade(viaCep.getLocalidade());
                            novoEndereco.setEstado(viaCep.getUf());
                            novoEndereco.setUsuario(usuario); // vínculo correto

                            usuario.getEnderecos().add(novoEndereco);
                        }
                    }

                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }


    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        // Força o carregamento da lista de endereços (em caso de FetchType.LAZY)
        if (usuario.getEnderecos() != null) {
            usuario.getEnderecos().size(); // Inicializa a collection
        }

        usuarioRepository.delete(usuario);
    }
}

