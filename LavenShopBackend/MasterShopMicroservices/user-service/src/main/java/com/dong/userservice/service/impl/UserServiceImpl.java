package com.dong.userservice.service.impl;

import com.dong.userservice.dto.AddressDTO;
import com.dong.userservice.dto.UpdateUserProfileRequest;
import com.dong.userservice.dto.UserProfileDTO;
import com.dong.userservice.entity.Address;
import com.dong.userservice.entity.UserProfile;
import com.dong.userservice.exception.ResourceNotFoundException;
import com.dong.userservice.mapper.UserMapper;
import com.dong.userservice.repository.AddressRepository;
import com.dong.userservice.repository.UserProfileRepository;
import com.dong.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserProfileDTO createUserProfile(String userId, String username, String email) {
        log.info("Creating user profile for user ID: {}", userId);
        
        UserProfile userProfile = UserProfile.builder()
                .id(userId)
                .username(username)
                .email(email)
                .build();
        
        UserProfile savedUserProfile = userProfileRepository.save(userProfile);
        return userMapper.toUserProfileDTO(savedUserProfile);
    }

    @Override
    public UserProfileDTO getUserProfileById(String id) {
        log.info("Getting user profile by ID: {}", id);
        
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found with ID: " + id));
        
        return userMapper.toUserProfileDTO(userProfile);
    }

    @Override
    public UserProfileDTO getUserProfileByUsername(String username) {
        log.info("Getting user profile by username: {}", username);
        
        UserProfile userProfile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found with username: " + username));
        
        return userMapper.toUserProfileDTO(userProfile);
    }

    @Override
    @Transactional
    public UserProfileDTO updateUserProfile(String id, UpdateUserProfileRequest request) {
        log.info("Updating user profile with ID: {}", id);
        
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found with ID: " + id));
        
        userMapper.updateUserProfileFromRequest(request, userProfile);
        UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
        
        return userMapper.toUserProfileDTO(updatedUserProfile);
    }

    @Override
    @Transactional
    public void deleteUserProfile(String id) {
        log.info("Deleting user profile with ID: {}", id);
        
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found with ID: " + id));
        
        userProfileRepository.delete(userProfile);
    }

    @Override
    public List<UserProfileDTO> getAllUserProfiles() {
        log.info("Getting all user profiles");
        
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return userMapper.toUserProfileDTOs(userProfiles);
    }

    @Override
    @Transactional
    public AddressDTO addAddress(String userId, AddressDTO addressDTO) {
        log.info("Adding address for user ID: {}", userId);
        
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found with ID: " + userId));
        
        Address address = userMapper.toAddress(addressDTO);
        
        // If this is the first address or marked as default, set it as default
        if (userProfile.getAddresses().isEmpty() || addressDTO.isDefault()) {
            // Reset any existing default address
            if (addressDTO.isDefault()) {
                resetDefaultAddress(userId);
            }
            address.setDefault(true);
        }
        
        address.setUserProfile(userProfile);
        Address savedAddress = addressRepository.save(address);
        
        return userMapper.toAddressDTO(savedAddress);
    }

    @Override
    @Transactional
    public AddressDTO updateAddress(String userId, Long addressId, AddressDTO addressDTO) {
        log.info("Updating address ID: {} for user ID: {}", addressId, userId);
        
        Address address = addressRepository.findByIdAndUserProfileId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
        
        // If setting as default, reset other default addresses
        if (addressDTO.isDefault() && !address.isDefault()) {
            resetDefaultAddress(userId);
        }
        
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setDefault(addressDTO.isDefault());
        
        Address updatedAddress = addressRepository.save(address);
        return userMapper.toAddressDTO(updatedAddress);
    }

    @Override
    @Transactional
    public void deleteAddress(String userId, Long addressId) {
        log.info("Deleting address ID: {} for user ID: {}", addressId, userId);
        
        Address address = addressRepository.findByIdAndUserProfileId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
        
        addressRepository.delete(address);
        
        // If deleted address was default and there are other addresses, set another one as default
        if (address.isDefault()) {
            List<Address> remainingAddresses = addressRepository.findByUserProfileId(userId);
            if (!remainingAddresses.isEmpty()) {
                Address newDefaultAddress = remainingAddresses.get(0);
                newDefaultAddress.setDefault(true);
                addressRepository.save(newDefaultAddress);
            }
        }
    }

    @Override
    public List<AddressDTO> getUserAddresses(String userId) {
        log.info("Getting addresses for user ID: {}", userId);
        
        // Verify user exists
        if (!userProfileRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User profile not found with ID: " + userId);
        }
        
        List<Address> addresses = addressRepository.findByUserProfileId(userId);
        return userMapper.toAddressDTOs(addresses);
    }

    @Override
    public AddressDTO getAddressById(String userId, Long addressId) {
        log.info("Getting address ID: {} for user ID: {}", addressId, userId);
        
        Address address = addressRepository.findByIdAndUserProfileId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
        
        return userMapper.toAddressDTO(address);
    }

    @Override
    @Transactional
    public AddressDTO setDefaultAddress(String userId, Long addressId) {
        log.info("Setting address ID: {} as default for user ID: {}", addressId, userId);
        
        // Reset any existing default address
        resetDefaultAddress(userId);
        
        // Set the new default address
        Address address = addressRepository.findByIdAndUserProfileId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
        
        address.setDefault(true);
        Address updatedAddress = addressRepository.save(address);
        
        return userMapper.toAddressDTO(updatedAddress);
    }
    
    private void resetDefaultAddress(String userId) {
        addressRepository.findByUserProfileIdAndIsDefaultTrue(userId)
                .ifPresent(defaultAddress -> {
                    defaultAddress.setDefault(false);
                    addressRepository.save(defaultAddress);
                });
    }
} 