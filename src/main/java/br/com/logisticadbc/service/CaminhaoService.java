package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.CaminhaoDTO;
import br.com.logisticadbc.dto.ViagemDTO;
import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.EmViagem;
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
    private final ObjectMapper objectMapper;

    public CaminhaoDTO adicionar(CaminhaoCreateDTO caminhao) throws BancoDeDadosException {
        Caminhao caminhaoEntity = objectMapper.convertValue(caminhao, Caminhao.class);
        Caminhao caminhaoSalvo = caminhaoRepository.adicionar(caminhaoEntity);

        return objectMapper.convertValue(caminhaoSalvo, CaminhaoDTO.class);
    }

    public List<CaminhaoDTO> listar() throws BancoDeDadosException {

        return caminhaoRepository.listar().stream()
                .map(caminhao -> objectMapper.convertValue(caminhao, CaminhaoDTO.class))
                .collect(toList());
    }

    public CaminhaoDTO editar(Integer id, CaminhaoCreateDTO caminhao) throws RegraDeNegocioException, BancoDeDadosException {
        Caminhao caminhaoRecuperado = getCaminhao(id);
        Integer idCaminhao = getCaminhao(id).getIdCaminhao();
        caminhaoRecuperado.setGasolina(caminhao.getGasolina());
        caminhaoRecuperado.setPlaca(caminhao.getPlaca());
        caminhaoRecuperado.setModelo(caminhao.getModelo());
        caminhaoRecuperado.setEmViagem(caminhao.getEmViagem());
        caminhaoRepository.editar(idCaminhao, caminhaoRecuperado);
        CaminhaoDTO dto = objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
        return dto;
    }

    public CaminhaoDTO editar(Integer id) throws Exception { //Fu????o utilizada para editar o status de caminh????o. Chamada no finalizarViagem e criarViagem
        Caminhao caminhaoRecuperado = getCaminhao(id);

        if (caminhaoRecuperado.getEmViagem() == EmViagem.ESTACIONADO) {
            caminhaoRecuperado.setEmViagem(EmViagem.EM_VIAGEM);
        } else {
            caminhaoRecuperado.setEmViagem(EmViagem.ESTACIONADO);
        }

        caminhaoRepository.editar(id, caminhaoRecuperado);

        return objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
    }

    public void deletar(Integer id) throws Exception {
        Caminhao caminhaoRecuperado = getCaminhao(id);
        Integer idCaminhao = getCaminhao(id).getIdCaminhao();
        CaminhaoDTO dto = objectMapper.convertValue(caminhaoRepository, CaminhaoDTO.class);

        caminhaoRepository.remover(idCaminhao);
    }

    public List<CaminhaoDTO> listarCaminhoesLivres() throws BancoDeDadosException {

        List<Caminhao> listar = caminhaoRepository.listar();

        List<Caminhao> caminhaoDisponivel = listar.stream()
                .filter(elemento -> elemento.getEmViagem().getOpcao().equals(2))
                .toList();

        return caminhaoDisponivel.stream()
                .map(caminhao -> objectMapper.convertValue(caminhao, CaminhaoDTO.class)).toList();
    }

    public Caminhao getCaminhao(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        return caminhaoRepository.listar().stream()
                .filter(u -> u.getIdCaminhao().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Caminh??o n??o encontrado"));
    }

    public CaminhaoDTO abastecerCaminhao(Integer id, Integer gasolina) throws Exception {
        Caminhao caminhaoRecuperado = getCaminhao(id);

        Integer totalGasolina = caminhaoRecuperado.getGasolina() + gasolina;
        if ( gasolina < 0){
            throw new RegraDeNegocioException("O caminh??o n??o pode ser abastecido com um n??mero negativo!");
        }
        else if (totalGasolina > 100) {
            throw new RegraDeNegocioException("O caminh??o n??o pode ter mais de 100% de abastecimento do seu tanque");
        }
        else {
            caminhaoRecuperado.setGasolina(totalGasolina);
        }
        caminhaoRepository.editar(id, caminhaoRecuperado);
        return null;
    }

    public CaminhaoDTO buscarPorId(Integer id) throws Exception {
        Caminhao caminhaoRecuperado = getCaminhao(id);
        return objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
    }

    public ViagemDTO buscarViagem(Integer id) throws Exception {
        Caminhao caminhaoRecuperado = getCaminhao(id);
        return objectMapper.convertValue(caminhaoRecuperado.getEmViagem(), ViagemDTO.class);
    }
}