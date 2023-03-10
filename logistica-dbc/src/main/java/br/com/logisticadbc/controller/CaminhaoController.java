package br.com.logisticadbc.controller;


import br.com.logisticadbc.controller.impl.CaminhaoInterface;
import br.com.logisticadbc.dto.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.CaminhaoDTO;
import br.com.logisticadbc.service.CaminhaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/caminhao") // http://localhost:8080/caminhao
@Validated
@Slf4j
public class CaminhaoController implements CaminhaoInterface {

    private final CaminhaoService caminhaoService;

    public CaminhaoController(CaminhaoService caminhaoService) {
        this.caminhaoService = caminhaoService;
    }

    @PostMapping
    public ResponseEntity<CaminhaoDTO> adicionar(@Valid @RequestBody CaminhaoCreateDTO caminhao) throws Exception {

        log.info("Recebendo requisição para adicionar um novo caminhão");
        CaminhaoDTO caminhaoDTO = caminhaoService.adicionar(caminhao);
        log.info("Caminhão adicionado com sucesso" + caminhaoDTO);
        return new ResponseEntity<>(caminhaoDTO, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<CaminhaoDTO>> listar() throws Exception {
        log.info("Recebendo requisição para listar todos os caminhões");
        return new ResponseEntity<>(caminhaoService.listar(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaminhaoDTO> editar(@PathVariable Integer id,
                                             @Valid @RequestBody CaminhaoCreateDTO caminhao) throws Exception {
        log.info("Recebendo requisição para atualizar um caminhão");
        CaminhaoDTO caminhaoDTO = caminhaoService.editar(id, caminhao);
        log.info("Caminhão atualizado com sucesso" + caminhaoDTO);
        return new ResponseEntity<>(caminhaoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws Exception {
        log.info("Recebendo requisição para remover um caminhão");
        caminhaoService.deletar(id);
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
}
