package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.PostoDTO;
import br.com.logisticadbc.dto.RotaCreateDTO;
import br.com.logisticadbc.dto.RotaDTO;
import br.com.logisticadbc.entity.Posto;
import br.com.logisticadbc.entity.Rota;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.repository.PostoRepository;
import br.com.logisticadbc.repository.RotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RotaService {

    private  final RotaRepository rotaRepository;
    private final ObjectMapper objectMapper;

    //private final


    public RotaService(RotaRepository rotaRepository, ObjectMapper objectMapper) {
        this.rotaRepository = rotaRepository;
        this.objectMapper = objectMapper;
    }

    public RotaDTO adicionaRota(int idPosto, Rota rota) {
        Rota rotaAdicionado = null;
        try {
            rotaAdicionado = rotaRepository.adicionar(rota);
            adicionaRotaXPosto(rota);
            System.out.println("Dados da rota adicionada: \n" + rotaAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO SQL-> " + e.getMessage());
        }

        return objectMapper.convertValue(rotaAdicionado, RotaDTO.class);
    }

    // adicionar Rota_X_Posto conforme tabela de relacionamento no banco de dados
    public void adicionaRotaXPosto(Rota rota) {
        try {
            for(int i = 0; i < rota.getListaPostoCadastrado().size(); i++){
                Rota rotaAdicionado = rotaRepository.adicionarPostoXRota(rota, i);
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO SQL-> " + e.getMessage());
        }
    }

    // removendo um obejto do tipo Colaborador passando o ID
    public void removerRota(Integer id) {
        try {

            boolean conseguiuRemoverRelacionamento = rotaRepository.removerPostoXRota(id);
            boolean conseguiuRemover = rotaRepository.remover(id);

            if (conseguiuRemover && conseguiuRemoverRelacionamento) {
                System.out.println("Rota removida: " + conseguiuRemover + "| Rota com o id= "
                        + id + " removido com sucesso");
            } else {
                System.out.println("Não foi possível remover o " + id + " do rota");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // atualizando um objeto do tipo Colaborador passando o ID e o objeto COLABORADOR
    public void editarRota(Integer id, Rota rota) {
        try {
            boolean conseguiuEditar = rotaRepository.editar(id, rota);
            if (conseguiuEditar) {
                System.out.println("Rota: " + conseguiuEditar + "| com id= "
                        + id + " editado com sucesso");
            } else {
                System.out.println("Não foi possível editar o " + id + " da rota");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // listando todos os colaboradores
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

    public Rota retornaPorId(int index){
        try {
            Rota rota = rotaRepository.buscaPorId(index);
            return rota;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }
}
