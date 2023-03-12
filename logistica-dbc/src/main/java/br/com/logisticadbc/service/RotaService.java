package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.RotaCreateDTO;
import br.com.logisticadbc.dto.RotaDTO;
import br.com.logisticadbc.entity.Posto;
import br.com.logisticadbc.entity.Rota;
import br.com.logisticadbc.entity.Viagem;
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

    private  final RotaRepository rotaRepository;
    private final ObjectMapper objectMapper;

    public RotaDTO adicionaRota(RotaCreateDTO rota) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Rota rotaEntity = objectMapper.convertValue(rota, Rota.class);

            Rota rotaAdicionada = rotaRepository.adicionar(rotaEntity);
            adicionaRotaXPosto(rotaEntity);

            return objectMapper.convertValue(rotaAdicionada, RotaDTO.class);
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados ao adicionar rota");
        } catch (Exception e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public void adicionaRotaXPosto(Rota rota) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            for(int i = 0; i < rota.getListaIdPostoCadastrado().size(); i++){
                rotaRepository.adicionarPostoXRota(rota.getIdRota(), rota.getListaIdPostoCadastrado().get(i));
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados ao adicionar o relacionamento de posto e rota");
        }
    }

    public List<RotaDTO> listarRotas() throws RegraDeNegocioException {
        try {
            return rotaRepository.listar().stream()
                    .map(rota -> objectMapper.convertValue(rota, RotaDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e ) {
            throw new RegraDeNegocioException("Erro no banco de dados ao listar rotas");
        }catch (Exception e){
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public RotaDTO editarRota(Integer id, RotaCreateDTO rota) throws RegraDeNegocioException {
        try {
            Rota rotaEntity = objectMapper.convertValue(rota, Rota.class);
            Rota rotaEditada = rotaRepository.editar(id, rotaEntity);
            return objectMapper.convertValue(rotaEditada, RotaDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados ao editar posto - " + e.getMessage());
        }
    }

    public boolean removerRota(Integer id) throws RegraDeNegocioException {
        try {
            boolean conseguiuRemoverRelacionamento = rotaRepository.removerPostoXRota(id);
            return rotaRepository.remover(id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro no banco de dados ao remover rota - " + e.getMessage());
        }
    }

    public Rota getRota(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        return rotaRepository.listar().stream()
                .filter(u -> u.getIdRota() == id)
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Viagem não encontrada"));
    }


















//

//

//
//    public Rota retornaPorId(int index){
//        try {
//            Rota rota = rotaRepository.buscaPorId(index);
//            return rota;
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
