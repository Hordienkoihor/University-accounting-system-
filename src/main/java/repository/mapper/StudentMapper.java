package repository.mapper;

import domain.Group;
import domain.Student;
import repository.dto.StudentDto;
import repository.mapper.interfaces.ObjectMapper;

import java.util.List;

public class StudentMapper implements ObjectMapper<Student, StudentDto, Group> {
    @Override
    public StudentDto toDto(Student student) {
        return null;
    }

    @Override
    public Student toEntity(StudentDto dto, List<Group> groups) {
        return null;
    }
}
