package com.acquia.sqlcrud.repository;

import com.acquia.sqlcrud.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s")
    List<Student> findAllStudents();

    @Query("SELECT s FROM Student s WHERE s.id = :id")
    Optional<Student> findStudentById(@Param("id") Long id);

    @Transactional
    @Query(value = "INSERT INTO Student (id, first_name, last_name, email, course) VALUES (:id, :firstName, :lastName, :email, :course) RETURNING *", nativeQuery = true)
    Student saveStudent(@Param("id") Long id,@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("course") String course);

    @Transactional
    @Query(value = "UPDATE Student SET first_name=:firstName, last_name=:lastName, email=:email, course=:course WHERE id=:id RETURNING *", nativeQuery = true)
    Optional<Student> updateStudent(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("course") String course);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Student WHERE id = :id", nativeQuery = true)
    int deleteStudent(@Param("id") Long id);

}