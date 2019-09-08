package com.example.secondMin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondMinRepository extends JpaRepository<SecondMin, Integer> {
}
