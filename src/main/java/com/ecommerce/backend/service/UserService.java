package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.login.LoginDto;
import com.ecommerce.backend.dto.user.UserAdminDto;
import com.ecommerce.backend.dto.user.UserDto;
import com.ecommerce.backend.form.login.LoginForm;
import com.ecommerce.backend.form.user.CreateUserForm;
import com.ecommerce.backend.form.user.UpdateUserForm;
import com.ecommerce.backend.storage.criteria.UserCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserAdminDto getUserById(Long id);

    ResponseListDto<List<UserAdminDto>> getUserList(UserCriteria userCriteria, Pageable pageable);

    void createUser(CreateUserForm createUserForm);

    void updateUser(UpdateUserForm updateUserForm);

    LoginDto login(LoginForm loginForm);

    UserDto getProfile(Long id);
}
