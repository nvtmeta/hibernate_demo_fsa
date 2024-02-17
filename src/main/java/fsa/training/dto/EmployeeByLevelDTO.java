package fsa.training.dto;

import fsa.training.entity.EmployeeLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeByLevelDTO {

   private EmployeeLevel level;
   private Long totalEmployee;

   public EmployeeByLevelDTO(EmployeeLevel level, Long totalEmployee)
   {
      this.level = level;
      this.totalEmployee = totalEmployee;
   }

}
