package com.dong.controller;

import com.dong.dto.model.UserDto;
import com.dong.dto.response.ObjectResponse;
import com.dong.service.UserService;
import com.dong.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody  UserDto userDto){
        return new ResponseEntity<>(this.userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ObjectResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(this.userService.getAllUser(pageSize, pageNo, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "userId") Long id){
        return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(value = "userId") Long id,
            @RequestBody UserDto userDto){
        return new ResponseEntity<>(this.userService.updateUser(userDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "userId") Long id){
        this.userService.deleteUser(id);

        return new ResponseEntity<>("Delete user successfully!", HttpStatus.OK);
    }

}
