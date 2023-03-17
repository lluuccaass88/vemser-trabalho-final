package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.service.ColaboradorService;
import br.com.logisticadbc.service.RotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

 /*   @PostMapping
    public ResponseEntity<RotaDTO> criarRota(
            @RequestParam("idColaborador") Integer idColaborador,
            @Valid @RequestBody RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando rota");
        return new ResponseEntity<>(rotaService.adicionaRota(idColaborador, rotaCreateDTO), HttpStatus.CREATED);
    }*/
}