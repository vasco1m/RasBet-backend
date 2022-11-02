package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findAllByNif(int nif);

    Boolean existsByNif(int nif);
}
