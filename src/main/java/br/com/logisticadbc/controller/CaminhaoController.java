package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.CaminhaoControllerDoc;
import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
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
public class CaminhaoController implements CaminhaoControllerDoc {

    private final CaminhaoService caminhaoService;
    @GetMapping
    public ResponseEntity<List<CaminhaoDTO>> listAll() {
        return new ResponseEntity<>(caminhaoService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CaminhaoDTO> create(@RequestParam("idColaborador") Integer idColaborador,
                                              @Valid @RequestBody CaminhaoCreateDTO caminhaoCreateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(caminhaoService.criar(idColaborador, caminhaoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/abastecer")
    public ResponseEntity<CaminhaoDTO> update(@RequestParam("idCaminhao") Integer idCaminhao,
                                              @RequestParam("Quantidade de gasolina") Integer gasolina)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(caminhaoService.abastecer(idCaminhao, gasolina), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<CaminhaoDTO> findById(@RequestParam("idCaminhao") Integer idCaminhao) throws Exception {
        return new ResponseEntity<>(caminhaoService.listarPorId(idCaminhao), HttpStatus.OK);
    }

    @GetMapping("/listar-disponiveis")
    public ResponseEntity<List<CaminhaoDTO>> listAllAvaiablesTrucks() {
        return new ResponseEntity<>(caminhaoService.listarCaminhoesLivres(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idCaminhao") Integer id) throws Exception {
        caminhaoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}