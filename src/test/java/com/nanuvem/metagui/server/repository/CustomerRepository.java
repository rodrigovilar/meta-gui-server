package com.nanuvem.metagui.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.entitytype.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
