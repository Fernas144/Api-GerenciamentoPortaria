package br.senac.loja.repositorios;

import br.senac.loja.entidades.Ocorrencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface OcorrenciaRepositorio extends JpaRepository<Ocorrencia, Long> {

    List<Ocorrencia> searchByPlaca(String placa);

    Ocorrencia findByPlaca(String placa);

    Ocorrencia findByCarro(String carro);

    @Query("select c from Ocorrencia c where lower(c.placa) like lower(concat(:termo, '%')) or lower(c.carro) like lower(concat(:termo, '%'))")
    List<Ocorrencia> searchByPlacaECarro(@Param("termo") String termoBusca);
}