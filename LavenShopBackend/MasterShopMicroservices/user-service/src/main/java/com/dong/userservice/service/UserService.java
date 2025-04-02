package com.dong.userservice.service;

import com.dong.userservice.dto.AddressDTO;
import com.dong.userservice.dto.UpdateUserProfileRequest;
import com.dong.userservice.dto.UserProfileDTO;

import java.util.List;

public interface UserService {
    
    UserProfileDTO createUserProfile(String userId, String username, String email);
    
    UserProfileDTO getUserProfileById(String id);
    
    UserProfileDTO getUserProfileByUsername(String username);
    
    UserProfileDTO updateUserProfile(String id, UpdateUserProfileRequest request);
    
    void deleteUserProfile(String id);
    
    List<UserProfileDTO> getAllUserProfiles();
    
    AddressDTO addAddress(String userId, AddressDTO addressDTO);
    
    AddressDTO updateAddress(String userId, Long addressId, AddressDTO addressDTO);
    
    void deleteAddress(String userId, Long addressId);
    
    List<AddressDTO> getUserAddresses(String userId);
    
    AddressDTO getAddressById(String userId, Long addressId);
    
    AddressDTO setDefaultAddress(String userId, Long addressId);
} 