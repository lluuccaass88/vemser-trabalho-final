package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.*;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.ViagemService;
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
@RequestMapping("/viagem") // localhost:8080/contato
@Validated
public class ViagemController {

    private final ViagemService viagemService;

    @PostMapping// POST localhost:8080/posto ----------- PROBLEMA - Como vamos adicionar mais de um posto aqui
    public ResponseEntity<ViagemDTO> create(@Valid @RequestBody ViagemCreateDTO viagemCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando viagem");
        return new ResponseEntity<>(viagemService.adicionarViagem(viagemCreateDTO), HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<List<ViagemDTO>> listar() throws Exception {
        log.info("Recebendo requisição para listar todos os usuários");
        return new ResponseEntity<>(viagemService.listarViagens(), HttpStatus.OK);
    }

    @GetMapping("/viagemFinalizada")
    public ResponseEntity<List<ViagemDTO>> listarViagensFinalizadas() throws Exception {
        log.info("Recebendo requisição para listar todas as viagens finalizadas");
        return new ResponseEntity<>(viagemService.listarViagensFinalizadas(), HttpStatus.OK);
    }

    @DeleteMapping("/finalizarViagem/{idViagem}") // DELETE localhost:8080/contato/2
    public ResponseEntity<ViagemDTO> delete(@PathVariable("idViagem") Integer id) throws Exception {
        return new ResponseEntity<>(viagemService.finalizarViagem(id), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{idViagem}") // PUT localhost:8080/pessoa/4
    public  ResponseEntity<ViagemDTO> update(@PathVariable("idViagem") Integer id, //Recuperando o id a ser editado por parametro
                                           @Valid @RequestBody ViagemCreateDTO viagemUpdateDTO) throws RegraDeNegocioException, BancoDeDadosException { //Recuperando os dados que serão editados pelo o body
        log.info("Viagem editada com sucesso!");
        return new ResponseEntity<> (viagemService.editarViagem(id, viagemUpdateDTO), HttpStatus.OK);
    }


//    @GetMapping("/listarPostos/{idViagem}")
//    public ResponseEntity<List<PostoDTO>> listarPostos(@PathVariable("idRota") Integer id) throws Exception {
//        log.info("Recebendo requisição para listar todos os usuários");
//        return new ResponseEntity<>(viagemService.listarPostosEmViagem(id), HttpStatus.OK);
//    }





}
