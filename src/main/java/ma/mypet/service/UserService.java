package ma.mypet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ma.mypet.domain.User;
import ma.mypet.model.UserCreationDTO;
import ma.mypet.model.UserDTO;
import ma.mypet.repos.UserRepository;
import ma.mypet.util.NotFoundException;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserCreationMapper userCreationMapper;

  public List<UserDTO> findAll() {
    final List<User> users = userRepository.findAll(Sort.by("id"));
    return users.stream()
        .map((user) -> mapToDTO(user, new UserDTO()))
        .collect(Collectors.toList());
  }

  public UserDTO get(final Long id) {
    return userRepository.findById(id)
        .map(user -> mapToDTO(user, new UserDTO()))
        .orElseThrow(() -> new NotFoundException());
  }

  public Long create(final UserCreationDTO userDTO) {
    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    final User user = userCreationMapper.apply(userDTO);
    return userRepository.save(user).getId();
  }

  public void update(final Long id, final UserDTO userDTO) {
    final User user = userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
    mapToEntity(userDTO, user);
    userRepository.save(user);
  }

  public void delete(final Long id) {
    userRepository.deleteById(id);
  }

  public UserDTO mapToDTO(final User user, final UserDTO userDTO) {
    userDTO.setId(user.getId());
    userDTO.setName(user.getName());
    userDTO.setEmail(user.getEmail());
    userDTO.setAddress(user.getAddress());
    userDTO.setPhone(user.getPhone());
    return userDTO;
  }

  public User mapToEntity(final UserDTO userDTO, final User user) {
    user.setName(userDTO.getName());
    user.setEmail(userDTO.getEmail());
    user.setAddress(userDTO.getAddress());
    user.setPhone(userDTO.getPhone());
    return user;
  }

  public boolean emailExists(final String email) {
    return userRepository.existsByEmailIgnoreCase(email);
  }

  public boolean phoneExists(final String phone) {
    return userRepository.existsByPhoneIgnoreCase(phone);
  }

  public UserDTO getByName(String name) {
    return userRepository.findByName(name).map(user -> mapToDTO(user, new UserDTO()))
        .orElseThrow(() -> new NotFoundException("no user found with name: " + name));
  }

  public UserDTO findByEmail(String email) {
    return userRepository.findByEmail(email).map(user -> mapToDTO(user, new UserDTO()))
        .orElseThrow(() -> new NotFoundException("no user found with email " + email));
  }

}
