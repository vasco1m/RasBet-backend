package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByIdSale(int idSale);

    Boolean existsByIdSale(int idSale);


}
