package auth.repository;

import auth.entities.User;
import auth.repository.interfaces.UserRepositoryInt;

import java.util.*;

public class UserRepository<T extends User> implements UserRepositoryInt<T> {
    private final Map<String, T> users = new HashMap<>();

    @Override
    public Optional<T> findByNameAndPassword(String name, String password) {
        return findById(name)
                .filter(user -> user.getPassword().equals(password));
    }

    @Override
    public void save(T entity) {
        users.put(entity.getName(), entity);
    }

    @Override
    public Optional<T> findById(String name) {
        return Optional.ofNullable(users.get(name));
    }

    @Override
    public boolean existsById(String name) {
        return findById(name).isPresent();
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteById(String name) {
        users.remove(name);
    }
}
