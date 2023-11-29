package br.senac.loja.repositorios;
import br.senac.loja.entidades.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepositorio
          extends JpaRepository<Veiculo, Long>  {

        List<Veiculo> searchByPlaca(String placa);

        Veiculo findByPlaca(String placa);

        Veiculo findByCor(String cor);

        @Query("select c from Veiculo c where lower(c.placa) like lower(concat(:termo, '%')) or lower(c.cor) like lower(concat(:termo, '%'))")
        List<Veiculo> searchByPlacaECor(@Param("termo") String termoBusca);
    }
