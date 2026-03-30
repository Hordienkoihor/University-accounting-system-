package repository.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PersistenceService<T> {
    private final ObjectMapper mapper;
    private final Class<T> clazz;
    private final Path storagePath;

    public PersistenceService(Class<T> clazz, String fileName) {
        this.clazz = clazz;
        this.storagePath = Paths.get("storage", fileName);
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public void saveAll(List<T> data) {
        try {
            if (Files.notExists(storagePath.getParent())) {
                Files.createDirectories(storagePath.getParent());
            }
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);

            Files.writeString(storagePath, json);
        } catch (Exception e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public List<T> loadAll() {
        try {
            if (Files.notExists(storagePath)) return List.of();

            String json = Files.readString(storagePath);
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            return List.of();
        }
    }
}