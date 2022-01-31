package com.bruno.bdb.repositories;

import com.bruno.bdb.domain.Holder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HolderRepository extends JpaRepository<Holder, String> {

    Optional<Holder> findByEmail(String email);
}
