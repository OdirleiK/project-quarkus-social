package br.com.kmpx.quarkussocial.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class CreateUserResquest {
	
	@NotBlank(message = "name is required")
	private String name;
	@NotNull(message = "age is required")
	private Integer age;
	
}
