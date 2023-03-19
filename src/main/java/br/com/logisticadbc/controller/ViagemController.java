package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.ViagemControllerDoc;
import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import br.com.logisticadbc.dto.in.ViagemUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.dto.out.ViagemDTO;
import br.com.logisticadbc.entity.enums.StatusViagem;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.ValidacaoService;
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
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/viagem")
public class ViagemController implements ViagemControllerDoc {

    private final ViagemService viagemService;
    private final ValidacaoService validacaoService;

    @GetMapping
    public ResponseEntity<List<ViagemDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(viagemService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<ViagemDTO> findById(@RequestParam("idViagem") Integer idViagem)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(viagemService.listarPorId(idViagem), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ViagemDTO> create(@RequestParam("idMotorista") Integer idUsuario,
                                            @Valid @RequestBody ViagemCreateDTO viagemCreateDTO)
            throws RegraDeNegocioException {

        validacaoService.validacao(idUsuario, "motorista");
        return new ResponseEntity<>(viagemService.criar(idUsuario,viagemCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idViagem}")
    public ResponseEntity<ViagemDTO> update(@RequestParam("idMotorista") Integer idUsuario,
                                            @RequestParam("idViagem") Integer idViagem,
                                            @Valid @RequestBody ViagemUpdateDTO viagemUpdateDTO)
            throws RegraDeNegocioException {

        validacaoService.validacao(idUsuario, "motorista");
        return new ResponseEntity<>(viagemService.editar(idViagem, viagemUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idViagem}")
    public ResponseEntity<Void> delete(@RequestParam("idMotorista") Integer idUsuario,
                                       @RequestParam("idViagem") Integer idViagem)
            throws RegraDeNegocioException {

        validacaoService.validacao(idUsuario, "motorista");
        viagemService.finalizar(idViagem);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar-por-motorista")
    public ResponseEntity<List<ViagemDTO>> listByIdUser(@RequestParam("idMotorista") Integer idMotorista)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(viagemService.listarPorIdMotorista(idMotorista), HttpStatus.OK);
    }

    @GetMapping("/listar-por-rota")
    public ResponseEntity<List<ViagemDTO>> listByIdRote(@RequestParam(value = "idRota") Integer idRota)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(
                viagemService.listarPorIdRota(idRota), HttpStatus.OK);
    }

    @GetMapping("/listar-por-caminhao")
    public ResponseEntity<List<ViagemDTO>> listByIdTruck(@RequestParam(value = "idCaminhao") Integer idCaminhao)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(viagemService.listarPorIdCaminhao(idCaminhao), HttpStatus.OK);
    }

    @GetMapping("/listar-por-status/paginacao")
    public ResponseEntity<PageDTO<ViagemDTO>> findByStatusOrderByDataBegun(
            @RequestParam(value = "status") StatusViagem statusViagem ,
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) {
        return new ResponseEntity<>(
                viagemService.listarPorStatusOrdenadoPorDataInicioAsc(statusViagem, pagina, tamanho), HttpStatus.OK);
    }
}
