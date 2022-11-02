package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    Optional<Bet> findByIdBet(int idBet);

    Optional<Bet> findByNif(int nif);

    Optional<Bet> findByNifAndIdGame(int nif, int idGame);

    Boolean existsByIdBet(int idBet);

    Boolean existsByNif(int nif);

    Boolean existsByNifAndIdGame(int nif, int idGame);
}