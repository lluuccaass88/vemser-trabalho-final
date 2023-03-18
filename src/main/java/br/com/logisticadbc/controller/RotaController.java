package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.in.ColaboradorUpdateDTO;
import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.ColaboradorDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
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
    public ResponseEntity<List<RotaDTO>> listAll() {
        return new ResponseEntity<>(rotaService.listar(), HttpStatus.OK);
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<RotaDTO> create(@RequestParam("idUsuario") Integer idUsuario,
                                          @Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.criar(idUsuario, rotaCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idRota}")
    public ResponseEntity<RotaDTO> update(@RequestParam("idRota") Integer idRota,
                                                 @Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.editar(idRota, rotaCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idRota}")
    public ResponseEntity<Void> delete(@RequestParam("idRota") Integer idRota) throws RegraDeNegocioException {
        rotaService.deletar(idRota);
        return ResponseEntity.ok().build();
    }
}