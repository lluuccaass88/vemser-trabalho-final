package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.RotaCreateDTO;
import br.com.logisticadbc.dto.RotaDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.RotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RotaService {

    private final RotaRepository rotaRepository;
    private final ObjectMapper objectMapper;
    private final ColaboradorService colaboradorService;

    /*public RotaDTO adicionaRota(Integer idColaborador, RotaCreateDTO rota) throws RegraDeNegocioException, BancoDeDadosException {

        ColaboradorEntity colaboradorEntity = colaboradorService.getColaborador(idColaborador);
        colaboradorEntity.setIdColabolador(idColaborador);

        RotaEntity rotaEntity = objectMapper.convertValue(rota, RotaEntity.class);
        rotaEntity.setColaborador(colaboradorService.getColaborador(idColaborador));
        rotaEntity = rotaRepository.save(rotaEntity);

        RotaDTO rotaDTO = objectMapper.convertValue(rotaEntity, RotaDTO.class);
        return rotaDTO;
    }*/


    public List<RotaDTO> listarRotas() {
        return rotaRepository.findAll()
                .stream()
                .map(rota -> objectMapper.convertValue(rota, RotaDTO.class))
                .toList();
    }
}