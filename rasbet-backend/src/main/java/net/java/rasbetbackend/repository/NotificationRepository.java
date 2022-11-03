package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Notification;
import net.java.rasbetbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByIdNotification(int idNotification);

    Boolean existsByIdNotification(int idNotification);

    List<Optional<Notification>> findAllByNif(int nif);

}