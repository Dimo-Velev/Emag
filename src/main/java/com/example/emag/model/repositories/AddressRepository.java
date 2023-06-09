package com.example.emag.model.repositories;

import com.example.emag.model.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

  List<Address> findAllByUserId(int id);
  Optional<Address> findByIdAndUserId(int id, int userId);

}
