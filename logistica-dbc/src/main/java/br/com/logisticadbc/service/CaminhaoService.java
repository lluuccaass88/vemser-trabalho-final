package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.*;
import br.com.logisticadbc.entity.Caminhao;
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

    public CaminhaoDTO adicionar(CaminhaoCreateDTO caminhao) throws BancoDeDadosException{
        Caminhao caminhaoEntity = objectMapper.convertValue(caminhao, Caminhao.class);

        Caminhao caminhaoSalvo = caminhaoRepository.adicionar(caminhaoEntity);

        return objectMapper.convertValue(caminhaoSalvo, CaminhaoDTO.class);
    }

    public List<CaminhaoDTO> listar() throws BancoDeDadosException {

        return caminhaoRepository.listar().stream()
                .map(caminhao -> objectMapper.convertValue(caminhao, CaminhaoDTO.class))
                .collect(toList());
    }

    public CaminhaoDTO editar(Integer id, CaminhaoCreateDTO caminhao) throws BancoDeDadosException {
        Caminhao caminhaoEntity = objectMapper.convertValue(caminhao, Caminhao.class);
        return caminhaoRepository.editar(id, caminhaoEntity);
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
                .filter(elemento -> elemento.getEmViagem().getOpcao().equals(1))
                .toList();

        return caminhaoDisponivel.stream()
                .map(caminhao -> objectMapper.convertValue(caminhao, CaminhaoDTO.class)).toList();
    }

    public Caminhao getCaminhao(Integer id) throws Exception {
        return caminhaoRepository.listar().stream()
                .filter(u -> u.getIdCaminhao().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Caminhão não encontrado"));
    }

    // abastercer o caminhão somente em postos e se tiver em rota, se nao tiver em rota nao
    // pode abastercer em qualquer posto independente de ser ou nao posto cadastrado.
    public void abastecerCaminhao(int index, int gasolina) {
        try {
            Caminhao caminhao = caminhaoRepository.buscaPorId(index);
            Integer totalGasolinaEmTanque = caminhao.getGasolina() + gasolina;
            System.out.println(totalGasolinaEmTanque);
            if (caminhao.getEmViagem().getOpcao().equals(1)) {
                if (caminhao.getGasolina() + gasolina <= 100) {
                    caminhao = caminhaoRepository.abastecerCaminhao(index, totalGasolinaEmTanque);
                    caminhao.setGasolina(totalGasolinaEmTanque);
                    System.out.println("Caminhão abastecido com sucesso");
                } else {
                    System.out.println("Caminhão com tanque cheio, não é necessário abastecer");
                }
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }
}
