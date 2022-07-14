package com.example.studentapp.student;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudent() {
        return studentRepository.findAll();
    }

    public void postStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email is token");
        }
        studentRepository.save(student);
    }

    public void deleteStudentById(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isEmpty()) {
            throw new IllegalStateException("this id isn't exists!");
        }
        studentRepository.deleteById(id);
    }

    public ResponseEntity<Student> updateStudentById(long id, Student student) {
        Student updateStudent = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("This id is not exists!"));
        updateStudent.setName(student.getName());
        updateStudent.setEmail(student.getEmail());
        updateStudent.setPhoneNumber(student.getPhoneNumber());

        studentRepository.save(updateStudent);

        return ResponseEntity.ok(updateStudent);
    }

}
