package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Boolean existsByNifAndIdGame(int nif, int idGame);

    Optional<Follow> findByNifAndIdGame(int nif, int idGame);

    List<Optional<Follow>> findAllByNif(int nif);

    List<Optional<Follow>> findAllByIdGame(int idGame);

    boolean existsByNif(int nif);

    boolean existsByIdGame(int idGame);

    void deleteByNifAndIdGame(int nif, int idGame);
}
