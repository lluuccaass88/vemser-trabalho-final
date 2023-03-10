package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.PostoCreateDTO;
import br.com.logisticadbc.dto.PostoDTO;
import br.com.logisticadbc.entity.Posto;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.repository.PostoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostoService {
    private  final PostoRepository postoRepository;
    private final ObjectMapper objectMapper;
//
//    public PostoService(PostoRepository postoRepository, ObjectMapper objectMapper) {
//        this.postoRepository = postoRepository;
//        this.objectMapper = objectMapper;
//    }


    public PostoDTO adicionaPosto(PostoCreateDTO posto) throws BancoDeDadosException {

        Posto postoEntity = objectMapper.convertValue(posto, Posto.class);

        Posto postoSalvo = postoRepository.adicionar(postoEntity);

        return  objectMapper.convertValue(postoSalvo, PostoDTO.class);
    }

    public List<PostoDTO> listarPosto() throws BancoDeDadosException {

        return postoRepository.listar().stream()
                .map(posto -> objectMapper.convertValue(posto, PostoDTO.class))
                .collect(Collectors.toList());
    }

        public boolean editarPosto(Integer id, PostoCreateDTO posto) throws BancoDeDadosException {
            Posto postoEntity = objectMapper.convertValue(posto, Posto.class);

            return postoRepository.editar(id, postoEntity);
        }

    public void removerPosto(Integer id) {
        try {
            boolean conseguiuRemoverRelacionamento = postoRepository.removerPostoXRota(id);
            boolean conseguiuRemover = postoRepository.remover(id);
            if (conseguiuRemover && conseguiuRemoverRelacionamento) {
                System.out.println("Posto romovido: " + conseguiuRemover + "| Posto com o id= "
                        + id + " removido com sucesso");
            } else {
                System.out.println("Não foi possível remover o posto com o id " + id + ".");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
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
