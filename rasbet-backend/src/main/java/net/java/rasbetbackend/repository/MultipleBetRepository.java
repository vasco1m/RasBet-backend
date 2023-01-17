package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Bet;
import net.java.rasbetbackend.model.MultipleBet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MultipleBetRepository extends JpaRepository<MultipleBet, Long> {
    Optional<MultipleBet> findByIdBet(int idBet);

    Optional<MultipleBet> findByNif(int nif);

    Optional<MultipleBet> findByNifAndDateTimeBetween(int idBet, LocalDateTime init, LocalDateTime end);

    List<Optional<MultipleBet>> findAllByNif(int nif);

    Boolean existsByIdBet(int idBet);

    Boolean existsByNif(int nif);

    Boolean existsByNifAndIdBet(int nif, int idBet);

    @Transactional
    void deleteByIdBet(int idBet);
}