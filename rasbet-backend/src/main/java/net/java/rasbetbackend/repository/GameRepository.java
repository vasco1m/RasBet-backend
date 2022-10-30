package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByIdGame(int idGame);

    Boolean existsByIdGame(int idGAme);

    Optional<Game> findByIdCategory(int idGame);

    Boolean existsByIdCategory(int idGAme);

}