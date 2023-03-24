package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.CargoCreateDTO;
import br.com.logisticadbc.dto.out.CargoDTO;
import br.com.logisticadbc.entity.CargoEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CargoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
