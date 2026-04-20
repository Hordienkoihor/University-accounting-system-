package repository.mapper;

import domain.Department;
import domain.Teacher;
import domain.records.PersonId;
import domain.records.StaffId;
import repository.dto.TeacherDto;
import repository.mapper.interfaces.ObjectMapper;

import java.util.List;

public class TeacherMapper implements ObjectMapper<Teacher, TeacherDto, Department> {

    @Override
    public TeacherDto toDto(Teacher teacher) {
        if (teacher == null) return null;

        TeacherDto dto = new TeacherDto();

        if (teacher.getId() != null) {
            dto.setId(teacher.getId().personId());
        }

        if (teacher.getStaffId() != null) {
            dto.setStaffId(teacher.getStaffId().staffId());
        }

        dto.setName(teacher.getName());
        dto.setSurname(teacher.getSurname());
        dto.setFatherName(teacher.getFatherName());
        dto.setDateOfBirth(teacher.getDateOfBirth());
        dto.setEmail(teacher.getEmail());
        dto.setPhoneNumber(teacher.getPhoneNumber());


        if (teacher.getDepartment() != null) {
            dto.setDepartmentCode(teacher.getDepartment().getCode());
        }

        dto.setUniversityPosition(teacher.getUniversityPosition());
        dto.setScientificDegree(teacher.getScientificDegree());
        dto.setDateOfHiring(teacher.getDateOfHiring());
        dto.setWeeklyHours(teacher.getWeeklyHours());
        dto.setHourlyRate(teacher.getHourlyRate());

        return dto;
    }

    @Override
    public Teacher toEntity(TeacherDto dto, List<Department> departments) {
        if (dto == null) return null;

        Teacher teacher = new Teacher();

        if (dto.getId() != null) {
            teacher.setId(new PersonId(dto.getId()));
        }

        if (dto.getStaffId() != null) {
            teacher.setStaffId(new StaffId(dto.getStaffId()));
        }

        teacher.setName(dto.getName());
        teacher.setSurname(dto.getSurname());
        teacher.setFatherName(dto.getFatherName());
        teacher.setDateOfBirth(dto.getDateOfBirth());
        teacher.setEmail(dto.getEmail());
        teacher.setPhoneNumber(dto.getPhoneNumber());


        if (dto.getDepartmentCode() != null && departments != null) {
            Department matchedDept = departments.stream()
                    .filter(d -> d.getCode().equals(dto.getDepartmentCode()))
                    .findFirst()
                    .orElse(null);
            teacher.setDepartment(matchedDept);
        }

        teacher.setUniversityPosition(dto.getUniversityPosition());
        teacher.setScientificDegree(dto.getScientificDegree());
        teacher.setDateOfHiring(dto.getDateOfHiring());
        teacher.setWeeklyHours(dto.getWeeklyHours());
        teacher.setHourlyRate(dto.getHourlyRate());

        return teacher;
    }
}