package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.RotaCreateDTO;
import br.com.logisticadbc.dto.RotaDTO;
import br.com.logisticadbc.dto.ViagemCreateDTO;
import br.com.logisticadbc.dto.ViagemDTO;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.ViagemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
        return new ResponseEntity<>(viagemService.adicionarViagem(viagemCreateDTO), HttpStatus.OK);
    }

}
