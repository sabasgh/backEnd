package com.acmeplex.movieticketreservation.Repository;

import com.acmeplex.movieticketreservation.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
