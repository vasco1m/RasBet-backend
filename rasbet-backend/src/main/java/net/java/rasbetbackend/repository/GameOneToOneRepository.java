package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Game;
import net.java.rasbetbackend.model.GameOneToOne;
import net.java.rasbetbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameOneToOneRepository extends JpaRepository<GameOneToOne, Long> {
    Optional<GameOneToOne> findByIdGame(int idGame);

    Boolean existsByIdGame(int idGAme);

}