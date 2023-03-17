package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.MotoristaCreateDTO;
import br.com.logisticadbc.dto.MotoristaDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.ColaboradorService;
import br.com.logisticadbc.service.MotoristaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/motorista")
@Validated
@Slf4j
public class MotoristaController {

    private final MotoristaService motoristaService;

    @GetMapping
    public ResponseEntity<List<MotoristaDTO>> listAll() {
        return ResponseEntity.ok(motoristaService.listar());
    }

    @PostMapping
    public ResponseEntity<MotoristaDTO> create(@Valid @RequestBody MotoristaCreateDTO motoristaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(motoristaService.criar(motoristaCreateDTO) , HttpStatus.CREATED);
    }
}