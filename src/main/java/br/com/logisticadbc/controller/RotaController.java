package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.impl.IRotaControllerDoc;
import br.com.logisticadbc.dto.PostoCreateDTO;
import br.com.logisticadbc.dto.PostoDTO;
import br.com.logisticadbc.dto.RotaCreateDTO;
import br.com.logisticadbc.dto.RotaDTO;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.RotaRepository;
import br.com.logisticadbc.service.ColaboradorService;
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
@RequestMapping("/rota")
@Validated
public class RotaController { // implements IRotaControllerDoc {

    private final RotaService rotaService;
    private final ColaboradorService colaboradorService;

    @GetMapping
    public List<RotaDTO> list() {
        return rotaService.listarRotas();
    }

    @PostMapping
    public ResponseEntity<RotaDTO> criarRota(
            @RequestParam("idColaborador") Integer idColaborador,
            @Valid @RequestBody RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando rota");
        return new ResponseEntity<>(rotaService.adicionaRota(idColaborador, rotaCreateDTO), HttpStatus.CREATED);
    }
}