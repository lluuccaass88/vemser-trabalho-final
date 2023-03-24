package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.in.CargoCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioUpdateDTO;
import br.com.logisticadbc.dto.out.CargoDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.CargoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cargo")
public class CargoController {

    private final CargoService cargoService;

    @PostMapping
    public ResponseEntity<CargoDTO> create(@Valid @RequestBody CargoCreateDTO cargoCreateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(cargoService.criar(cargoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CargoDTO> update(@RequestParam("idCargo") Integer idCargo,
                                             @Valid @RequestBody CargoDTO cargoCreateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(cargoService.editar(idCargo, cargoCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idCargo") Integer idCargo)
            throws RegraDeNegocioException {

        cargoService.deletar(idCargo);
        return ResponseEntity.ok().build();
    }
}
