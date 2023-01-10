package ma.mypet.service;

import java.util.List;
import java.util.stream.Collectors;
import ma.mypet.domain.Adoption;
import ma.mypet.domain.User;
import ma.mypet.model.UserDTO;
import ma.mypet.repos.AdoptionRepository;
import ma.mypet.repos.UserRepository;
import ma.mypet.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final AdoptionRepository adoptionRepository;

    public UserService(final UserRepository userRepository,
            final AdoptionRepository adoptionRepository) {
        this.userRepository = userRepository;
        this.adoptionRepository = adoptionRepository;
    }

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

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
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

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        userDTO.setAdoptedByUser(user.getAdoptedByUser() == null ? null : user.getAdoptedByUser().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        final Adoption adoptedByUser = userDTO.getAdoptedByUser() == null ? null : adoptionRepository.findById(userDTO.getAdoptedByUser())
                .orElseThrow(() -> new NotFoundException("adoptedByUser not found"));
        user.setAdoptedByUser(adoptedByUser);
        return user;
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public boolean phoneExists(final String phone) {
        return userRepository.existsByPhoneIgnoreCase(phone);
    }

}
