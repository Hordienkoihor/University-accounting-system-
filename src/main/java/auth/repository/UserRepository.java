package auth.repository;

import auth.entities.HeadAdminRole;
import auth.entities.User;
import auth.repository.interfaces.UserRepositoryInt;

import java.io.*;
import java.util.*;

public class UserRepository<T extends User> implements UserRepositoryInt<T> {
    private final Map<String, T> users = new HashMap<>();
    private static final String headAdminConfigPath = "head_admin_config.csv";
    private static final String userConfigPath = "user_config.csv";

    public UserRepository() {
        loadHeadAdmin();
        loadUsers();
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

    private void loadUsers() {
        File file = new File(userConfigPath);
        if (!file.exists()) {
            return;
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.isBlank()) continue;

                String[] data = line.split(",");
                if (data.length >= 3) {
                    String type = data[0];
                    String name = data[1];
                    String password = data[2];

                    T user = (T) auth.UserFactory.createUser(type, name, password);
                    users.put(name, user);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("User config file not found, starting with empty list");
        }
    }

    @Override
    public void save(T entity) {
        users.put(entity.getName(), entity);
        saveAllToFile();
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
        saveAllToFile();
    }

    private void saveAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userConfigPath))) {
            for (T user : users.values()) {
                if (!(user instanceof HeadAdminRole)) {
                    writer.write(user.getClass().getSimpleName() + "," + user.getName() + "," + user.getPassword());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Could not save users: " + e.getMessage());
        }
    }
}
