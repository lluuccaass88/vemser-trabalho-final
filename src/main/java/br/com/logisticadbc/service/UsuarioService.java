package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.LoginDTO;
import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.dto.in.UsuarioUpdateDTO;
import br.com.logisticadbc.dto.out.CargoDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.dto.out.UsuarioCompletoDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.CargoEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.UsuarioRepository;
import br.com.logisticadbc.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final CargoService cargoService;
    public final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private PasswordEncoder passwordEncoder;
    private final LogService logService;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          EmailService emailService,
                          TokenService tokenService,
                          @Lazy CargoService cargoService,
                          @Lazy AuthenticationManager authenticationManager,
                          ObjectMapper objectMapper,
                          PasswordEncoder passwordEncoder,
                          LogService logService
                          ) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
        this.cargoService = cargoService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.logService = logService;
    }

    public UsuarioDTO criar(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);

        // retorna cargo informado no usuarioCreateDTO
        CargoEntity cargoEntity = cargoService.buscarPorNome(usuarioCreateDTO.getNomeCargo());
        Set<CargoEntity> cargos = new HashSet<>();
        cargos.add(cargoEntity);

        try {
            usuarioEntity.setStatus(StatusGeral.ATIVO);
            //criptografa senha
            usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
            usuarioEntity.setCargos(cargos);

            UsuarioEntity usuarioCriado = usuarioRepository.save(usuarioEntity);

            String descricao = "Operação em Usuário: " + usuarioEntity.getLogin();
            logService.gerarLog(usuarioEntity.getLogin(), descricao, TipoOperacao.CADASTRO);

            emailService.enviarEmailBoasVindas(usuarioCriado);

            return transformaEmUsuarioDTO(usuarioCriado);

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public UsuarioDTO editar(Integer idUsuario, UsuarioUpdateDTO usuarioUpdateDTO)
            throws RegraDeNegocioException {
        Integer idLoggedUser = getIdLoggedUser();
        UsuarioEntity usuarioLogado = buscarPorId(idLoggedUser);

        UsuarioEntity usuarioEncontrado = null;

        // se tiver parâmetro verifica se é admin
        if (idUsuario != null) {

            if (!isAdmin(usuarioLogado)) {
                throw new RegraDeNegocioException("Só admin pode editar outros usuários.");
            }

            usuarioEncontrado = buscarPorId(idUsuario);

            if (isAdmin(usuarioEncontrado)) {
                throw new RegraDeNegocioException("Não é possível editar um admin.");
            }

            // se nao tiver parâmetro, usa o proprio usuario logado
        } else {
            usuarioEncontrado = usuarioLogado;
        }
        try {
            usuarioEncontrado.setNome(usuarioUpdateDTO.getNome());
            usuarioEncontrado.setEmail(usuarioUpdateDTO.getEmail());
            usuarioEncontrado.setSenha(passwordEncoder.encode(usuarioUpdateDTO.getSenha()));
            usuarioEncontrado.setDocumento(usuarioUpdateDTO.getDocumento());

            UsuarioEntity usuarioEditado = usuarioRepository.save(usuarioEncontrado);

            String descricao = "Operação em Usuário: " + usuarioEncontrado.getLogin();
            logService.gerarLog(usuarioEncontrado.getLogin(), descricao, TipoOperacao.ALTERACAO);

            return transformaEmUsuarioDTO(usuarioEditado);

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    public void deletar(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = buscarPorId(idUsuario);

        if (isAdmin(usuarioEncontrado)) {
            throw new RegraDeNegocioException("Não é possível deletar um admin.");
        }

        try {
            usuarioEncontrado.setStatus(StatusGeral.INATIVO);
            usuarioRepository.save(usuarioEncontrado);

            String descricao = "Operação em Usuário: " + usuarioEncontrado.getLogin();
            logService.gerarLog(usuarioEncontrado.getLogin(), descricao, TipoOperacao.EXCLUSAO);

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> transformaEmUsuarioDTO(usuario))
                .toList();
    }

    public UsuarioDTO listarPorId(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = buscarPorId(idUsuario);

        try {
            return transformaEmUsuarioDTO(usuarioEncontrado);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro de conversão.");
        }
    }

    public PageDTO<UsuarioDTO> listarPorCargo(String cargo, Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<UsuarioEntity> paginacaoUsuario = usuarioRepository.findByCargoUsuario(solicitacaoPagina, cargo);

        List<UsuarioDTO> usuarioDTOList = paginacaoUsuario
                .getContent()
                .stream()
                .map(usuario -> transformaEmUsuarioDTO(usuario))
                .toList();

        return new PageDTO<>(
                paginacaoUsuario.getTotalElements(),
                paginacaoUsuario.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOList
        );
    }

    public PageDTO<UsuarioDTO> listarPorCargoEStatus(String cargo, StatusGeral status, Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<UsuarioEntity> paginacaoUsuario = usuarioRepository.findByCargosAndStatus(solicitacaoPagina, cargo, status);

        List<UsuarioDTO> usuarioDTOList = paginacaoUsuario
                .getContent()
                .stream()
                .map(usuario -> transformaEmUsuarioDTO(usuario))
                .toList();

        return new PageDTO<>(
                paginacaoUsuario.getTotalElements(),
                paginacaoUsuario.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOList
        );
    }

    public PageDTO<UsuarioCompletoDTO> gerarRelatorioCompleto(Integer pagina, Integer tamanho) { //ORDENAR POR CARGO
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<UsuarioCompletoDTO> paginacaoMotorista = usuarioRepository.relatorio(solicitacaoPagina);

        List<UsuarioCompletoDTO> usuarioDTOList = paginacaoMotorista
                .getContent()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioCompletoDTO.class))
                .toList();

        return new PageDTO<>(
                paginacaoMotorista.getTotalElements(),
                paginacaoMotorista.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOList
        );
    }

    public PageDTO<UsuarioDTO> listarMotoristasLivres(Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<UsuarioEntity> paginacaoUsuario = usuarioRepository
                .findByMotoristasLivres(solicitacaoPagina);

        List<UsuarioDTO> usuarioDTOList = paginacaoUsuario
                .getContent()
                .stream()
                .map(usuario -> transformaEmUsuarioDTO(usuario))
                .toList();

        return new PageDTO<>(
                paginacaoUsuario.getTotalElements(),
                paginacaoUsuario.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOList
        );
    }

    public UsuarioEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
    }

    public Optional<UsuarioEntity> buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public String autenticar(LoginDTO loginDTO) throws RegraDeNegocioException {
        ativo(loginDTO);
        try {
            // cria dto do spring
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getLogin(),
                            loginDTO.getSenha()
                    );

            // autentica
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            // user details
            Object principal = authentication.getPrincipal();
            // recupera usuario da autenticação
            UsuarioEntity usuarioEntity = (UsuarioEntity) principal;

            return tokenService.gerarToken(usuarioEntity);

        } catch (NoSuchElementException | BadCredentialsException e) {
            throw new RegraDeNegocioException("Credenciais inválidas");
        }
    }

    // recupera id do usuário do Token
    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString());
    }

    // recupera usuário do Token
    public UsuarioDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioEntity usuarioLogado = buscarPorId(getIdLoggedUser());
        return transformaEmUsuarioDTO(usuarioLogado);
    }

    public void ativo(LoginDTO loginDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(loginDTO.getLogin())
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));

        if (usuarioEntity.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Usuário inativo!");
        }
    }

    // retorna usuarioDTO já com os cargos convertidos
    public UsuarioDTO transformaEmUsuarioDTO(UsuarioEntity usuarioEntity) {
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);

        Set<CargoDTO> cargoDTOSet = usuarioEntity.getCargos()
                .stream()
                .map(cargo -> {
                    CargoDTO cargoDTO = objectMapper.convertValue(cargo, CargoDTO.class);
                    return cargoDTO;
                })
                .collect(Collectors.toSet());

        usuarioDTO.setCargos(cargoDTOSet);
        return usuarioDTO;
    }

    // verifica se usuario é admin
    public boolean isAdmin(UsuarioEntity usuarioEncontrado) {
        boolean isAdmin = usuarioEncontrado.getCargos()
                .stream()
                .anyMatch(cargo -> cargo.getNome().equals("ROLE_ADMIN"));
        return isAdmin;
    }
}