package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Bet;
import net.java.rasbetbackend.model.BetSingle;
import net.java.rasbetbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BetSingleRepository extends JpaRepository<BetSingle, Long> {
    Optional<BetSingle> findByIdBet(int idBet);

    Optional<BetSingle> findByIdGame(int idGame);

    Boolean existsByIdBet(int idBet);

    Boolean existsByIdGame(int idGame);
}