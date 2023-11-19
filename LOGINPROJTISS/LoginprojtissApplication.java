package com.example.LOGINPROJTISS;

// SE IMPORTAN LOS SPRINGS NECESARIOS

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@SpringBootApplication

// Estructura del login de la app tissu

public class LoginprojtissApplication 

// Email requerimiento mensaje de ingreso y solicitud

{@NotNull(message = "Email is required.")
	@Size(min = 1, message = "Email is required.")
	@Email(message = "Email must have a @ sign")

	private String email;

	// Password requerimiento mensaje de ingreso y soicitud
	
	@NotNull(message = "Password is required.")
	@Size(min = 6, message = "Password should be atleast 8 characters.")

	private String password;

	// Obtener email

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	// Obtener contraseña

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

// Error response excepciones

	public static void main(String[] args) {
		SpringApplication.run(LoginprojtissApplication.class, args);
	}
	
	package com.mediumstory.springbootfieldvalidation.exceptions;

import java.util.List;

// Errores 

public class FieldErrorResponse {
	private List<CustomFieldError> fieldErrors;

	public FieldErrorResponse() {
	}

	public List<CustomFieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<CustomFieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
//error handler

package com.mediumstory.springbootfieldvalidation.exceptions.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mediumstory.springbootfieldvalidation.exceptions.CustomFieldError;
import com.mediumstory.springbootfieldvalidation.exceptions.FieldErrorResponse;

@ControllerAdvice
public class ControllerErrorHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		FieldErrorResponse fieldErrorResponse = new FieldErrorResponse();

		List<CustomFieldError> fieldErrors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			CustomFieldError fieldError = new CustomFieldError();
			fieldError.setField(((FieldError) error).getField());
			fieldError.setMessage(error.getDefaultMessage());
			fieldErrors.add(fieldError);
		});

		fieldErrorResponse.setFieldErrors(fieldErrors);
		return new ResponseEntity<>(fieldErrorResponse, status);
	}

	//login response 

}

package com.mediumstory.springbootfieldvalidation.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediumstory.springbootfieldvalidation.dto.LoginRequestDTO;
import com.mediumstory.springbootfieldvalidation.dto.LoginResponseDTO;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3006")
public class LoginController {

// Mensaje de ingreso exitoso

	@PostMapping
	public ResponseEntity<LoginResponseDTO> performLogin(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
		return ResponseEntity.ok(new LoginResponseDTO("Success !"));
	}
}

// login response validation

package com.mediumstory.springbootfieldvalidation.dto;

public class LoginResponseDTO {
	private String message;

	public LoginResponseDTO(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}


