package hu.webuni.userservice.mapper;

import org.mapstruct.Mapper;

import hu.webuni.userservice.api.model.UserDto;
import hu.webuni.userservice.model.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	Users DtoToUser(UserDto userDto);
	
	UserDto userToDto(Users user);

}
