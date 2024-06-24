//package com.example.messenger.security;
//
//import com.example.messenger.user.User;
//import com.example.messenger.user.UserDetail;
//import com.example.messenger.user.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//@Service
//public class SecurityUserDetailService implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(username).get();
//        return UserDetail.builder()
//                .id(user.getId())
//                .password(user.getPassword())
//                .roles(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()))
//                .build();
//    }
//}
