package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
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
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de rotas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<RotaDTO>> listAll();

    @Operation(summary = "Listar Rota a partir de um id",
            description = "Lista uma Rota do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma rota"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    ResponseEntity<RotaDTO> findById(@RequestParam("idRota") Integer idRota) throws RegraDeNegocioException;

    @Operation(summary = "Adicionar Rotas", description = "Adicionar uma rota ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados da rota adicionada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<RotaDTO> create(@Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar Rota", description = "Edita uma rota ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a rota editada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    public ResponseEntity<RotaDTO> update(@RequestParam("idRota") Integer idRota,
                                          @Valid @RequestBody RotaCreateDTO rotaCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Deletar Rota", description = "Deleta uma rota ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Retorno positivo ao deletar a rota"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("idRota") Integer idRota) throws RegraDeNegocioException;

    @Operation(summary = "Listar rotas de um usuario",
            description = "Lista todas rotas de um usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com rotas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-usuario")
    public ResponseEntity<List<RotaDTO>> listByIdUser(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar rotas ativas",
            description = "Lista todas rotas ativas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com rotas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-ativas")
    public ResponseEntity<List<RotaDTO>> listByActiveRoute();

    @Operation(summary = "Listar rotas inativas",
            description = "Lista todas rotas inativas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com rotas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-inativas")
    public ResponseEntity<List<RotaDTO>> listByInactiveRoute();

    @Operation(summary = "Listar rotas por local de partida",
            description = "Lista todas rotas por local de partida")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com rotas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-local-partida")
    public ResponseEntity<List<RotaDTO>> listByPlaceOfDeparture(@RequestParam("LocalPartida") String localPartida)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar rotas por local de destino",
            description = "Lista todas rotas por local de destino")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista com rotas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-local-destino")
    public ResponseEntity<List<RotaDTO>> listByDesnitation(@RequestParam("LocalDestino") String LocalDestino)
            throws RegraDeNegocioException;
}
