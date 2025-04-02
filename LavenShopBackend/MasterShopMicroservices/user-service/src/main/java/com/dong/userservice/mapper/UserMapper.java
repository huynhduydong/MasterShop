package com.dong.userservice.mapper;

import com.dong.userservice.dto.AddressDTO;
import com.dong.userservice.dto.UpdateUserProfileRequest;
import com.dong.userservice.dto.UserProfileDTO;
import com.dong.userservice.entity.Address;
import com.dong.userservice.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    
    UserProfileDTO toUserProfileDTO(UserProfile userProfile);
    
    List<UserProfileDTO> toUserProfileDTOs(List<UserProfile> userProfiles);
    
    AddressDTO toAddressDTO(Address address);
    
    List<AddressDTO> toAddressDTOs(List<Address> addresses);
    
    @Mapping(target = "userProfile", ignore = true)
    Address toAddress(AddressDTO addressDTO);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    void updateUserProfileFromRequest(UpdateUserProfileRequest request, @MappingTarget UserProfile userProfile);
} 