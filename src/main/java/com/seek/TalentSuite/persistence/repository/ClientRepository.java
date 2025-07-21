package com.seek.TalentSuite.persistence.repository;

import com.seek.TalentSuite.persistence.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository  extends JpaRepository<Client,Long> {
    Optional<Client> getClientByName(String name);
    Optional<Client> getClientById(Long id);


}
