package dev.samuel.financesystem.infrastructure.presentation;

import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.core.usecases.createUser.CreateUserUseCase;
import dev.samuel.financesystem.infrastructure.mapper.UserMapper;
import dev.samuel.financesystem.infrastructure.request.UserRequest;
import dev.samuel.financesystem.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        User newUser = userMapper.toEntity(request);
        User createdUser = createUserUseCase.execute(newUser);
        UserResponse response = userMapper.toUserResponse(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
