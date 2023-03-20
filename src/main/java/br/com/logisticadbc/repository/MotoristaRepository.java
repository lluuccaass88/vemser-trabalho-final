package br.com.logisticadbc.repository;

import br.com.logisticadbc.dto.out.MotoristaCompletoDTO;
import br.com.logisticadbc.entity.MotoristaEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusMotorista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<MotoristaEntity, Integer> {

    @Query( "   SELECT m " +
            "     FROM MOTORISTA m " +
            "    WHERE m.statusMotorista = ?1 " +
            "      AND m.status = ?2 " +
            " ORDER BY m.nome ASC" )
    Page<MotoristaEntity> findByStatusMotoristaEqualsAndStatusEqualsOrderByNomeAsc (Pageable pageable,
                                                                                    StatusMotorista statusMotorista,
                                                                                    StatusGeral statusUsuario);

    @Query( " SELECT DISTINCT new br.com.logisticadbc.dto.out.MotoristaCompletoDTO(" +
            "   m.idUsuario, " +
            "   m.nome, " +
            "   m.usuario, " +
            "   m.email, " +
            "   m.status, " +
            "   m.cnh, " +
            "   m.statusMotorista, " +

            "   v.idViagem, " +
            "   v.descricao, " +
            "   v.dataInicio, " +
            "   v.dataFim, " +
            "   v.statusViagem " +
            ")" +
            "   From MOTORISTA m " +
            "   left join m.viagens v"
    )
    Page<MotoristaCompletoDTO> relatorio(Pageable pageable); //Mudar para paginação

}





