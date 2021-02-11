package user.api.service.tests;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.hamcrest.core.IsAnything;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import org.modelmapper.ModelMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import user.api.dto.UserDto;
import user.api.model.User;
import user.api.repository.UserRepository;
import user.api.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

	UserRepository userRepositoryMock;
	UserService userService;

	@BeforeEach
	public void beforeEach() {
		userRepositoryMock = mock(UserRepository.class);
		userService = new UserService(userRepositoryMock, new ModelMapper());
	}

	@Test
	public void testGetAllUsersReturnsUsers() {

		var users = getUsers();
		
		userRepositoryMock = mock(UserRepository.class);
		UserService userService = new UserService(userRepositoryMock, new ModelMapper());

		when(userRepositoryMock.findAll()).thenReturn(Flux.fromIterable(users));

		Flux<UserDto> usersReturned = userService.getAllUsers();

		StepVerifier.create(usersReturned)
				.assertNext(udto1 -> assertThat(udto1.getName()).isEqualTo(users.get(0).getName()))
				.assertNext(udto2 -> assertThat(udto2.getName()).isEqualTo(users.get(1).getName())).verifyComplete();

	}
	
	@Test
	public void testGetUserByIdReturnsUser() {
		var user1 = new User();
		user1.setName("Name1");
		
		var userId = "userId1";
				
		userRepositoryMock = mock(UserRepository.class);
		UserService userService = new UserService(userRepositoryMock, new ModelMapper());

		when(userRepositoryMock.findById(userId)).thenReturn(Mono.just(user1));

		UserDto userDto = userService.getUserById(userId).block();
		
		assertThat(userDto.getName()).isEqualTo(user1.getName());		
	}
	
	@Test
	public void testCreateUserReturnsUserWithId() {	
		
		var user = new User();
		user.setName("Name1");
		
		var userDto = new UserDto();
		user.setName("Name1");
						
		userRepositoryMock = mock(UserRepository.class);
		UserService userService = new UserService(userRepositoryMock, new ModelMapper());
		
		UserDto createdUser = userService.createUser(userDto).block();
		
		assertThat(createdUser.getName()).isEqualTo(userDto.getName());		
	}

	private List<User> getUsers() {
		var user1 = new User();
		user1.setName("Name1");

		var user2 = new User();
		user2.setName("Name1");

		List<user.api.model.User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);

		return users;
	}
}
