package auth.service;

import auth.entities.LoginResponse;
import auth.entities.User;
import auth.repository.interfaces.UserRepositoryInt;
import auth.service.interfaces.AuthenticationServiceInt;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AuthenticationService<T extends User> implements AuthenticationServiceInt<T> {
    private final UserRepositoryInt<T> userRepository;
    private final Set<Integer> activeTokens = new HashSet<>();

    public AuthenticationService(UserRepositoryInt<T> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponse<T> login(String name, String password) {
        Optional<T> user = userRepository.findByNameAndPassword(name, password);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        int token = generateToken(user.get());

        if (activeTokens.contains(token)) {
            throw new RuntimeException("User already logged in");
        }

        activeTokens.add(token);

        return new LoginResponse<>(user.get(), token);
    }

    @Override
    public T register(T user) {
        if (userRepository.existsById(user.getName())) {
            throw new RuntimeException("User already exists");
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void logout(T user) {
        int token = generateToken(user);

        if (!activeTokens.contains(token)) {
            throw new RuntimeException("User is not currently logged in");
        }

        activeTokens.remove(token);
    }

    @Override
    public boolean validateToken(int token) {
        return activeTokens.contains(token);
    }

    private int generateToken(T user) {
        return (user.getName() + user.getPassword()).hashCode();
    }
}
