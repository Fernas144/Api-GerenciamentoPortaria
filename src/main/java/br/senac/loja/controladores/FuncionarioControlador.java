package br.senac.loja.controladores;

import br.senac.loja.entidades.Funcionario;
import br.senac.loja.repositorios.FuncionarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/funcionario")
public class FuncionarioControlador {

    private FuncionarioRepositorio funcionarioRepositorio;

    public FuncionarioControlador(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }


    // GET: Obter todos os funcionario com paginação
    @GetMapping
    public ResponseEntity<Page<Funcionario>> listarFuncionario(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanhoPagina) {

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        Page<Funcionario> funcionarios = funcionarioRepositorio.findAll(pageable);

        return ResponseEntity.ok(funcionarios);
    }

    // GET: Obter um único funcionario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> obterFuncionarioPorId(@PathVariable Long id) {
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        return funcionario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // POST: Criar um novo funcionario
    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody @Valid Funcionario funcionario) {
        Funcionario funcionarioCriado = funcionarioRepositorio.save(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioCriado);
    }

    // PUT: Atualizar um funcionario existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioAtualizado) {
        if (!funcionarioRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        funcionarioAtualizado.setId(id);
        Funcionario funcionarioAtualizadoSalvo = funcionarioRepositorio.save(funcionarioAtualizado);
        return ResponseEntity.ok(funcionarioAtualizadoSalvo);
    }

    // DELETE: Excluir um funcionario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Long id) {
        if (!funcionarioRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        funcionarioRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
