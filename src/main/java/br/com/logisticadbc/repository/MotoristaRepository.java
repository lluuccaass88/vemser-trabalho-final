package br.com.logisticadbc.repository;

import br.com.logisticadbc.dto.out.MotoristaCompletoDTO;
import br.com.logisticadbc.entity.MotoristaEntity;
import br.com.logisticadbc.entity.enums.StatusMotorista;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoristaRepository extends JpaRepository<MotoristaEntity, Integer> {

    /**
     *    @Query("SELECT p " +
     *             " FROM PESSOA p " +
     *             "WHERE p.dataNascimento >= ?1 " +
     *             "OR ?1 IS NULL " +
     *             "ORDER BY p.nome ASC")
     *     Page<PessoaEntity> findByDataNascimentoIsGreaterThanEqualOrderByNomeAsc(Pageable pageable, LocalDate data);
     */

    @Query( "   SELECT m " +
            "     FROM MOTORISTA m " +
            "    WHERE m.statusMotorista = ?1 " +
            "      AND m.statusUsuario = ?2 " +
            " ORDER BY m.nome ASC" )
    Page<MotoristaEntity> findByStatusMotoristaEqualsAndStatusUsuarioEqualsOrderByNomeAsc (Pageable pageable,
                                                                                           StatusMotorista statusMotorista,
                                                                                           StatusUsuario statusUsuario);

    @Query( " SELECT DISTINCT new br.com.logisticadbc.dto.out.MotoristaCompletoDTO(" +
            "   m.idUsuario, " +
            "   m.nome, " +
            "   m.usuario, " +
            "   m.email, " +
            "   m.statusUsuario, " +
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
    List<MotoristaCompletoDTO> relatorio(); //Mudar para paginação

}
