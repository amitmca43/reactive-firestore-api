package user.api.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.cloud.gcp.data.firestore.Document;
import com.google.cloud.firestore.annotation.DocumentId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collectionName = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User { 
	@DocumentId	
	private String id;

	@NotBlank
	@Size(max = 140)
	private String name;

	@Size(max = 15)
	private String phoneNumber;
	
	private String gender;

	@NotNull
	private Date dateOfBirth;
	
	List<UserAddress> addresses;
}
