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
    @Operation(summary = "Adicionar Posto", description = "Adicionar uma viagem no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do posto adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping()
    public ResponseEntity<PostoDTO> create(@RequestParam("idColaborador") Integer idColaborador, @Valid @RequestBody PostoCreateDTO postoCreateDTO)
            throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Listar Postos", description = "Lista todas os postos no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de postos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<PostoDTO>> listAll() throws BancoDeDadosException, RegraDeNegocioException;


    @Operation(summary = "Editar Posto", description = "Edita um posto do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o posto editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPosto}")
    public ResponseEntity<PostoDTO> update(@PathVariable("idPosto") Integer id, //Recuperando o id a ser editado por parametro
                                           @Valid @RequestBody PostoCreateDTO postoAtualizarDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Deleta Posto", description = "Deleta um posto do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar a viagem"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPosto}") // DELETE localhost:8080/contato/2
    public ResponseEntity<Void> delete(@PathVariable("idPosto") Integer id) throws RegraDeNegocioException;

}
