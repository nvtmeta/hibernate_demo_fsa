package fsa.training.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "employee")
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nationalized
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    private EmployeeLevel level;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


}
