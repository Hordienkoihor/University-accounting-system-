package auth.repository.interfaces;

import auth.entities.HeadAdminRole;
import auth.entities.User;
import repository.interfaces.DefaultRepository;

import java.util.Optional;

public interface UserRepositoryInt<T extends User> extends DefaultRepository<T, String> {
    Optional<T> findByNameAndPassword(String name, String password);
}
