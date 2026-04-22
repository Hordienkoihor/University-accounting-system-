package auth.service;

import auth.entities.HeadAdminRole;
import auth.entities.LoginResponse;
import auth.entities.User;
import auth.repository.interfaces.UserRepositoryInt;
import auth.service.interfaces.AuthenticationServiceInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AuthenticationService<T extends User> implements AuthenticationServiceInt<T> {
    private final UserRepositoryInt<T> userRepository;
    private final Set<Integer> activeTokens = new HashSet<>();

    private final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(UserRepositoryInt<T> userRepository) {
        log.info("Initializing AuthenticationService");
        this.userRepository = userRepository;
        log.info("Initialized AuthenticationService");
    }

    @Override
    public LoginResponse<T> login(String name, String password) {
        Optional<T> user = userRepository.findByNameAndPassword(name, password);

        if (user.isEmpty()) {
            log.error("User with login: {} and password: {}  not found", name, password);
            throw new RuntimeException("Invalid username or password");
        }

        int token = generateToken(user.get());

        if (activeTokens.contains(token)) {
            log.error("User already logged in: {}", user.get());
            throw new RuntimeException("User already logged in");
        }

        activeTokens.add(token);

        log.info("User logged in: {}", user.get());
        return new LoginResponse<>(user.get(), token);
    }

    @Override
    public T register(T user) {
        if (user instanceof HeadAdminRole) {
            log.error("Head admin role is not supported");
            throw new RuntimeException("Cannot register head admin role");
        }

        if (userRepository.existsById(user.getName())) {
            log.error("User with login: {} already registered", user.getName());
            throw new RuntimeException("User already exists");
        }
        userRepository.save(user);
        log.info("User registered: {}", user);
        return user;
    }

    @Override
    public void logout(T user) {
        int token = generateToken(user);

        if (!activeTokens.contains(token)) {
            log.error("User is not logged in: {}", user);
            throw new RuntimeException("User is not currently logged in");
        }

        activeTokens.remove(token);
        log.info("User logged out: {}", user);
    }

    @Override
    public boolean validateToken(int token) {
        return activeTokens.contains(token);
    }

    @Override
    public boolean isLoggedIn(int token) {
        return activeTokens.contains(token);
    }

    private int generateToken(T user) {
        return (user.getName() + user.getPassword()).hashCode();
    }
}
