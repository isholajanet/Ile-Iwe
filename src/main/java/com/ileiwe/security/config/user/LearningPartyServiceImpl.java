package com.ileiwe.service.user;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.repository.LearningPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class LearningPartyServiceImpl implements UserDetailsService {

    @Autowired
    private LearningPartyRepository learningPartyRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        LearningParty user = learningPartyRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User with email does not exists");
        }
//        List<SimpleGrantedAuthority> authorities = user.getAuthorities().stream()
//                                                    .map(authority -> {
//                                                        return new SimpleGrantedAuthority
//                                                                (authority.getAuthority().name());
//                                                    }).collect(Collectors.toList());

        return new User(user.getEmail(), user.getPassword(),getAuthorities(user.getAuthorities()));
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Authority> authorities){
        return authorities.stream()
                .map(authority -> {
                    return new SimpleGrantedAuthority(authority.getAuthority().name());
                }).collect(Collectors.toList());
    }
}