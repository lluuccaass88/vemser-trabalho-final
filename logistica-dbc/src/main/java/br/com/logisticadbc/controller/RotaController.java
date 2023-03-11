package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.PostoCreateDTO;
import br.com.logisticadbc.dto.PostoDTO;
import br.com.logisticadbc.dto.RotaCreateDTO;
import br.com.logisticadbc.dto.RotaDTO;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.RotaRepository;
import br.com.logisticadbc.service.RotaService;
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
@RequestMapping("/rota") // localhost:8080/contato
@Validated
public class RotaController {

    private final RotaService rotaService;

    @GetMapping // GET localhost:8080/contato
    public List<RotaDTO> list() throws RegraDeNegocioException {
        return rotaService.listarRotas();
    }

    @PostMapping// POST localhost:8080/posto ----------- PROBLEMA - Como vamos adicionar mais de um posto aqui
    public ResponseEntity<RotaDTO> create(@Valid @RequestBody RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando rota");
        return new ResponseEntity<>(rotaService.adicionaRota(rotaCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idRota}") // PUT localhost:8080/pessoa/4
    public  ResponseEntity<RotaDTO> update(@PathVariable("idRota") Integer id, //Recuperando o id a ser editado por parametro
                                           @Valid @RequestBody RotaCreateDTO rotaUpdateDTO) throws RegraDeNegocioException, BancoDeDadosException { //Recuperando os dados que serão editados pelo o body
        log.info("Contato editado com sucesso!");
        return new ResponseEntity<> (rotaService.editarRota(id, rotaUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idRota}") // DELETE localhost:8080/contato/2
    public ResponseEntity<Boolean> delete(@PathVariable("idRota") Integer id) throws RegraDeNegocioException {
        rotaService.removerRota(id);
        log.info("Contato deletado com sucesso!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
