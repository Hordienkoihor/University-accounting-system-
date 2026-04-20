package repository.mapper;

import domain.*;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import domain.records.StudentId;
import repository.*;
import repository.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyDepartmentPersonLinker {
    public void loadUniversityData(
            FacultyRepository facultyRepo,
            DepartmentRepository deptRepo,
            StaffRepository staffRepo,
            SpecialityRepository specialityRepo,
            GroupRepository groupRepo,
            StudentRepository studentRepo
    ) {

        DepartmentMapper departmentMapper = new DepartmentMapper();
        TeacherMapper teacherMapper = new TeacherMapper();
        FacultyMapper facultyMapper = new FacultyMapper();

        List<FacultyDto> facultyDtos = facultyRepo.loadRawDtos();
        List<DepartmentDto> deptDtos = deptRepo.loadRawDtos();
        List<TeacherDto> teacherDtos = staffRepo.loadRawDtos();


        Map<String, Faculty> faculties = new HashMap<>();
        for (FacultyDto dto : facultyDtos) {
            Faculty f = facultyMapper.toEntity(dto, null);
            faculties.put(f.getCode(), f);
        }

        Map<String, Department> departments = new HashMap<>();
        for (DepartmentDto dto : deptDtos) {
            Department d = departmentMapper.toEntity(dto, faculties.values().stream().toList());
            departments.put(d.getCode(), d);
        }

        Map<StaffId, Teacher> teachers = new HashMap<>();
        for (TeacherDto dto : teacherDtos) {
            Teacher t = teacherMapper.toEntity(dto, departments.values().stream().toList());
            teachers.put(new StaffId(dto.getStaffId()), t);
        }


        for (FacultyDto dto : facultyDtos) {
            if (dto.getDeanId() != null) {
                Faculty f = faculties.get(dto.getCode());
                Teacher dean = teachers.get(new StaffId(dto.getDeanId()));
                f.setDean(dean);
            }
        }

        for (DepartmentDto dto : deptDtos) {
            if (dto.getHeadId() != null) {
                Department d = departments.get(dto.getCode());
                Teacher head = teachers.get(new StaffId(dto.getHeadId()));
                d.setHeadOfDepartment(head);
            }
        }

        facultyRepo.initData(faculties.values().stream().toList());
        deptRepo.initData(departments.values().stream().toList());

        List<Staff> staffList = teachers.values().stream().map(t -> (Staff) t).toList();
        staffRepo.initData(staffList);

        SpecialityMapper specialtyMapper = new SpecialityMapper();
        GroupMapper groupMapper = new GroupMapper();
        StudentMapper studentMapper = new StudentMapper();

        List<SpecialityDto> specialtyDtos = specialityRepo.loadRawDtos();
        List<GroupDto> groupDtos = groupRepo.loadRawDtos();
        List<StudentDto> studentDtos = studentRepo.loadRawDtos();

        Map<String, Specialty> specialties = new HashMap<>();
        for (SpecialityDto dto : specialtyDtos) {
            Specialty s = specialtyMapper.toEntity(dto, departments.values().stream().toList());
            specialties.put(s.getTag(), s);
        }

        Map<String, Group> groups = new HashMap<>();
        for (GroupDto dto : groupDtos) {
            Group g = groupMapper.toEntity(dto, specialties.values().stream().toList());
            groups.put(g.getName(), g);
        }

        Map<StudentId, Student> students = new HashMap<>();
        for (StudentDto dto : studentDtos) {
            Student s = studentMapper.toEntity(dto, groups.values().stream().toList());
            students.put(s.getStudentId(), s);
        }


        specialityRepo.initData(specialties.values().stream().toList());
        groupRepo.initData(groups.values().stream().toList());
        studentRepo.initData(students.values().stream().toList());

    }
}