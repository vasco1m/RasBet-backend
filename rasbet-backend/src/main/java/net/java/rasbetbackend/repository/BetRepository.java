package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Bet;
import net.java.rasbetbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    Optional<Bet> findByIdBet(int idBet);

    Optional<Bet> findByNif(int nif);

    Boolean existsByIdBet(int idBet);

    Boolean existsByNif(int nif);
}