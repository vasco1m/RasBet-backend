package net.java.rasbetbackend.repository;

import net.java.rasbetbackend.model.Category;
import net.java.rasbetbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByIdCategory(int idCategory);

    Optional<Category> findByName(String name);

    Boolean existsByIdCategory(int idCategory);

    Boolean existsByName(String name);
}