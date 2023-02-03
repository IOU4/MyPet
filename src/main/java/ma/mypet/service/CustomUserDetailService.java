// package ma.mypet.service;
//
// import java.util.Collections;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import
// org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
//
// import ma.mypet.model.UserDTO;
//
// @Service
// public class CustomUserDetailService implements UserDetailsService {
//
// @Autowired
// UserService userService;
//
// @Override
// public UserDetails loadUserByUsername(String email) throws
// UsernameNotFoundException {
// UserDTO user = userService.findByEmail(email);
// var authorities = Collections.singleton(new SimpleGrantedAuthority("user"));
// return new User(user.getEmail(), user.getPassword(), authorities);
// }
//
// }
