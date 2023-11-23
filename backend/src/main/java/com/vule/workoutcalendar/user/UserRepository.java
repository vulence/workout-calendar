package com.vule.workoutcalendar.user;

import com.vule.workoutcalendar.user.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    @Query("""
           SELECT * FROM USERS WHERE username= :username
            """)
    User findByUsername(@Param("username") String username);

    @Query("""
           SELECT * FROM USERS WHERE username = :username OR email = :email
            """)
    Optional<User> findExistingUser(@Param("username") String username, @Param("email") String email);
}