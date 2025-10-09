package com.example.WigellGym.repository;

import com.example.WigellGym.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GymCustomerRepository extends CrudRepository<User, UUID> {

}
