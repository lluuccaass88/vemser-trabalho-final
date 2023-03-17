
package br.com.logisticadbc.controller;


import br.com.logisticadbc.controller.impl.ICaminhaoControllerDoc;
import br.com.logisticadbc.dto.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.CaminhaoDTO;
import br.com.logisticadbc.dto.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.ColaboradorDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
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
public class CaminhaoController {//implements ICaminhaoControllerDoc {

    private final CaminhaoService caminhaoService;

    @GetMapping
    public ResponseEntity<List<CaminhaoDTO>> listAll() {
        return new ResponseEntity<>(caminhaoService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CaminhaoDTO> create (@RequestParam ("idColaborador") Integer idColaborador, @Valid @RequestBody CaminhaoCreateDTO caminhaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(caminhaoService.criar(idColaborador, caminhaoCreateDTO), HttpStatus.CREATED);
    }
}




























/*
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

    @PutMapping("/abastecer/{id}/")
    public ResponseEntity<CaminhaoDTO> abastecer(@PathVariable Integer id, Integer gasolina) throws Exception {
        CaminhaoDTO caminhaoDTO = caminhaoService.abastecerCaminhao(id,gasolina);
        return new ResponseEntity<>(caminhaoDTO, HttpStatus.OK);
    }

    @GetMapping("/caminhoesdisponiveis")
    public ResponseEntity<List<CaminhaoDTO>> listarCaminhoesDisponiveis() throws Exception {
        log.info("Recebendo requisição para listar todos os caminhões disponiveis");
        return new ResponseEntity<>(caminhaoService.listarCaminhoesLivres(), HttpStatus.OK);
    }
}
*/
