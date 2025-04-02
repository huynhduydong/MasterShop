package com.dong.userservice.controller;

import com.dong.userservice.dto.AddressDTO;
import com.dong.userservice.dto.UpdateUserProfileRequest;
import com.dong.userservice.dto.UserProfileDTO;
import com.dong.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Controller", description = "API for managing user profiles")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all user profiles", description = "Retrieves all user profiles (Admin only)")
    public ResponseEntity<List<UserProfileDTO>> getAllUserProfiles() {
        log.info("Getting all user profiles");
        return ResponseEntity.ok(userService.getAllUserProfiles());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.subject")
    @Operation(summary = "Get user profile by ID", description = "Retrieves a user profile by ID")
    public ResponseEntity<UserProfileDTO> getUserProfileById(@PathVariable String id) {
        log.info("Getting user profile by ID: {}", id);
        return ResponseEntity.ok(userService.getUserProfileById(id));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user profile", description = "Retrieves the profile of the currently authenticated user")
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        log.info("Getting current user profile for user ID: {}", userId);
        return ResponseEntity.ok(userService.getUserProfileById(userId));
    }

    @PutMapping("/me")
    @Operation(summary = "Update current user profile", description = "Updates the profile of the currently authenticated user")
    public ResponseEntity<UserProfileDTO> updateCurrentUserProfile(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody UpdateUserProfileRequest request) {
        String userId = jwt.getSubject();
        log.info("Updating current user profile for user ID: {}", userId);
        return ResponseEntity.ok(userService.updateUserProfile(userId, request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.subject")
    @Operation(summary = "Update user profile", description = "Updates a user profile by ID")
    public ResponseEntity<UserProfileDTO> updateUserProfile(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserProfileRequest request) {
        log.info("Updating user profile with ID: {}", id);
        return ResponseEntity.ok(userService.updateUserProfile(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user profile", description = "Deletes a user profile by ID (Admin only)")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable String id) {
        log.info("Deleting user profile with ID: {}", id);
        userService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }

    // Address endpoints
    @GetMapping("/me/addresses")
    @Operation(summary = "Get current user addresses", description = "Retrieves all addresses for the currently authenticated user")
    public ResponseEntity<List<AddressDTO>> getCurrentUserAddresses(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        log.info("Getting addresses for current user ID: {}", userId);
        return ResponseEntity.ok(userService.getUserAddresses(userId));
    }

    @PostMapping("/me/addresses")
    @Operation(summary = "Add address for current user", description = "Adds a new address for the currently authenticated user")
    public ResponseEntity<AddressDTO> addAddressForCurrentUser(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody AddressDTO addressDTO) {
        String userId = jwt.getSubject();
        log.info("Adding address for current user ID: {}", userId);
        return new ResponseEntity<>(userService.addAddress(userId, addressDTO), HttpStatus.CREATED);
    }

    @GetMapping("/me/addresses/{addressId}")
    @Operation(summary = "Get address by ID for current user", description = "Retrieves a specific address for the currently authenticated user")
    public ResponseEntity<AddressDTO> getAddressByIdForCurrentUser(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long addressId) {
        String userId = jwt.getSubject();
        log.info("Getting address ID: {} for current user ID: {}", addressId, userId);
        return ResponseEntity.ok(userService.getAddressById(userId, addressId));
    }

    @PutMapping("/me/addresses/{addressId}")
    @Operation(summary = "Update address for current user", description = "Updates a specific address for the currently authenticated user")
    public ResponseEntity<AddressDTO> updateAddressForCurrentUser(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressDTO addressDTO) {
        String userId = jwt.getSubject();
        log.info("Updating address ID: {} for current user ID: {}", addressId, userId);
        return ResponseEntity.ok(userService.updateAddress(userId, addressId, addressDTO));
    }

    @DeleteMapping("/me/addresses/{addressId}")
    @Operation(summary = "Delete address for current user", description = "Deletes a specific address for the currently authenticated user")
    public ResponseEntity<Void> deleteAddressForCurrentUser(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long addressId) {
        String userId = jwt.getSubject();
        log.info("Deleting address ID: {} for current user ID: {}", addressId, userId);
        userService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/addresses/{addressId}/default")
    @Operation(summary = "Set default address for current user", description = "Sets a specific address as default for the currently authenticated user")
    public ResponseEntity<AddressDTO> setDefaultAddressForCurrentUser(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long addressId) {
        String userId = jwt.getSubject();
        log.info("Setting address ID: {} as default for current user ID: {}", addressId, userId);
        return ResponseEntity.ok(userService.setDefaultAddress(userId, addressId));
    }
} 