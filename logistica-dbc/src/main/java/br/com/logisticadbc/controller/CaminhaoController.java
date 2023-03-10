package br.com.logisticadbc.controller;


import br.com.logisticadbc.controller.impl.CaminhaoInterface;
import br.com.logisticadbc.dto.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.CaminhaoDTO;
import br.com.logisticadbc.service.CaminhaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/caminhao") // http://localhost:8080/caminhao
@Validated
@Slf4j
public class CaminhaoController implements CaminhaoInterface {

    private final CaminhaoService caminhaoService;

    @PostMapping
    public ResponseEntity<CaminhaoDTO> adicionar(@Valid @RequestBody CaminhaoCreateDTO caminhao) throws Exception {

        log.info("Recebendo requisição para adicionar um novo caminhão");
//        CaminhaoDTO caminhaoDTO = caminhaoService.adicionar(caminhao);
//        log.info("Caminhão adicionado com sucesso" + caminhaoDTO);
//        return new ResponseEntity<>(caminhaoDTO, HttpStatus.CREATED);

        return null;
    }

    @GetMapping
    public ResponseEntity<List<CaminhaoDTO>> listar() throws Exception {
        log.info("Recebendo requisição para listar todos os caminhões");
//        return new ResponseEntity<>(caminhaoService.listar(), HttpStatus.OK);
        return null;
    }


    @PutMapping("/{id}")
    public ResponseEntity<CaminhaoDTO> editar(@PathVariable Integer id,
                                              @Valid @RequestBody CaminhaoCreateDTO caminhao) throws Exception {
        log.info("Recebendo requisição para atualizar um caminhão");
//        CaminhaoDTO caminhaoDTO = caminhaoService.editar(id, caminhao);
//        log.info("Caminhão atualizado com sucesso" + caminhaoDTO);
//        return new ResponseEntity<>(caminhaoDTO, HttpStatus.OK);
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws Exception {
        log.info("Recebendo requisição para remover um caminhão");
//        caminhaoService.deletar(id);
        log.info("Caminhão removido com sucesso");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/abastecer/{id}")
    public ResponseEntity<CaminhaoDTO> abastecer(@PathVariable Integer id,
                                                 @Valid @RequestBody CaminhaoCreateDTO caminhao, Integer gasolina) throws Exception {
//        log.info("Recebendo requisição para abastecer um caminhão");
//        CaminhaoDTO caminhaoDTO = caminhaoService.abastecerCaminhao(id, caminhao.setGasolina(caminhao.getGasolina())+ gasolina);
//        log.info("Caminhão atualizado com sucesso" + caminhaoDTO);
//        return new ResponseEntity<>(caminhaoDTO, HttpStatus.OK);
//    }
        return null;
    }

    @GetMapping("/caminhoesdisponiveis")
    public ResponseEntity<List<CaminhaoDTO>> listarCaminhoesDisponiveis() throws Exception {
        log.info("Recebendo requisição para listar todos os caminhões disponiveis");
//        return new ResponseEntity<>(caminhaoService.listarCaminhoesLivres(), HttpStatus.OK);
        return null;
    }
}
