package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.ViagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViagemRepository extends JpaRepository<ViagemEntity, Integer> {

    // TODO - RELATORIO DE VIAGENS COM CAMPOS RELACIONADOS E PAGINAÇÃO

    // TODO - LISTAR VIAGENS POR STATUS DA VIAGEM (FINALIZADA/EM_ANDAMENTO)
}

