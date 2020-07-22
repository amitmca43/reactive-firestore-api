package user.api.model;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {	
		@Id	   
	    private int id;      
	 
		@Size(max = 100)
	    private String address1;

	    @Size(max = 100)
	    private String address2;

	    @Size(max = 100)
	    private String street;

	    @Size(max = 100)
	    private String city;

	    @Size(max = 100)
	    private String state;

	    @Size(max = 100)
	    private String country;
	  
	    @Size(max = 32)
	    private String zipCode;
}
