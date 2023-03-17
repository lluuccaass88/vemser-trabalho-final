
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.*;
import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CaminhaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class CaminhaoService {

    private final CaminhaoRepository caminhaoRepository;
    private final ColaboradorService colaboradorService;
    private final ObjectMapper objectMapper;


    public List<CaminhaoDTO> listar() {
        return caminhaoRepository.findAll()
                .stream()
                .map(colaborador -> objectMapper.convertValue(colaborador, CaminhaoDTO.class))
                .toList();
    }

    public CaminhaoDTO criar(Integer idColaborador, CaminhaoCreateDTO caminhaoCreateDTO) throws RegraDeNegocioException {
        ColaboradorEntity colaboradorEntity = colaboradorService.buscarPorId(idColaborador);

        CaminhaoEntity caminhaoEntity = objectMapper.convertValue(caminhaoCreateDTO, CaminhaoEntity.class);
        caminhaoEntity.setStatusCaminhao(StatusCaminhao.ESTACIONADO);

        caminhaoEntity.setColaborador(colaboradorEntity);

        caminhaoRepository.save(caminhaoEntity);

        CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoEntity, CaminhaoDTO.class);

        return caminhaoDTO;
    }


}
/*
    public CaminhaoDTO adicionar(CaminhaoCreateDTO caminhao) throws BancoDeDadosException {
        CaminhaoEntity caminhaoEntity = objectMapper.convertValue(caminhao, CaminhaoEntity.class);
        CaminhaoEntity caminhaoSalvo = caminhaoRepository.adicionar(caminhaoEntity);

        return objectMapper.convertValue(caminhaoSalvo, CaminhaoDTO.class);
    }

    public List<CaminhaoDTO> listar() throws BancoDeDadosException {

        return caminhaoRepository.listar().stream()
                .map(caminhao -> objectMapper.convertValue(caminhao, CaminhaoDTO.class))
                .collect(toList());
    }

    public CaminhaoDTO editar(Integer id, CaminhaoCreateDTO caminhao) throws RegraDeNegocioException, BancoDeDadosException {
        CaminhaoEntity caminhaoRecuperado = getCaminhao(id);
        Integer idCaminhao = getCaminhao(id).getIdCaminhao();
        caminhaoRecuperado.setGasolina(caminhao.getGasolina());
        caminhaoRecuperado.setPlaca(caminhao.getPlaca());
        caminhaoRecuperado.setModelo(caminhao.getModelo());
        caminhaoRecuperado.setEmViagem(caminhao.getEmViagem());
        caminhaoRepository.editar(idCaminhao, caminhaoRecuperado);
        CaminhaoDTO dto = objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
        return dto;
    }

    public CaminhaoDTO editar(Integer id) throws Exception { //Fução utilizada para editar o status de caminhção. Chamada no finalizarViagem e criarViagem
        CaminhaoEntity caminhaoRecuperado = getCaminhao(id);

        if (caminhaoRecuperado.getEmViagem() == StatusCaminhao.ESTACIONADO) {
            caminhaoRecuperado.setEmViagem(StatusCaminhao.EM_VIAGEM);
        } else {
            caminhaoRecuperado.setEmViagem(StatusCaminhao.ESTACIONADO);
        }

        caminhaoRepository.editar(id, caminhaoRecuperado);

        return objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
    }

    public void deletar(Integer id) throws Exception {
        CaminhaoEntity caminhaoRecuperado = getCaminhao(id);
        Integer idCaminhao = getCaminhao(id).getIdCaminhao();
        CaminhaoDTO dto = objectMapper.convertValue(caminhaoRepository, CaminhaoDTO.class);

        caminhaoRepository.remover(idCaminhao);
    }

    public List<CaminhaoDTO> listarCaminhoesLivres() throws BancoDeDadosException {

        List<CaminhaoEntity> listar = caminhaoRepository.listar();

        List<CaminhaoEntity> caminhaoDisponivel = listar.stream()
                .filter(elemento -> elemento.getEmViagem().getOpcao().equals(2))
                .toList();

        return caminhaoDisponivel.stream()
                .map(caminhao -> objectMapper.convertValue(caminhao, CaminhaoDTO.class)).toList();
    }

    public CaminhaoEntity getCaminhao(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        return caminhaoRepository.listar().stream()
                .filter(u -> u.getIdCaminhao().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Caminhão não encontrado"));
    }

    public CaminhaoDTO abastecerCaminhao(Integer id, Integer gasolina) throws Exception {
        CaminhaoEntity caminhaoRecuperado = getCaminhao(id);

        Integer totalGasolina = caminhaoRecuperado.getGasolina() + gasolina;
        if ( gasolina < 0){
            throw new RegraDeNegocioException("O caminhão não pode ser abastecido com um número negativo!");
        }
        else if (totalGasolina > 100) {
            throw new RegraDeNegocioException("O caminhão não pode ter mais de 100% de abastecimento do seu tanque");
        }
        else {
            caminhaoRecuperado.setGasolina(totalGasolina);
        }
        caminhaoRepository.editar(id, caminhaoRecuperado);
        return null;
    }

    public CaminhaoDTO buscarPorId(Integer id) throws Exception {
        CaminhaoEntity caminhaoRecuperado = getCaminhao(id);
        return objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
    }

    public ViagemDTO buscarViagem(Integer id) throws Exception {
        CaminhaoEntity caminhaoRecuperado = getCaminhao(id);
        return objectMapper.convertValue(caminhaoRecuperado.getEmViagem(), ViagemDTO.class);
    }
}*/
