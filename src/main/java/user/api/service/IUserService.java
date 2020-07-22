package user.api.service;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import user.api.dto.UserAddressDto;
import user.api.dto.UserDto;

public interface IUserService {

	Flux<UserDto> getAllUsers();
	
	Mono<List<UserAddressDto>> getAddressesByUserId(String userId);

	Mono<UserDto> createUser(UserDto user);

	Mono<UserDto> getUserById(String userId);

	Mono<UserDto> updateUser(String userId, UserDto user);

	Mono<String> deleteUser(String userId);

}