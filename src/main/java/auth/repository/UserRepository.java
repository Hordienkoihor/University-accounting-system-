package auth.repository;

import auth.entities.HeadAdminRole;
import auth.entities.User;
import auth.repository.interfaces.UserRepositoryInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class UserRepository<T extends User> implements UserRepositoryInt<T> {
    private final Map<String, T> users = new HashMap<>();
    private static final String headAdminConfigPath = "head_admin_config.csv";

    public UserRepository() {
        loadHeadAdmin();
    }

    @Override
    public Optional<T> findByNameAndPassword(String name, String password) {
        return findById(name)
                .filter(user -> user.getPassword().equals(password));
    }

    private void loadHeadAdmin() {
        File headAdminConfig = new File(headAdminConfigPath);

        try (Scanner myReader = new Scanner(headAdminConfig)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String[] dataArray = data.split(",");


                if (dataArray.length == 2) {
                    users.put(dataArray[0], (T) new HeadAdminRole(dataArray[0], dataArray[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
