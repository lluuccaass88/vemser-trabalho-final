package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.PostoControllerDoc;
import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.PostoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/posto")
@Validated
public class PostoController implements PostoControllerDoc {
    private final PostoService postoService;

    @GetMapping
    public ResponseEntity<List<PostoDTO>> listAll(){
        return new ResponseEntity<>(postoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<PostoDTO> findById(@RequestParam("idPosto") Integer idPosto) throws RegraDeNegocioException {
        return new ResponseEntity<>(postoService.listarPorId(idPosto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostoDTO> create(@RequestParam("idColaborador") Integer idColaborador,
                                           @Valid @RequestBody PostoCreateDTO postoCreateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(postoService.criar(idColaborador, postoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PostoDTO> update(@RequestParam("idColaborador") Integer idColaborador,
                                           @RequestParam("idPosto") Integer idPosto,
                                           @Valid @RequestBody PostoCreateDTO postoCreateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(postoService.editar(idPosto, postoCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idColaborador") Integer idColaborador,
                                       @RequestParam("idPosto") Integer idPosto) throws RegraDeNegocioException {

        postoService.deletar(idPosto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/listar-por-colaborador")
    public ResponseEntity<List<PostoDTO>> listByIdUser(@RequestParam("idColaborador") Integer idColaborador)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(postoService.listarPorIdColaborador(idColaborador), HttpStatus.OK);
    }

    @GetMapping("/listar-ativos")
    public ResponseEntity<List<PostoDTO>> listByActiveGassStation(){
        return new ResponseEntity<>(postoService.listarPostosAtivos(), HttpStatus.OK);
    }

    @GetMapping("/listar-inativos")
    public ResponseEntity<List<PostoDTO>> listByInactiveGassStation(){
        return new ResponseEntity<>(postoService.listarPostosInativos(), HttpStatus.OK);
    }
}