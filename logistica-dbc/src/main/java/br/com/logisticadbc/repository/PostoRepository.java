package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.Posto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
public class PostoRepository{


//    public ContatoRepository() { Construtor
//
//    }

    public Posto create(Posto posto) {
        log.info("Repository criar posto");
        return posto;
    }

    public List<Posto> list() {
        log.info("Repository listar posto");
        List<Posto> listaPosto = null;
        return listaPosto;
    }

    public List<Posto> listByIdPessoa(int idPosto)throws Exception {
        log.info("Repository listar posto por id");
        List<Posto> listaPosto = null;
        return listaPosto;

//        return listaContato.stream()
//                .filter(contato -> contato.getIdPessoa() == idPessoa)
//                .collect(Collectors.toList());
    }

    public void delete(Posto posto) {
        log.info("Repository remove posto");
    }
}
