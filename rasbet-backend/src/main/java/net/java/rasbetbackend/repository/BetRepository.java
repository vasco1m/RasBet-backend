package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    Optional<Bet> findByIdBet(int idBet);

    Optional<Bet> findByNif(int nif);

    List<Optional<Bet>> findAllByNif(int nif);

    List<Optional<Bet>> findAllByIdGame(int idGame);

    Optional<Bet> findByNifAndIdGame(int nif, int idGame);

    Boolean existsByIdBet(int idBet);

    Boolean existsByNif(int nif);

    Boolean existsByNifAndIdGame(int nif, int idGame);

    Boolean existsByNifAndIdBet(int nif, int idBet);

    @Transactional
    void deleteByIdBet(int idBet);
}