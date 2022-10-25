package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Game;
import net.java.rasbetbackend.model.GameOneToMany;
import net.java.rasbetbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameOneToManyRepository extends JpaRepository<GameOneToMany, Long> {
    Optional<GameOneToMany> findByIdGame(int idGame);

    Boolean existsByIdGame(int idGAme);

}