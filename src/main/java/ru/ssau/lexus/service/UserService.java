/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-custom:src/main/java/service/Service.e.vm.java
 */
package ru.ssau.lexus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.lexus.domain.dto.UserDto;
import ru.ssau.lexus.domain.entity.Role;
import ru.ssau.lexus.domain.entity.User;
import ru.ssau.lexus.domain.mapper.UserMapper;
import ru.ssau.lexus.repository.crud.GroupRepository;
import ru.ssau.lexus.repository.crud.RoleRepository;
import ru.ssau.lexus.repository.crud.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    protected final UserRepository repository;
    protected final UserMapper mapper;
    protected final PasswordEncoder passwordEncoder;
    protected final RoleRepository roleRepository;
    protected final GroupRepository groupRepository;

    @Value("${password.salt:0}")
    public long salt;

    /**
     * Save a User.
     *
     * @param dto the entity to save.
     * @return the persisted entity.
     */
    public UserDto save(UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword() + salt));
        User entity = mapper.dtoToEntity(dto);
        //groupRepository.findById(dto.getIdGroup()).ifPresent(entity::setIdGroup);
        entity = repository.save(entity);
        return mapper.entityToDto(entity);
    }

    /**
     * Get all the Users.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Collection<UserDto> findAll() {
        return repository.findAll().stream().map(mapper::entityToDto).collect(Collectors.toList());
    }

    /**
     * Get one User by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserDto> findOne(Integer id) {
        return repository.findById(id).map(mapper::entityToDto);
    }

    /**
     * Delete the anodeHist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repository.findByName(s).orElseThrow(() -> new UsernameNotFoundException("user not found!"));
        List<Role> roles = roleRepository.findAllByIdUser(user);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList()));
    }
}