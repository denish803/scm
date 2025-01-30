package com.scm.repository;

import com.scm.entites.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Integer> {

    Optional<ContactEntity> findByPhoneNo(String number);


}
