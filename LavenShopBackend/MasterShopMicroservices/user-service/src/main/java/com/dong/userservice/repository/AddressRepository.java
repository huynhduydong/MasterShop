package com.dong.userservice.repository;

import com.dong.userservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    List<Address> findByUserProfileId(String userProfileId);
    
    Optional<Address> findByIdAndUserProfileId(Long id, String userProfileId);
    
    Optional<Address> findByUserProfileIdAndIsDefaultTrue(String userProfileId);
} 