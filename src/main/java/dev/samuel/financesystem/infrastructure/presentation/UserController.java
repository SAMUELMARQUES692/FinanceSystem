package dev.samuel.financesystem.infrastructure.presentation;

import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.core.usecases.createUser.CreateUserUseCase;
import dev.samuel.financesystem.core.usecases.deleteUser.DeleteUserUseCase;
import dev.samuel.financesystem.core.usecases.updateUser.UpdateUseCase;
import dev.samuel.financesystem.infrastructure.mapper.UserMapper;
import dev.samuel.financesystem.infrastructure.request.UserRequest;
import dev.samuel.financesystem.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUseCase updateUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        User newUser = userMapper.toEntity(request);
        User createdUser = createUserUseCase.execute(newUser);
        UserResponse response = userMapper.toUserResponse(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        User newUser = userMapper.toEntity(request);
        User updateUser = updateUseCase.execute(id, newUser);
        UserResponse response = userMapper.toUserResponse(updateUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
