package mocc.moocs_backend.services;

import mocc.moocs_backend.entities.User;
import mocc.moocs_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        User user=userRepository.findByUserName(username);

        if(null==user) {
            throw new UsernameNotFoundException("User Not Found with userName "+username);
        }
        return user;
    }

}
