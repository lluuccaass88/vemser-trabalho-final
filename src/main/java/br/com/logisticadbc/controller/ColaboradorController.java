package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.out.ColaboradorDTO;
import br.com.logisticadbc.dto.in.ColaboradorUpdateDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.ColaboradorService;
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
@RequestMapping("/colaborador")
@Validated
@Slf4j
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    @GetMapping
    public ResponseEntity<List<ColaboradorDTO>> listAll() {
        return new ResponseEntity<>(colaboradorService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ColaboradorDTO> create(@Valid @RequestBody ColaboradorCreateDTO colaboradorCreateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(colaboradorService.criar(colaboradorCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/idUsuario")
    public ResponseEntity<ColaboradorDTO> update(@RequestParam("idUsuario") Integer idUsuario,
                                                 @Valid @RequestBody ColaboradorUpdateDTO colaboradorUpdateDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(colaboradorService.editar(idUsuario, colaboradorUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/idUsuario")
    public ResponseEntity<Void> delete(Integer idUsuario) throws RegraDeNegocioException {
        colaboradorService.deletar(idUsuario);
        return ResponseEntity.ok().build();
    }
}