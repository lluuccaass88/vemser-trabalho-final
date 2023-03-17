package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface RotaControllerDoc {
    @Operation(summary = "Listar Rotas", description = "Lista todas as rotas no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de rotas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<RotaDTO> list() throws Exception;

    @Operation(summary = "Adicionar Rotas", description = "Adicionar uma rota ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados da rota adicionada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<RotaDTO> create(@Valid @RequestBody RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Editar Rota", description = "Edita uma rota ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados da rota editada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idRota}")
    public  ResponseEntity<RotaDTO> update(@PathVariable("idRota") Integer id,
                                           @Valid @RequestBody RotaCreateDTO rotaUpdateDTO) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Deleta Rota", description = "Excui uma rota ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorna verdadeiro caso tenha deletado a rota com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idRota}")
    public ResponseEntity<Boolean> delete(@PathVariable("idRota") Integer id) throws RegraDeNegocioException;
}
