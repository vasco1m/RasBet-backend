package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Sale;
import net.java.rasbetbackend.model.Transaction;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByIdTransaction(int idTransaction);

    Boolean existsByIdTransaction(int idTransaction);

    List<Optional<Transaction>> findAllByNif(int nif);

    List<Optional<Transaction>> findAllByNifAndDateBetween(int nif, LocalDateTime init, LocalDateTime end);

}
