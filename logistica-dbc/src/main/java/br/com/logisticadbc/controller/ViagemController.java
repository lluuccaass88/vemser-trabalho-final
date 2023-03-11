package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.impl.IUViagemControllerDoc;
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
@RequestMapping("/viagem") // localhost:8080/viagem
@Validated
public class ViagemController implements IUViagemControllerDoc {

    private final ViagemService viagemService;

    @PostMapping
    public ResponseEntity<ViagemDTO> create(@Valid @RequestBody ViagemCreateDTO viagemCreateDTO) throws RegraDeNegocioException {
        log.info("Criando viagem");
        return new ResponseEntity<>(viagemService.adicionarViagem(viagemCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ViagemDTO>> listar() throws RegraDeNegocioException {
        log.info("Recebendo requisição para listar todos os usuários");
        return new ResponseEntity<>(viagemService.listarViagens(), HttpStatus.OK);
    }

    @GetMapping("/viagemFinalizada")
    public ResponseEntity<List<ViagemDTO>> listarViagensFinalizadas() throws RegraDeNegocioException {
        log.info("Recebendo requisição para listar todas as viagens finalizadas");
        return new ResponseEntity<>(viagemService.listarViagensFinalizadas(), HttpStatus.OK);
    }

    @GetMapping("/{idViagem}")
    public ResponseEntity<ViagemDTO> listarPorId (@PathVariable("idViagem") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(viagemService.listarPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/finalizarViagem/{idViagem}")
    public ResponseEntity<ViagemDTO> delete(@PathVariable("idViagem") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(viagemService.finalizarViagem(id), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{idViagem}")
    public ResponseEntity<ViagemDTO> update(@PathVariable("idViagem") Integer id,
                                            @Valid @RequestBody ViagemCreateDTO viagemUpdateDTO) throws RegraDeNegocioException, BancoDeDadosException { //Recuperando os dados que serão editados pelo o body
        log.info("Viagem editada com sucesso!");
        return new ResponseEntity<>(viagemService.editarViagem(id, viagemUpdateDTO), HttpStatus.OK);
    }
}
