package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.ColaboradorControllerDoc;
import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.out.*;
import br.com.logisticadbc.dto.in.ColaboradorUpdateDTO;
import br.com.logisticadbc.dto.out.ColaboradorDTO;
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
public class ColaboradorController implements ColaboradorControllerDoc {

    private final ColaboradorService colaboradorService;

    @GetMapping
    public ResponseEntity<List<ColaboradorDTO>> listAll() {
        return new ResponseEntity<>(colaboradorService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<ColaboradorDTO> findById(@RequestParam("idColaborador") Integer idColaborador) throws RegraDeNegocioException {
        return new ResponseEntity<>(colaboradorService.listarPorId(idColaborador), HttpStatus.OK);
    }

    @GetMapping("/relatorio-completo")
    public ResponseEntity<List<ColaboradorCompletoDTO>> generateReportComplete(
                                                            @RequestParam(value = "page") Integer pagina,
                                                            @RequestParam(value = "size") Integer tamanho) {
        return new ResponseEntity<>(colaboradorService.gerarRelatorioColaboradoresInformacoesCompletas(pagina, tamanho), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ColaboradorDTO> create(@Valid @RequestBody ColaboradorCreateDTO colaboradorCreateDTO)
                                                    throws RegraDeNegocioException{
        return new ResponseEntity<>(colaboradorService.criar(colaboradorCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<ColaboradorDTO> update(@RequestParam("idUsuario") Integer idUsuario,
                                                 @Valid @RequestBody ColaboradorUpdateDTO colaboradorUpdateDTO)
                                                        throws RegraDeNegocioException {
        return new ResponseEntity<>(colaboradorService.editar(idUsuario, colaboradorUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> delete(@RequestParam("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        colaboradorService.deletar(idUsuario);
        return ResponseEntity.ok().build();
    }
}