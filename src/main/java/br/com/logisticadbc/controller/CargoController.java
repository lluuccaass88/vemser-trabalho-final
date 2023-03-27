package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.CargoControllerDoc;
import br.com.logisticadbc.dto.in.CargoCreateDTO;
import br.com.logisticadbc.dto.out.CargoDTO;
import br.com.logisticadbc.dto.out.CargosDeUsuarioDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.CargoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cargo")
public class CargoController implements CargoControllerDoc {

    private final CargoService cargoService;

    @PostMapping
    public ResponseEntity<CargoDTO> create(@Valid @RequestBody CargoCreateDTO cargoCreateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(cargoService.criar(cargoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CargoDTO> update(@RequestParam("idCargo") Integer idCargo,
                                             @Valid @RequestBody CargoCreateDTO cargoCreateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(cargoService.editar(idCargo, cargoCreateDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CargoDTO>> listAll() {
        return new ResponseEntity<>(cargoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<CargoDTO> findById(@RequestParam("idCargo") Integer idCargo)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(cargoService.listarPorId(idCargo), HttpStatus.OK);
    }

    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<CargosDeUsuarioDTO> linkEntities(@RequestParam("idCargo") Integer idCargo,
                                             @RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException {
        
        return new ResponseEntity<>(cargoService.cadastrarUsuario(idCargo, idUsuario), HttpStatus.CREATED);
    }

    @GetMapping("/listar-por-usuario")
    public ResponseEntity<CargosDeUsuarioDTO> listByUser(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(cargoService.listarPorUsuario(idUsuario), HttpStatus.OK);
    }
}
