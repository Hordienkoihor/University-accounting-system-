package repository.mapper;

import domain.Group;
import domain.Student;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import repository.dto.StudentDto;
import repository.mapper.interfaces.ObjectMapper;

import java.util.List;

public class StudentMapper implements ObjectMapper<Student, StudentDto, Group> {
    @Override
    public StudentDto toDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setSurname(student.getSurname());
        studentDto.setFatherName(student.getFatherName());
        studentDto.setEmail(student.getEmail());
        studentDto.setPhoneNumber(student.getPhoneNumber());
        studentDto.setStudentId(student.getStudentId());
        studentDto.setCourse(student.getCourse());

        if (student.getStudyForm() != null) {
            studentDto.setStudyForm(student.getStudyForm().name());
        }
        if (student.getStudyStatus() != null) {
            studentDto.setStudyStatus(student.getStudyStatus().name());
        }

        if (student.getGroup() != null) {
            studentDto.setGroupName(student.getGroup().getName());
        }

        return studentDto;
    }

    @Override
    public Student toEntity(StudentDto dto, List<Group> groups) {
        if (dto == null) return null;

        Student student = new Student();
        student.setName(dto.getName());
        student.setSurname(dto.getSurname());
        student.setFatherName(dto.getFatherName());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setCourse(dto.getCourse());

        student.setStudyStatus(StudyStatus.getStudyStatus(dto.getStudyStatus()));
        student.setStudyForm(StudyForm.getStudyForm(dto.getStudyForm()));


        if (dto.getGroupName() != null) {
            Group studentGroup = groups.stream()
                    .filter(g -> g.getName().equals(dto.getGroupName()))
                    .findFirst()
                    .orElse(null);
            student.setGroup(studentGroup);
        }

        return student;
    }
}
