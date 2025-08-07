package br.com.dellavolpe.desafio;



import br.com.dellavolpe.desafio.model.Endereco;
import br.com.dellavolpe.desafio.model.Usuario;
import br.com.dellavolpe.desafio.repository.EnderecoRepository;
import br.com.dellavolpe.desafio.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EnderecoRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Test
    @DisplayName("Deve salvar um endereço vinculado a um usuário")
    void deveSalvarEnderecoComUsuario() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .nome("Carlos Lima")
                .email("carlos.lima@teste.com")
                .telefone("11666666666")
                .build();

        usuario = usuarioRepository.save(usuario);

        Endereco endereco = Endereco.builder()
                .cep("01001000")
                .rua("Praça da Sé")
                .numero("100")
                .estado("SP")
                .cidade("São Paulo")
                .bairro("Centro")
                .usuario(usuario)
                .build();

        // Act
        Endereco salvo = enderecoRepository.save(endereco);

        // Assert
        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getUsuario()).isEqualTo(usuario);
    }

    @Test
    @DisplayName("Deve buscar um endereço por ID")
    void deveBuscarEnderecoPorId() {
        // Arrange
        Usuario usuario = usuarioRepository.save(
                Usuario.builder()
                        .nome("Joana")
                        .email("joana@teste.com")
                        .telefone("11555555555")
                        .build()
        );

        Endereco endereco = enderecoRepository.save(
                Endereco.builder()
                        .cep("22222222")
                        .rua("Rua do Sol")
                        .numero("22")
                        .estado("RJ")
                        .cidade("Rio de Janeiro")
                        .bairro("Botafogo")
                        .usuario(usuario)
                        .build()
        );

        // Act
        Optional<Endereco> encontrado = enderecoRepository.findById(endereco.getId());

        // Assert
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getRua()).isEqualTo("Rua do Sol");
    }

    @Test
    @DisplayName("Deve deletar um endereço")
    void deveDeletarEndereco() {
        // Arrange
        Usuario usuario = usuarioRepository.save(
                Usuario.builder()
                        .nome("Marina")
                        .email("marina@teste.com")
                        .telefone("11444444444")
                        .build()
        );

        Endereco endereco = enderecoRepository.save(
                Endereco.builder()
                        .cep("33333333")
                        .rua("Av. Paulista")
                        .numero("123")
                        .estado("SP")
                        .cidade("São Paulo")
                        .bairro("Bela Vista")
                        .usuario(usuario)
                        .build()
        );

        Long id = endereco.getId();

        // Act
        enderecoRepository.deleteById(id);

        // Assert
        assertThat(enderecoRepository.findById(id)).isEmpty();
    }
}

