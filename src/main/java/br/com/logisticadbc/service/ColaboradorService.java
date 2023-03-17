
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.ColaboradorDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.ColaboradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColaboradorService {
    private final ColaboradorRepository colaboradorRepository;
    private final ObjectMapper objectMapper;

/*    public ColaboradorEntity getColaborador(Integer idColaborador) throws RegraDeNegocioException{
        return colaboradorRepository.findById(idColaborador)
                .orElseThrow(() -> new RegraDeNegocioException("Colaborador não encontrado"));
    }

    public List<ColaboradorDTO> listarColaboradores() {
        return colaboradorRepository.findAll()
                .stream()
                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorDTO.class))
                .toList();
    }*/
}