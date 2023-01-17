package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Bet;
import net.java.rasbetbackend.model.MultipleBetLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MultipleBetLineRepository extends JpaRepository<MultipleBetLine, Long> {

    Optional<MultipleBetLine> findByIdBet(int idBet);

    List<Optional<MultipleBetLine>> findAllByIdBet(int idBet);

    Boolean existsByIdBet(int idBet);

    Boolean existsByIdBetAndIdGame(int idBet, int idGame);

    @Transactional
    void deleteByIdBet(int idBet);
}