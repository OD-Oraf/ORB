package com.odoraf.resumebuilder;

import com.odoraf.resumebuilder.models.MyUserDetails;
import com.odoraf.resumebuilder.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Implementation of Spring UserDetailsService Interface
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    //UserDetails is returned bean
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        //if found, convert to user details class, spring security user details
        //Look up map for more details
        return user.map(MyUserDetails::new).get();
    }
}
