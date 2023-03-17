package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.PostoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostoRepository extends JpaRepository<PostoEntity, Integer> {


}