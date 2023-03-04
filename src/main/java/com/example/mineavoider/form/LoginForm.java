package com.example.mineavoider.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * hold the login data
 * 
 * @author araki
 *
 */
@Data
public class LoginForm {

		@NotBlank
		private String userId;
		
		@NotBlank
		private String password;

}
