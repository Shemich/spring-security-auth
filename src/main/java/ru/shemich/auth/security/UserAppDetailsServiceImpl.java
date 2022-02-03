package ru.shemich.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shemich.auth.model.UserApp;
import ru.shemich.auth.repository.UserAppRepository;

@Service("userAppDetailsServiceImpl")
public class UserAppDetailsServiceImpl implements UserDetailsService {

    private final UserAppRepository userAppRepository;

    @Autowired
    public UserAppDetailsServiceImpl(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserApp userApp = userAppRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("Application doesn`t exists"));
        return SecurityUserApp.fromUserApp(userApp);
    }
}
