package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface PostoControllerDoc {
    @Operation(summary = "Listar Postos", description = "Lista todas os postos no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de postos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping // GET localhost:8080/posto
    public List<PostoDTO> list() throws BancoDeDadosException, RegraDeNegocioException;

    @Operation(summary = "Adicionar Posto", description = "Adiciona um posto ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do posto adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping()// POST localhost:8080/posto
    public ResponseEntity<PostoDTO> create(@Valid @RequestBody PostoCreateDTO postoCreateDTO)
            throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Editar Posto", description = "Edita um posto do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados do posto editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPosto}") // PUT localhost:8080/pessoa/4
    public  ResponseEntity<Boolean> update(@PathVariable("idPosto") Integer id, //Recuperando o id a ser editado por parametro
                                           @Valid @RequestBody PostoCreateDTO postoAtualizarDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Deleta Posto", description = "Exclui um posto do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorna verdadeiro caso tenha deletado o posto com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPosto}") // DELETE localhost:8080/contato/2
    public ResponseEntity<Boolean> delete(@PathVariable("idPosto") Integer id) throws RegraDeNegocioException;

}
