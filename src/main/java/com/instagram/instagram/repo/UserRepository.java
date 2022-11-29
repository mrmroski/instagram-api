package com.instagram.instagram.repo;

import com.instagram.instagram.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = :username") //JPQL not SQL - JPQL is not database specific
    Optional<User> findUserByUsername(@Param("username") String username);

    @Transactional
    @Modifying //It means that query does not need to map anything from database into entities, just modifying some data in a table
    @Query("DELETE FROM User u WHERE u.username = ?1")
    void deleteUserByUsername(String username);

}
