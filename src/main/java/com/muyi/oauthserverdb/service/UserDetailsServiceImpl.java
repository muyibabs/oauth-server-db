package com.muyi.oauthserverdb.service;

import com.muyi.oauthserverdb.model.AuthUserDetail;
import com.muyi.oauthserverdb.model.User;
import com.muyi.oauthserverdb.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userDetailRepository.findByUsername(s);
        user.orElseThrow(() -> new UsernameNotFoundException("Username/password not found"));
        AuthUserDetail userDetail = new AuthUserDetail(user.get());

        new AccountStatusUserDetailsChecker().check(userDetail);

        return userDetail;
    }
}
