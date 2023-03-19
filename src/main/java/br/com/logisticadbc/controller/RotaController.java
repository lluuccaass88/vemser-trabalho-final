package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.RotaControllerDoc;
import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.RotaComPostosDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.RotaService;
import br.com.logisticadbc.service.ValidacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rota")
@Validated
public class RotaController implements RotaControllerDoc {

    private final RotaService rotaService;
    private final ValidacaoService validacaoService;

    @GetMapping
    public ResponseEntity<List<RotaDTO>> listAll() {
        return new ResponseEntity<>(rotaService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<RotaDTO> findById(@RequestParam("idRota") Integer idRota) throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.listarPorId(idRota), HttpStatus.OK);
    }

    @PostMapping("/{idColaborador}")
    public ResponseEntity<RotaDTO> create(@RequestParam("idColaborador") Integer idUsuario,
                                          @Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException {

        validacaoService.validacao(idUsuario, "colaborador");
        return new ResponseEntity<>(rotaService.criar(idUsuario, rotaCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idRota}")
    public ResponseEntity<RotaDTO> update(@RequestParam("idColaborador") Integer idUsuario,
                                          @RequestParam("idRota") Integer idRota,
                                                 @Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException {

        validacaoService.validacao(idUsuario, "colaborador");
        return new ResponseEntity<>(rotaService.editar(idRota, rotaCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idRota}")
    public ResponseEntity<Void> delete(@RequestParam("idColaborador") Integer idUsuario,
                                       @RequestParam("idRota") Integer idRota) throws RegraDeNegocioException {

        validacaoService.validacao(idUsuario, "colaborador");
        rotaService.deletar(idRota);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cadastrar-posto")
    public ResponseEntity<Void> linkEntities(@RequestParam("idColaborador") Integer idUsuario,
                                             @RequestParam("idRota") Integer idRota,
                                             @RequestParam("idPosto") Integer idPosto)
            throws RegraDeNegocioException {

        validacaoService.validacao(idUsuario, "colaborador");
        rotaService.cadastrarPosto(idRota, idPosto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar-postos-cadastrados")
    public ResponseEntity<RotaComPostosDTO> listLink(@RequestParam("idRota") Integer idRota)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.listarPostosCadastrados(idRota), HttpStatus.OK);
    }

    @GetMapping("/listar-por-colaborador")
    public ResponseEntity<List<RotaDTO>> listByIdUser(@RequestParam("idColaborador") Integer idColaborador)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.listarPorIdColaborador(idColaborador), HttpStatus.OK);
    }
}