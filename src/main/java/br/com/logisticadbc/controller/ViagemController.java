package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.ViagemControllerDoc;
import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import br.com.logisticadbc.dto.in.ViagemUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.ViagemDTO;
import br.com.logisticadbc.entity.enums.StatusViagem;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.ViagemService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Viagem")
@RequestMapping("/viagem")
public class ViagemController implements ViagemControllerDoc {

    private final ViagemService viagemService;

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
    public ResponseEntity<ViagemDTO> create(@RequestParam("idMotorista") Integer idMotorista,
                                            @Valid @RequestBody ViagemCreateDTO viagemCreateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(viagemService.criar(idMotorista, viagemCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ViagemDTO> update(@RequestParam("idMotorista") Integer idMotorista,
                                            @RequestParam("idViagem") Integer idViagem,
                                            @Valid @RequestBody ViagemUpdateDTO viagemUpdateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(viagemService.editar(idMotorista, idViagem, viagemUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idMotorista") Integer idMotorista,
                                       @RequestParam("idViagem") Integer idViagem)
            throws RegraDeNegocioException {

        viagemService.finalizar(idMotorista, idViagem);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/listar-por-idUsuario")
    public ResponseEntity<List<ViagemDTO>> listByIdUser(
            @RequestParam(value = "idMotorista", required = false) Integer idMotorista)
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
            @RequestParam(value = "status") StatusViagem statusViagem,
            @RequestParam(value = "page") Integer pagina,
            @RequestParam(value = "size") Integer tamanho) {
        return new ResponseEntity<>(
                viagemService.listarPorStatusOrdenadoPorDataInicioAsc(statusViagem, pagina, tamanho), HttpStatus.OK);
    }
}
