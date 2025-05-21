package com.ofektom.med.repositroy;

import com.ofektom.med.enums.Role;
import com.ofektom.med.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String userInput);
    Page<User> findByUserRole(Role role, Pageable pageable);
    List<User> findByUserRole(Role role);
    boolean existsByUserRole(Role role);

    Optional<List<User>> findByFirstNameContainingIgnoringCaseOrLastNameContainingIgnoringCase(String firstName, String lastName);
    Optional<List<User>> findByEmailContainingIgnoringCaseOrMembershipNoContainingIgnoringCase(String email, String membershipNo);
    Optional<List<User>> findByDesignationContainingIgnoringCaseOrDepartmentContainingIgnoringCase(
            String designation, String department);
}
