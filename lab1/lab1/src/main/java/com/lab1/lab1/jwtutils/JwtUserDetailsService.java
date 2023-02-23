package com.lab1.lab1.jwtutils;

import lombok.AllArgsConstructor;
import com.lab1.lab1.model.UserDto;
import com.lab1.lab1.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService
{

    private ModelMapper mapper;
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User details = userRepository.findByLogin(login)
                .map(found ->
                        mapper.map(found, UserDto.class))
                .map(foundDto ->
                        new User(foundDto.getLogin(), foundDto.getPasswordHash(), assignRoles(foundDto)))
                .orElse(null);
        if (details == null)
            throw new UsernameNotFoundException("User not found with login: " + login);
        return details;
    }

    private List<GrantedAuthority> assignRoles(final UserDto user)
    {
        final List<GrantedAuthority> result = new LinkedList<>();
        result.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return result;
    }

}
