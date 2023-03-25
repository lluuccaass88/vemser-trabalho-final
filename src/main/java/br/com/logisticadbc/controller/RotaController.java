package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.RotaControllerDoc;
import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.RotaComPostosDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.RotaService;
import br.com.logisticadbc.service.UsuarioService;
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
public class RotaController implements RotaControllerDoc {

    private final RotaService rotaService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<RotaDTO>> listAll() {
        return new ResponseEntity<>(rotaService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<RotaDTO> findById(@RequestParam("idRota") Integer idRota) throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.listarPorId(idRota), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RotaDTO> create(@Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException {

        Integer idLoggedUser = usuarioService.getIdLoggedUser();
        return new ResponseEntity<>(rotaService.criar(idLoggedUser, rotaCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RotaDTO> update(@RequestParam("idRota") Integer idRota,
                                                 @Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(rotaService.editar(idRota, rotaCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idRota") Integer idRota) throws RegraDeNegocioException {

        rotaService.deletar(idRota);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cadastrar-posto")
    public ResponseEntity<RotaComPostosDTO> linkEntities(@RequestParam("idRota") Integer idRota,
                                                         @RequestParam("idPosto") Integer idPosto)
            throws RegraDeNegocioException {

        return  new ResponseEntity<>(rotaService.cadastrarPosto(idRota, idPosto), HttpStatus.CREATED);
    }

    @GetMapping("/listar-postos-cadastrados")
    public ResponseEntity<RotaComPostosDTO> listLink(@RequestParam("idRota") Integer idRota)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.listarPostosCadastrados(idRota), HttpStatus.OK);
    }

    @GetMapping("/listar-por-usuario")
    public ResponseEntity<List<RotaDTO>> listByIdUser(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.listarPorIdColaborador(idUsuario), HttpStatus.OK);
    }

    @GetMapping("/listar-ativas")
    public ResponseEntity<List<RotaDTO>> listByActiveRoute(){
        return new ResponseEntity<>(rotaService.listarRotasAtivas(), HttpStatus.OK);
    }

    @GetMapping("/listar-inativas")
    public ResponseEntity<List<RotaDTO>> listByInactiveRoute(){
        return new ResponseEntity<>(rotaService.listarRotasInativas(), HttpStatus.OK);
    }

    @GetMapping("/listar-por-local-partida")
    public ResponseEntity<List<RotaDTO>> listByPlaceOfDeparture(@RequestParam("LocalPartida") String localPartida)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.listarPorLocalPartida(localPartida), HttpStatus.OK);
    }

    @GetMapping("/listar-por-local-destino")
    public ResponseEntity<List<RotaDTO>> listByDesnitation(@RequestParam("LocalDestino") String LocalDestino)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(rotaService.listarPorLocalDestino(LocalDestino), HttpStatus.OK);
    }
}