package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.account.LoginAuthDto;
import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.dto.user.UserAdminDto;
import com.ecommerce.backend.dto.user.UserDto;
import com.ecommerce.backend.form.login.LoginForm;
import com.ecommerce.backend.form.user.CreateUserForm;
import com.ecommerce.backend.form.user.UpdateProfileUserForm;
import com.ecommerce.backend.form.user.UpdateUserForm;
import com.ecommerce.backend.service.UserService;
import com.ecommerce.backend.storage.criteria.CustomerCriteria;
import com.ecommerce.backend.storage.criteria.UserCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class UserController extends BasicController{

    @Autowired
    UserService userService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UserAdminDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<UserAdminDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(userService.getUserById(id));
        apiMessageDto.setMessage("Get user success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<UserAdminDto>>> getList(UserCriteria userCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<UserAdminDto>>> responseListDtoApiMessageDto = new ApiMessageDto<>();
        responseListDtoApiMessageDto.setData(userService.getUserList(userCriteria, pageable));
        responseListDtoApiMessageDto.setMessage("Get list user success");
        return responseListDtoApiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<UserDto>> getListAutoComplete(UserCriteria userCriteria) {
        ApiMessageDto<List<UserDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(userService.getUserListAutoComplete(userCriteria));
        apiMessageDto.setMessage("Get list auto complete success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateUserForm createUserForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        userService.createUser(createUserForm);
        apiMessageDto.setMessage("Create user success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateUserForm updateUserForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        userService.updateUser(updateUserForm);
        apiMessageDto.setMessage("Update user success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        userService.deleteUser(id);
        apiMessageDto.setMessage("Delete user success");
        return apiMessageDto;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UserDto> login(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult) {
        ApiMessageDto<UserDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(userService.login(loginForm));
        apiMessageDto.setMessage("Login success");
        return apiMessageDto;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UserDto> get() {
        ApiMessageDto<UserDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(userService.getProfile(getCurrentUser()));
        apiMessageDto.setMessage("Get profile success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateProfile(@Valid @RequestBody UpdateProfileUserForm updateProfileUserForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        userService.updateProfile(getCurrentUser(), updateProfileUserForm);
        apiMessageDto.setMessage("Update profile success");
        return apiMessageDto;
    }
}
