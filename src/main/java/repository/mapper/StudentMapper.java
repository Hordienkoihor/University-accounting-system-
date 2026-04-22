package repository.mapper;

import domain.Group;
import domain.Student;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import domain.records.PersonId;
import domain.records.StudentId;
import repository.dto.StudentDto;
import repository.mapper.interfaces.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

public class StudentMapper implements ObjectMapper<Student, StudentDto, Group> {
    @Override
    public StudentDto toDto(Student student) {
        StudentDto studentDto = new StudentDto();

        if (student.getId() != null) {
            studentDto.setId(student.getId().toString());
        }
        if (student.getStudentId() != null) {
            studentDto.setStudentId(student.getStudentId().toString());
        }

        if (student.getDateOfBirth() != null) {
            studentDto.setDateOfBirth(student.getDateOfBirth());
        }

        studentDto.setName(student.getName());
        studentDto.setSurname(student.getSurname());
        studentDto.setFatherName(student.getFatherName());
        studentDto.setEmail(student.getEmail());
        studentDto.setPhoneNumber(student.getPhoneNumber());
        studentDto.setStudentId(student.getStudentId().toString());
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

        if (dto.getId() != null) {
            student.setId(new PersonId(dto.getId()));
        }
        if (dto.getStudentId() != null) {
            student.setStudentId(new StudentId(dto.getStudentId()));
        }


        student.setDateOfBirth(dto.getDateOfBirth());

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
