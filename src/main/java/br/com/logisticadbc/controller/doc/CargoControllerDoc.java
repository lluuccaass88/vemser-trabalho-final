package br.com.logisticadbc.controller.doc;

import br.com.logisticadbc.dto.in.CargoCreateDTO;
import br.com.logisticadbc.dto.out.CargoDTO;
import br.com.logisticadbc.dto.out.CargosDeUsuarioDTO;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface CargoControllerDoc {

    @Operation(summary = "Adicionar cargo", description = "Adicionar um cargo no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do cargo adicionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<CargoDTO> create(@Valid @RequestBody CargoCreateDTO cargoCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Editar cargo", description = "Edita um cargo do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o cargo editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    public ResponseEntity<CargoDTO> update(@RequestParam("idCargo") Integer idCargo,
                                           @Valid @RequestBody CargoCreateDTO cargoCreateDTO)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar cargos", description = "Lista todos os cargos banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna uma lista de cargos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<CargoDTO>> listAll();

    @Operation(summary = "Listar cargo a partir de um id",
            description = "Lista um cargo do banco de dados a partir de um id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um cargo"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar-por-id")
    public ResponseEntity<CargoDTO> findById(@RequestParam("idCargo") Integer idCargo)
            throws RegraDeNegocioException;

    @Operation(summary = "Cadastrar usuário em cargo", description = "Cadastra um usuário no cargo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do cadastro"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<CargosDeUsuarioDTO> linkEntities(@RequestParam("idCargo") Integer idCargo,
                                                           @RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException;

    @Operation(summary = "Listar cargos por usuário",
            description = "Lista cargos por usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cargos de um usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-usuario")
    public ResponseEntity<CargosDeUsuarioDTO> listByUser(@RequestParam("idUsuario") Integer idUsuario)
            throws RegraDeNegocioException;
}
