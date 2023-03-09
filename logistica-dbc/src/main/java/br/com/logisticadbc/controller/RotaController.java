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
    public List<RotaDTO> list() {
        return rotaService.listarRotas();
    }

//    @PostMapping("/idPosto")// POST localhost:8080/posto ----------- PROBLEMA - Como vamos adicionar mais de um posto aqui
//    public ResponseEntity<RotaDTO> create(@PathVariable("idPosto") Integer idPosto,
//                                          @Valid @RequestBody RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
//        log.info("Rota cirada com sucesso!");
//        return new ResponseEntity<>(rotaService.adicionaRota(idPosto, rotaCreateDTO), HttpStatus.OK);
//    }

}
