package br.com.logisticadbc.controller;

import br.com.logisticadbc.dto.PostoCreateDTO;
import br.com.logisticadbc.dto.PostoDTO;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.service.PostoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posto") // localhost:8080/contato
@Validated
public class PostoController {
    private final PostoService postoService;

    public PostoController(PostoService postoService) {
        this.postoService = postoService;
    }


    @GetMapping // GET localhost:8080/posto
    public List<PostoDTO> list() throws BancoDeDadosException {
        return postoService.listarPosto();
    }

    @PostMapping()// POST localhost:8080/posto
    public ResponseEntity<PostoDTO> create(@Valid @RequestBody PostoCreateDTO postoCreateDTO)
            throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Contato criado com sucesso!");
        return new ResponseEntity<>(postoService.adicionaPosto(postoCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/{idPosto}") // PUT localhost:8080/pessoa/4
    public  ResponseEntity<Boolean> update(@PathVariable("idPosto") Integer id, //Recuperando o id a ser editado por parametro
                                           @Valid @RequestBody PostoCreateDTO postoAtualizarDTO) throws RegraDeNegocioException, BancoDeDadosException { //Recuperando os dados que serão editados pelo o body
        log.info("Contato editado com sucesso!");
        return new ResponseEntity<> (postoService.editarPosto(id, postoAtualizarDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idPosto}") // DELETE localhost:8080/contato/2
    public ResponseEntity<Boolean> delete(@PathVariable("idPosto") Integer id) throws RegraDeNegocioException {
        postoService.removerPosto(id);
        log.info("Contato deletado com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
