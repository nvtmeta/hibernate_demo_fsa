package fsa.training.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "department")
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    private Set<Employee> employees;


}
