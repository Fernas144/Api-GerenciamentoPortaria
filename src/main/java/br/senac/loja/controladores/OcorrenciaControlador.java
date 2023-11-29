package br.senac.loja.controladores;

import br.senac.loja.entidades.Ocorrencia;
import br.senac.loja.repositorios.OcorrenciaRepositorio;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/ocorrencia")
public class OcorrenciaControlador {
    private OcorrenciaRepositorio ocorrenciaRepositorio;

    public OcorrenciaControlador(OcorrenciaRepositorio ocorrenciaRepositorio) {
        this.ocorrenciaRepositorio = ocorrenciaRepositorio;
    }


    // GET: Obter todos os ocorrencia com paginação
    @GetMapping
    public ResponseEntity<Page<Ocorrencia>> listarOcorrencia(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanhoPagina) {

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        Page<Ocorrencia> funcionarios = ocorrenciaRepositorio.findAll(pageable);

        return ResponseEntity.ok(funcionarios);
    }

    // GET: Obter um único ocorrencia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> obterOcorrenciaPorId(@PathVariable Long id) {
        Optional<Ocorrencia> ocorrencia = ocorrenciaRepositorio.findById(id);
        return ocorrencia.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // POST: Criar um novo ocorrencia
    @PostMapping
    public ResponseEntity<Ocorrencia> criarOcorrencia(@RequestBody @Valid Ocorrencia ocorrencia) {
        Ocorrencia ocorrenciaCriado = ocorrenciaRepositorio.save(ocorrencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorrenciaCriado);
    }

    // PUT: Atualizar um ocorrencia existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Ocorrencia> atualizarOcorrencia(@PathVariable Long id, @RequestBody Ocorrencia ocorrenciaAtualizado) {
        if (!ocorrenciaRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ocorrenciaAtualizado.setId(id);
        Ocorrencia ocorrenciaAtualizadoSalvo = ocorrenciaRepositorio.save(ocorrenciaAtualizado);
        return ResponseEntity.ok(ocorrenciaAtualizadoSalvo);
    }

    // DELETE: Excluir um ocorrencia por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirOcorrencia(@PathVariable Long id) {
        if (!ocorrenciaRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ocorrenciaRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

