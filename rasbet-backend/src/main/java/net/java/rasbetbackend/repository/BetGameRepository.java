package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Bet;
import net.java.rasbetbackend.model.BetGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BetGameRepository extends JpaRepository<BetGame, Long> {
    Optional<BetGame> findByIdBet(int idBet);

    Optional<BetGame> findByIdGame(int idGame);

    Boolean existsByIdBet(int idBet);

    Boolean existsByIdGame(int idGame);
}