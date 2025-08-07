package br.com.dellavolpe.desafio.repository;

import br.com.dellavolpe.desafio.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
