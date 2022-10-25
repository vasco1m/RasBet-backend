package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Participant;
import net.java.rasbetbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByIdParticipant(int idParticipant);

    Optional<Participant> findByIdGame(int idGame);

    Boolean existsByIdParticipant(int idParticipant);

    Boolean existsByIdGame(int idGame);

}