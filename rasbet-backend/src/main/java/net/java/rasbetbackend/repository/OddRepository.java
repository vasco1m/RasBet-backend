package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Odd;
import net.java.rasbetbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OddRepository extends JpaRepository<Odd, Long> {
    Optional<Odd> findByIdOdd(int idOdd);

    Boolean existsByIdOdd(int idOdd);

}