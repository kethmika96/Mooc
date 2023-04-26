package mocc.moocs_backend;

import mocc.moocs_backend.entities.Authority;
import mocc.moocs_backend.entities.User;
import mocc.moocs_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MoocsBackendApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(MoocsBackendApplication.class, args);
	}

	@PostConstruct
	protected void init() {
		List<Authority> authorityList= new ArrayList<>();
		authorityList.add(createAuthority("USER","User_role"));
		authorityList.add(createAuthority("ADMIN","Admin_role"));

		User user=new User();

		user.setUserName("kethmika");
		user.setFirstName("weee");
		user.setLastName("K");

		user.setPassword(passwordEncoder.encode("1234"));
		user.setEnabled(true);
		user.setAuthorities(authorityList);
		userRepository.save(user);


	}

	private Authority createAuthority(String roleCode,String roleDescription){
		Authority authority = new Authority();
		authority.setRoleCode(roleCode);
		authority.setRoleDescription(roleDescription);
		return authority;
	}

}
