
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.out.ColaboradorDTO;
import br.com.logisticadbc.dto.in.ColaboradorUpdateDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.ColaboradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    private final ObjectMapper objectMapper;

    public List<ColaboradorDTO> listar() {
        return colaboradorRepository.findAll()
                .stream()
                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorDTO.class))
                .toList();
    }

    // TODO - fazer senha nao retornar no dto
    public ColaboradorDTO criar(ColaboradorCreateDTO colaboradorCreateDTO) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEntity = objectMapper.convertValue(colaboradorCreateDTO, ColaboradorEntity.class);

            colaboradorEntity.setStatusUsuario(StatusUsuario.ATIVO);

            colaboradorRepository.save(colaboradorEntity);

            return objectMapper.convertValue(colaboradorEntity, ColaboradorDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public ColaboradorDTO editar(Integer idUsuario, ColaboradorUpdateDTO colaboradorUpdateDTO)
            throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEncontrado = buscarPorId(idUsuario);

            colaboradorEncontrado.setNome(colaboradorUpdateDTO.getNome());
            colaboradorEncontrado.setSenha(colaboradorUpdateDTO.getSenha());

            colaboradorRepository.save(colaboradorEncontrado);

            return objectMapper.convertValue(colaboradorEncontrado, ColaboradorDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar (Integer idUsuario) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEncontrado = buscarPorId(idUsuario);
            colaboradorEncontrado.setStatusUsuario(StatusUsuario.INATIVO);

            colaboradorRepository.save(colaboradorEncontrado);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public ColaboradorEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException{
        return colaboradorRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Colaborador não encontrado"));
    }
}