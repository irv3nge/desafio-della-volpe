package br.com.dellavolpe.desafio.exception;

public class EnderecoNotFoundException extends RuntimeException {
    public EnderecoNotFoundException(Long id) {
        super("Endereço não encontrado com ID: " + id);
    }
}
