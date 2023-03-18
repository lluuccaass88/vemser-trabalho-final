package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.MotoristaControllerDoc;
import br.com.logisticadbc.dto.in.MotoristaCreateDTO;
import br.com.logisticadbc.dto.out.MotoristaDTO;
import br.com.logisticadbc.dto.in.MotoristaUpdateDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
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
public class MotoristaController implements MotoristaControllerDoc{

    private final MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity<MotoristaDTO> create(@Valid @RequestBody MotoristaCreateDTO motoristaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(motoristaService.criar(motoristaCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MotoristaDTO>> listAll() {
        return ResponseEntity.ok(motoristaService.listar());
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<MotoristaDTO> update(@RequestParam Integer idUsuario,
                                               @Valid @RequestBody MotoristaUpdateDTO motoristaUpdateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(motoristaService.editar(idUsuario, motoristaUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> delete(@RequestParam Integer idUsuario) throws RegraDeNegocioException {
        motoristaService.deletar(idUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}