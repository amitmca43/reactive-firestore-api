package user.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import user.api.dto.UserAddressDto;
import user.api.dto.UserDto;
import user.api.service.IUserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private IUserService userService;

	@GetMapping
	public Flux<UserDto> getAllusers() {
		return userService.getAllUsers();
	}

	@PostMapping
	public Mono<ResponseEntity<UserDto>> createUser(@Valid @RequestBody UserDto user) {
		return userService.createUser(user).map(createdUser -> {
			URI uri = linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).toUri();
			return ResponseEntity.created(uri).body(createdUser);
		});
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable(value = "id") String userId) {
		return userService.getUserById(userId).map(user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/{id}/addresses")
	public Mono<ResponseEntity<List<UserAddressDto>>> getAddressesByUserId(@PathVariable(value = "id") String userId) {
		return userService.getAddressesByUserId(userId).map(userAddresses -> ResponseEntity.ok(userAddresses))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable(value = "id") String userId,
			@Valid @RequestBody UserDto user) {
		return userService.updateUser(userId, user)
				.map(updateUser -> new ResponseEntity<>(updateUser, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteUser(@PathVariable(value = "id") String userId) {
		return userService.deleteUser(userId).map(id ->  new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
