package repository.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PersistenceService<T> {
    private final ObjectMapper mapper;
    private final Class<T> clazz;
    private final Path storagePath;

    private final ExecutorService writerExecutor = Executors.newSingleThreadExecutor();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Logger log = LoggerFactory.getLogger(PersistenceService.class);

    public PersistenceService(Class<T> clazz, String fileName) {
        this.clazz = clazz;
        this.storagePath = Paths.get("storage", fileName);
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        this.mapper.findAndRegisterModules();
    }

    public void saveAll(List<T> data) {
        List<T> copy = List.copyOf(data);

        writerExecutor.submit(() -> {
            lock.writeLock().lock();
            try {
                if (Files.notExists(storagePath.getParent())) {
                    Files.createDirectories(storagePath.getParent());
                }

                String json = mapper.writerWithDefaultPrettyPrinter()
                        .forType(mapper.getTypeFactory().constructCollectionType(List.class, clazz))
                        .writeValueAsString(copy);

                Files.writeString(storagePath, json);
                log.info("Saved {} objects to {}", copy.size(), storagePath.getFileName());
            } catch (Exception e) {
                log.error("Error saving data: {}", e.getMessage());
            } finally {
                lock.writeLock().unlock();
            }
        });

    }

    public List<T> loadAll() {
        lock.readLock().lock();
        try {
            if (Files.notExists(storagePath)) return List.of();

            String json = Files.readString(storagePath);
            List<T> map = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
            log.info("Loaded {} objects from {}", map.size(), storagePath.getFileName());
            return map;
        } catch (Exception e) {
            log.error("Error loading data from {}: {}", storagePath, e.getMessage());
            return List.of();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void shutdown() {
        log.info("Shutting down persistence service for {}", storagePath.getFileName());
        writerExecutor.shutdown();
    }
}