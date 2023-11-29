package br.senac.loja.controladores;

import br.senac.loja.entidades.Veiculo;
import br.senac.loja.repositorios.VeiculoRepositorio;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/veiculo")
public class VeiculoControlador {
    private VeiculoRepositorio veiculoRepositorio;

    public VeiculoControlador(VeiculoRepositorio veiculoRepositorio) {
        this.veiculoRepositorio = veiculoRepositorio;
    }


    // GET: Obter todos os veiculos com paginação
    @GetMapping
    public ResponseEntity<Page<Veiculo>> listarVeiculo(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanhoPagina) {

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        Page<Veiculo> veiculos = veiculoRepositorio.findAll(pageable);

        return ResponseEntity.ok(veiculos);
    }

    // GET: Obter um único veiculo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> obterVeiculoPorId(@PathVariable Long id) {
        Optional<Veiculo> produto = veiculoRepositorio.findById(id);
        return produto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // POST: Cadastrar um novo veículo
    @PostMapping
    public ResponseEntity<Veiculo> criarVeiculo(@RequestBody @Valid Veiculo produto) {
        Veiculo veiculoCriado = veiculoRepositorio.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoCriado);
    }

    // PUT: Atualizar um veiculo existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculoAtualizado) {
        if (!veiculoRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        veiculoAtualizado.setId(id);
        Veiculo veiculoAtualizadoSalvo = veiculoRepositorio.save(veiculoAtualizado);
        return ResponseEntity.ok(veiculoAtualizadoSalvo);
    }

    // DELETE: Excluir um veiculo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVeiculo(@PathVariable Long id) {
        if (!veiculoRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        veiculoRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
