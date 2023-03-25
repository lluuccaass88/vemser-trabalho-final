package br.com.logisticadbc.controller;

import br.com.logisticadbc.controller.doc.CaminhaoControllerDoc;
import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.CaminhaoService;
import br.com.logisticadbc.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/caminhao") // http://localhost:8080/caminhao
@Validated
@Slf4j
public class CaminhaoController implements CaminhaoControllerDoc {

    private final CaminhaoService caminhaoService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<CaminhaoDTO>> listAll() {
        return new ResponseEntity<>(caminhaoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<CaminhaoDTO> findById(@RequestParam("idCaminhao") Integer idCaminhao)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(caminhaoService.listarPorId(idCaminhao), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CaminhaoDTO> create(@RequestParam("idUsuario") Integer idUsuario, @Valid @RequestBody CaminhaoCreateDTO caminhaoCreateDTO)
            throws RegraDeNegocioException {

        Integer idLoggedUser = usuarioService.getIdLoggedUser();
        return new ResponseEntity<>(caminhaoService.criar(idLoggedUser, caminhaoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/abastecer")
    public ResponseEntity<CaminhaoDTO> update(@RequestParam("idCaminhao") Integer idCaminhao,
                                              @RequestParam("Quantidade de gasolina") Integer gasolina)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(caminhaoService.abastecer(idCaminhao, gasolina), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idCaminhao") Integer idCaminhao) throws RegraDeNegocioException {

        caminhaoService.deletar(idCaminhao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/listar-por-usuario")
    public ResponseEntity<List<CaminhaoDTO>> listByIdUser(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException {
        return new ResponseEntity<>(caminhaoService.listarPorIdColaborador(idUsuario), HttpStatus.OK);
    }

    @GetMapping("/listar-disponiveis")
    public ResponseEntity<List<CaminhaoDTO>> listAllAvaiablesTrucks() {
        return new ResponseEntity<>(caminhaoService.listarCaminhoesLivres(), HttpStatus.OK);
    }

    @GetMapping("/listar-ativos")
    public ResponseEntity<List<CaminhaoDTO>> listActive() {
        return new ResponseEntity<>(caminhaoService.listarAtivos(), HttpStatus.OK);
    }

    @GetMapping("/listar-inativos")
    public ResponseEntity<List<CaminhaoDTO>> listInactive() {
        return new ResponseEntity<>(caminhaoService.listarInativos(), HttpStatus.OK);
    }

}