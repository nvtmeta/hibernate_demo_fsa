package fsa.training.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class DepartmentSummaryDTO {
    private String departmentName;
    private Long totalEmployee;

    public DepartmentSummaryDTO(String departmentName, Long totalEmployee) {
        this.departmentName = departmentName;
        this.totalEmployee = totalEmployee;
    }
}
