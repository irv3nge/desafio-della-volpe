package br.com.dellavolpe.desafio.exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Long id) {
        super("Usuário não encontrado com ID: " + id);
    }
}
