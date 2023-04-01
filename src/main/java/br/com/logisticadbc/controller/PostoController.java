package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.PostoService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Posto")
@RequestMapping("/posto")
@Validated
public class PostoController /*implements PostoControllerDoc*/ {

    private final PostoService postoService;

    @GetMapping
    public ResponseEntity<List<PostoDTO>> listAll(){
        return new ResponseEntity<>(postoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<PostoDTO> findById(@RequestParam("idPosto") String idPosto) throws RegraDeNegocioException {
        return new ResponseEntity<>(postoService.listarPorId(idPosto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostoDTO> create(@Valid @RequestBody PostoCreateDTO postoCreateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(postoService.criar(postoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PostoDTO> update(@RequestParam("idPosto") String idPosto,
                                           @Valid @RequestBody PostoCreateDTO postoCreateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(postoService.editar(idPosto, postoCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idPosto") String idPosto) throws RegraDeNegocioException {

        postoService.deletar(idPosto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/listar-ativos")
    public ResponseEntity<List<PostoDTO>> listByActiveGassStation(){
        return new ResponseEntity<>(postoService.listarPostosAtivos(), HttpStatus.OK);
    }

    @GetMapping("/listar-inativos")
    public ResponseEntity<List<PostoDTO>> listByInactiveGassStation(){
        return new ResponseEntity<>(postoService.listarPostosInativos(), HttpStatus.OK);
    }

    @GetMapping("/listar-por-localizacao")
    public ResponseEntity<List<PostoDTO>> listByLocation(@RequestParam String longitude,
                                                         @RequestParam String latitude,
                                                         @RequestParam("distancia(km)") Double distancia) {

        return new ResponseEntity<>(postoService.listarPorLocalizacao(longitude, latitude, distancia), HttpStatus.OK);
    }

    @GetMapping("/listar-por-cidade")
    public ResponseEntity<List<PostoDTO>> listByCity(@RequestParam String cidade) {
        return new ResponseEntity<>(postoService.listByCidade(cidade), HttpStatus.OK);
    }
}