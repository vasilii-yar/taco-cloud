package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.User;
import tacos.data.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
	private UserRepository userRep;
	
	@Autowired
	public UserRepositoryUserDetailsService(UserRepository usR) {
		this.userRep = usR;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRep.findByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("User '" + username + "' not found!");
		}
	}
	
	
}
