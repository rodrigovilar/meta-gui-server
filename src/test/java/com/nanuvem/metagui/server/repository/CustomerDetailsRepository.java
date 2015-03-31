package com.nanuvem.metagui.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nanuvem.metagui.server.entitytype.CustomerDetails;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, String> {

}
