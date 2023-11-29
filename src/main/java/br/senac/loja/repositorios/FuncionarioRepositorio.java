package br.senac.loja.repositorios;

import br.senac.loja.entidades.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepositorio
        extends JpaRepository<Funcionario, Long> {

    List<Funcionario> searchByNome(String nome);

    Funcionario findByNome(String nome);

    Funcionario findByDescricao(String descricao);

    @Query("select c from Funcionario c where lower(c.nome) like lower(concat(:termo, '%')) or lower(c.descricao) like lower(concat(:termo, '%'))")
    List<Funcionario> searchByNomeEDescricao(@Param("termo") String termoBusca);
}
