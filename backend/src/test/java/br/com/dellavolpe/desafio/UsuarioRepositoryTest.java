package br.com.dellavolpe.desafio;

import br.com.dellavolpe.desafio.model.Endereco;
import br.com.dellavolpe.desafio.model.Usuario;
import br.com.dellavolpe.desafio.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve salvar um usuário com endereços")
    void deveSalvarUsuarioComEnderecos() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .nome("Pedro Jorge")
                .email("pedro.jorge@teste.com")
                .telefone("11999999999")
                .build();

        Endereco endereco1 = Endereco.builder()
                .cep("12345678")
                .rua("Rua A")
                .numero("123")
                .estado("SP")
                .cidade("São Paulo")
                .bairro("Centro")
                .usuario(usuario)
                .build();

        Endereco endereco2 = Endereco.builder()
                .cep("87654321")
                .rua("Rua B")
                .numero("456")
                .estado("RJ")
                .cidade("Rio de Janeiro")
                .bairro("Copacabana")
                .usuario(usuario)
                .build();

        usuario.setEnderecos(Arrays.asList(endereco1, endereco2));

        // Act
        Usuario salvo = usuarioRepository.save(usuario);

        // Assert
        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getEnderecos()).hasSize(2);
        assertThat(salvo.getEnderecos().get(0).getUsuario().getId()).isEqualTo(salvo.getId());
    }

    @Test
    @DisplayName("Deve buscar um usuário por ID")
    void deveBuscarUsuarioPorId() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .nome("João Silva")
                .email("joao.silva@teste.com")
                .telefone("11888888888")
                .build();

        Usuario salvo = usuarioRepository.save(usuario);

        // Act
        Optional<Usuario> encontrado = usuarioRepository.findById(salvo.getId());

        // Assert
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("Deve deletar um usuário")
    void deveDeletarUsuario() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .nome("Maria Oliveira")
                .email("maria.oliveira@teste.com")
                .telefone("11777777777")
                .build();

        Usuario salvo = usuarioRepository.save(usuario);

        // Act
        usuarioRepository.deleteById(salvo.getId());

        // Assert
        Optional<Usuario> resultado = usuarioRepository.findById(salvo.getId());
        assertThat(resultado).isEmpty();
    }
}
