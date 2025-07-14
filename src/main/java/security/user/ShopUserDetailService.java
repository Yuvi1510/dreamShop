package security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yuvraj.main.exception.ResourceNotFoundException;
import com.yuvraj.main.models.User;
import com.yuvraj.main.repositories.UserRepository;


@Service
public class ShopUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "email", username));
		return ShopUserDetails.buildUserDetails(user);
	}

}
