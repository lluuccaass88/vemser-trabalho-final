package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.PostoCreateDTO;
import br.com.logisticadbc.dto.PostoDTO;
import br.com.logisticadbc.entity.Posto;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.PostoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class PostoService {
    private  final PostoRepository postoRepository;
    private final ObjectMapper objectMapper;

    //private final


    public PostoService(PostoRepository postoRepository, ObjectMapper objectMapper) {
        this.postoRepository = postoRepository;
        this.objectMapper = objectMapper;
    }

    public void adicionaPosto(Posto posto) {
        try {
            Posto postoAdicionado = postoRepository.adicionar(posto);
            System.out.println("Dados do posto adicionado: \n " + postoAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO SQL-> " + e.getMessage());
        }
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

    // atualizando um objeto do tipo Colaborador passando o ID e o objeto COLABORADOR
    public void editarPosto(Integer id, Posto posto) {
        try {
            boolean conseguiuEditar = postoRepository.editar(id, posto);
            if (conseguiuEditar) {
//                System.out.println("Posto " + conseguiuEditar + "| com id= "
//                        + id + " editado com sucesso");
                System.out.println("Posto com o id "
                        + id + " editado com sucesso");
            } else {
                System.out.println("Não foi possível editar o posto com o id " + id + ".");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // listando todos os colaboradores
    public void listarPosto() {
        try {
            List<Posto> listar = postoRepository.listar();
            listar.forEach(System.out::println);
        } catch (BancoDeDadosException e ) {
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
