package user.api.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private String id;
	
	@NotBlank
	@Size(max = 140)
	private String name;

	@Size(max = 15)
	private String phoneNumber;
	
	private String gender;

	@NotNull
	private Date dateOfBirth;
	
	List<UserAddressDto> addresses;
}
