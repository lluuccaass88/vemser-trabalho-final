package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.PostoCreateDTO;
import br.com.logisticadbc.dto.PostoDTO;
import br.com.logisticadbc.entity.Posto;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.PostoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostoService {
    private  final PostoRepository postoRepository;
    private final ObjectMapper objectMapper;

    public PostoDTO adicionaPosto(PostoCreateDTO posto) throws RegraDeNegocioException {
        try {
            Posto postoEntity = objectMapper.convertValue(posto, Posto.class);

            Posto postoSalvo = postoRepository.adicionar(postoEntity);

            return  objectMapper.convertValue(postoSalvo, PostoDTO.class);
        }catch (BancoDeDadosException e) {
             e.printStackTrace();
            throw new RegraDeNegocioException("Erro no banco de dados ao adicionar posto");
        }
    }

    public List<PostoDTO> listarPosto() throws RegraDeNegocioException {
        try {
            return postoRepository.listar().stream()
                    .map(posto -> objectMapper.convertValue(posto, PostoDTO.class))
                    .collect(Collectors.toList());
        }catch (BancoDeDadosException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro no banco de dados ao listar posto");
        }
    }

    public boolean editarPosto(Integer id, PostoCreateDTO posto) throws RegraDeNegocioException {
        try {
            Posto postoEntity = objectMapper.convertValue(posto, Posto.class);

            return postoRepository.editar(id, postoEntity);
        }catch (BancoDeDadosException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro no banco de dados ao editar posto");
        }
    }

    public void removerPosto(Integer id) throws RegraDeNegocioException {
        try {
            boolean conseguiuRemoverRelacionamento = postoRepository.removerPostoXRota(id);
            boolean conseguiuRemover = postoRepository.remover(id);
            if (conseguiuRemover && conseguiuRemoverRelacionamento) {

            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro no banco de dados ao remover posto");
        }
    }

    public Posto buscarPostoId(int id) {
        try {
            Posto retornoBusca = postoRepository.buscarPorId(id);
            if(retornoBusca == null){
                System.out.println("Não foi encontrar o posto com o id " + id);
            }else{
                System.out.println("Posto: \n "+ retornoBusca);
                return retornoBusca;
            }
        } catch (BancoDeDadosException e ) {
            e.printStackTrace();
        }
        return null;
    }

}
