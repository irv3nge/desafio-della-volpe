package br.com.dellavolpe.desafio.controller;

import br.com.dellavolpe.desafio.model.Endereco;
import br.com.dellavolpe.desafio.services.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/usuarios/{usuarioId}/enderecos")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    public ResponseEntity<Endereco> criar(
            @PathVariable Long usuarioId,
            @RequestBody Endereco endereco) {
        Endereco enderecoSalvo = enderecoService.criar(endereco, usuarioId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enderecoSalvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(enderecoSalvo);
    }

    @PutMapping("/{id}")
    public Endereco atualizar(
            @PathVariable Long id,
            @RequestBody Endereco endereco) {
        return enderecoService.atualizar(id, endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
