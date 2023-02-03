
package ma.mypet.service;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import ma.mypet.domain.User;
import ma.mypet.model.UserCreationDTO;

@Service
public class UserCreationMapper implements Function<UserCreationDTO, User> {

  @Override
  public User apply(UserCreationDTO u) {
    var user = new User();
    user.setName(u.getName());
    user.setEmail(u.getEmail());
    user.setAddress(u.getAddress());
    user.setPhone(u.getPhone());
    user.setPassword(u.getPassword());
    return user;
  }

}
