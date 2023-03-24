package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.CargoCreateDTO;
import br.com.logisticadbc.dto.out.CargoDTO;
import br.com.logisticadbc.entity.CargoEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CargoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CargoService {

    private final CargoRepository cargoRepository;
    private final ObjectMapper objectMapper;

    public CargoDTO criar(CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException {
        CargoEntity cargoEntity = objectMapper.convertValue(cargoCreateDTO, CargoEntity.class);

        try {
            cargoRepository.save(cargoEntity);

            return objectMapper.convertValue(cargoEntity, CargoDTO.class);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public CargoDTO editar(Integer idCargo, CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException {
        CargoEntity cargoEncontrado = buscarPorId(idCargo);

        try {
            cargoEncontrado.setNome(cargoCreateDTO.getNome());

            cargoRepository.save(cargoEncontrado);

            return objectMapper.convertValue(cargoEncontrado, CargoDTO.class);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public List<CargoDTO> listar() {
        return cargoRepository.findAll()
                .stream()
                .map(cargo -> objectMapper.convertValue(cargo, CargoDTO.class))
                .toList();
    }

    public CargoDTO listarPorId(Integer idCargo) throws RegraDeNegocioException {
        CargoEntity cargoEncontrado = buscarPorId(idCargo);

        return objectMapper.convertValue(cargoEncontrado, CargoDTO.class);
    }

    public CargoEntity buscarPorId(Integer idCargo) throws RegraDeNegocioException {
        return cargoRepository.findById(idCargo)
                .orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado!"));
    }
}
