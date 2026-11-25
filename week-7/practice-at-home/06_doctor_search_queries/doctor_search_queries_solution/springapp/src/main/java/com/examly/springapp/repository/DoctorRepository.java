package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecializationIgnoreCaseOrderByExperienceYearsDesc(String specialization);

    List<Doctor> findByNameContainingIgnoreCase(String keyword);

    List<Doctor> findByExperienceYearsBetween(Integer min, Integer max);

    List<Doctor> findBySpecializationIgnoreCaseAndExperienceYearsGreaterThanEqual(String specialization, Integer min);

    List<Doctor> findBySpecializationIgnoreCaseOrNameContainingIgnoreCase(String specialization, String keyword);

    @Query("SELECT x FROM Doctor x WHERE x.experienceYears > :min ORDER BY x.experienceYears DESC")
    List<Doctor> findAboveExperienceYears(@Param("min") Integer min);

    @Query(value = "SELECT * FROM doctors WHERE experience_years <= :max ORDER BY experience_years ASC", nativeQuery = true)
    List<Doctor> findUpToExperienceYearsNative(@Param("max") Integer max);

    @Modifying
    @Transactional
    @Query("UPDATE Doctor x SET x.status = :status WHERE LOWER(x.specialization) = LOWER(:specialization)")
    int updateStatusBySpecialization(@Param("specialization") String specialization, @Param("status") String status);
}
