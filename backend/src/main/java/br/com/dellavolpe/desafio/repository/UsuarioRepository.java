package br.com.dellavolpe.desafio.repository;

import br.com.dellavolpe.desafio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario , Long> {
}
