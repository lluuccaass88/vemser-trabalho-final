package br.com.logisticadbc.repository;

import br.com.logisticadbc.dto.out.ColaboradorCompletoDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColaboradorRepository extends JpaRepository<ColaboradorEntity, Integer> {
    @Query( " SELECT new br.com.logisticadbc.dto.out.ColaboradorCompletoDTO(" +
            "   c.idUsuario, " +
            "   c.nome, " +
            "   c.usuario, " +
            "   c.email, " +
            "   c.statusUsuario, " +
            "   c.cpf, " +
            "   cam.idCaminhao, " +
            "   cam.modelo, " +
            "   cam.placa, " +
            "   cam.nivelCombustivel, " +
            "   cam.statusCaminhao, " +
            "   r.idRota, " +
            "   r.descricao, " +
            "   r.localPartida, " +
            "   r.localDestino, " +
            "   p.idPosto, " +
            "   p.nome, " +
            "   p.valorCombustivel" +
            " ) " +
            "   From COLABORADOR c " +
            "   left join c.caminhoes cam " +
            "   left join c.rotas r " +
            "   left join c.postos p "
    )
    List<ColaboradorCompletoDTO> relatorio();
}