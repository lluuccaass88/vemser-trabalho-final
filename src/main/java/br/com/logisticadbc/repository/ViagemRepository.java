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

    @Query( "   SELECT v " +
            "     FROM VIAGEM v " +
            "    WHERE v.statusViagem = ?1 " +
            " ORDER BY v.dataInicio ASC" )
    Page<ViagemEntity> findByStatusViagemEqualsOrderByDataInicioAsc(Pageable pageable, StatusViagem statusViagem);
}

