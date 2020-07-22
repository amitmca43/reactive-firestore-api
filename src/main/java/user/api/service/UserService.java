package user.api.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import user.api.dto.UserAddressDto;
import user.api.dto.UserDto;
import user.api.model.User;
import user.api.model.UserAddress;
import user.api.respository.UserRepository;

@Service
public class UserService implements IUserService {

	private final UserRepository userRepository;	
	private final ModelMapper mapper;

	public UserService(UserRepository userRepository, ModelMapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	@Override
	public Flux<UserDto> getAllUsers() {
		return userRepository.findAll().map(user -> mapper.map(user, UserDto.class));
	}

	@Override
	public Mono<List<UserAddressDto>> getAddressesByUserId(String userId) {
		return userRepository.findById(userId).map(user -> { 
			UserDto userDto = mapper.map(user, UserDto.class);
			return userDto.getAddresses();
		});				
	}
	
	@Override
	public Mono<UserDto> createUser(UserDto userDto) {
		User user = mapper.map(userDto, User.class);
		user.setId(UUID.randomUUID().toString());
		return userRepository.save(user).map(createdUser -> mapper.map(createdUser, UserDto.class));
	}

	@Override
	public Mono<UserDto> getUserById(String userId) {
		return userRepository.findById(userId).map(user -> mapper.map(user, UserDto.class));
	}

	@Override
	public Mono<UserDto> updateUser(String userId, UserDto user) {
		return userRepository.findById(userId).flatMap(existingUser -> {
			existingUser.setName(user.getName());
			existingUser.setPhoneNumber(user.getPhoneNumber());
			existingUser.setGender(user.getGender());
			return userRepository.save(existingUser).map(savedUser -> mapper.map(savedUser, UserDto.class));
		});
	}

	@Override
	public Mono<String> deleteUser(String userId) {
		return userRepository.findById(userId)
				.flatMap(existingUser -> userRepository.delete(existingUser).then(Mono.just(existingUser.getId())));
	}
}
