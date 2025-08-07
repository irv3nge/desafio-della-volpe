package br.com.dellavolpe.desafio;

import br.com.dellavolpe.desafio.exception.UsuarioNotFoundException;
import br.com.dellavolpe.desafio.model.Usuario;
import br.com.dellavolpe.desafio.repository.UsuarioRepository;
import br.com.dellavolpe.desafio.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void listarTodos_DeveRetornarListaDeUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));

        List<Usuario> usuarios = usuarioService.listarTodos();

        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_DeveRetornarUsuario_QuandoExistir() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.buscarPorId(id);

        assertEquals(id, encontrado.getId());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void buscarPorId_DeveLancarExcecao_QuandoNaoExistir() {
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.buscarPorId(id));
    }
}