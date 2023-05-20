package com.Bikkadit.blog.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.Bikkadit.blog.entities.Post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

public class UserDto {

	private Integer userId;
	
	@NotBlank
	@Size(min = 4, message = "Name must be Of 4 chracters")
	private String name;

	@Email(message = "Email Address is not valid")
	private String email;
	
	
	@NotBlank
	@Size(min = 4, max = 10, message = "Password must be minimum of 3 and maximum of 6 characters")
	@Pattern(regexp="[a-z][a-zA-Z]*[0-9]+{6}",message="length must be 6")  
	private String password;
	
	@NotBlank
	private String about;
	
	private Set<RolesDto> roles=new HashSet<>();
	
	

}
