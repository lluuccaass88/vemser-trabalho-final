package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.PostoEntity;
import br.com.logisticadbc.entity.RotaEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@AllArgsConstructor
public class ColaboradorCompletoListasDTO {

    private Set<CaminhaoEntity> caminhoes;

//    private Set<RotaEntity> rotas;
//    private Set<PostoEntity> postos;

}
