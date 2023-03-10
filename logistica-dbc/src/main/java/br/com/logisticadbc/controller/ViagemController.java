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

    @GetMapping("/listarPostos/{idViagem}")
    public ResponseEntity<List<PostoDTO>> listarPostos(@PathVariable("idRota") Integer id) throws Exception {
        log.info("Recebendo requisição para listar todos os usuários");
        return new ResponseEntity<>(viagemService.listarPostosEmViagem(id), HttpStatus.OK);
    }





}
