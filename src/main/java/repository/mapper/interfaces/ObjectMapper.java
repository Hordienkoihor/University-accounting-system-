package repository.mapper.interfaces;

import java.util.List;
import java.util.Objects;

public interface ObjectMapper<E, D, L> {

    D toDto(E t);
    E toEntity(D dto, List<L> linkedEntities);
}
