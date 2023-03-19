package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.ViagemEntity;
import br.com.logisticadbc.entity.enums.StatusViagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ViagemRepository extends JpaRepository<ViagemEntity, Integer> {

    // TODO - RELATORIO DE VIAGENS COM CAMPOS RELACIONADOS E PAGINAÇÃO

    // TODO - LISTAR VIAGENS POR STATUS DA VIAGEM (FINALIZADA/EM_ANDAMENTO)
    @Query( "   SELECT v " +
            "     FROM VIAGEM v " +
            "    WHERE v.statusViagem = ?1 " +
            " ORDER BY v.dataInicio ASC" )
    Page<ViagemEntity> findByStatusViagemEqualsOrderByDataInicioAsc(Pageable pageable, StatusViagem statusViagem); //LocalDateTime dataInicio caso necessário
}

