package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.RotaCreateDTO;
import br.com.logisticadbc.dto.RotaDTO;
import br.com.logisticadbc.entity.Posto;
import br.com.logisticadbc.entity.Rota;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
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
    private final PostoService postoService;


    public RotaDTO adicionaRota(RotaCreateDTO rota) throws BancoDeDadosException {
        try {
            Rota rotaEntity = objectMapper.convertValue(rota, Rota.class);

            Rota rotaAdicionada = rotaRepository.adicionar(rotaEntity);
            adicionaRotaXPosto(rotaEntity);

            return objectMapper.convertValue(rotaAdicionada, RotaDTO.class);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new BancoDeDadosException("Erro de banco de dados - adicionar rotas: " + e);
        }
        return null;
    }

    public void adicionaRotaXPosto(Rota rota) {
        try {
            for(int i = 0; i < rota.getListaIdPostoCadastrado().size(); i++){
                rotaRepository.adicionarPostoXRota(rota.getIdRota(), rota.getListaIdPostoCadastrado().get(i));
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO SQL-> " + e.getMessage());
        }
    }

    public List<RotaDTO> listarRotas() {
        try {
            return rotaRepository.listar().stream()
                    .map(rota -> objectMapper.convertValue(rota, RotaDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e ) {
            e.printStackTrace();
        }

        return null;
    }

    public RotaDTO editarRota(Integer id, RotaCreateDTO rota) {
        try {
            Rota rotaEntity = objectMapper.convertValue(rota, Rota.class);
            Rota rotaEditada = rotaRepository.editar(id, rotaEntity);
            return objectMapper.convertValue(rotaEditada, RotaDTO.class);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removerRota(Integer id) {
        try {
            boolean conseguiuRemoverRelacionamento = rotaRepository.removerPostoXRota(id);
            return rotaRepository.remover(id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return false;
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
