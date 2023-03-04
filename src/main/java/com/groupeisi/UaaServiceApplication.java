package com.groupeisi;

import com.groupeisi.dao.IAppRolesRepository;
import com.groupeisi.dao.IMyUserRepository;
import com.groupeisi.entities.AppRolesEntity;
import com.groupeisi.entities.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
//@EnableHystrix
public class UaaServiceApplication {
	@Autowired
	private IMyUserRepository userRepository;
	@Autowired
	private IAppRolesRepository rolesRepository;
	@PostConstruct
	public void initUsersAndRoles(){
		if (rolesRepository.findAll().isEmpty()) {
			rolesRepository.save(new AppRolesEntity(1, "ROLE_ADMIN"));
			rolesRepository.save(new AppRolesEntity(2, "ROLE_USER"));
			rolesRepository.save(new AppRolesEntity(3, "ROLE_SUPER_ADMIN"));
		}

		if(userRepository.findAll().isEmpty()){
			List<MyUser> users = Stream.of(
					new MyUser(101, "fmbow", "passer123", "mbow@gmail.com", rolesRepository.findById(1).orElseThrow()),
					new MyUser(102, "technique", "passer123", "madicke@gmail.com", rolesRepository.findById(2).orElseThrow())
			).collect(Collectors.toList());
			userRepository.saveAll(users);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(UaaServiceApplication.class, args);
	}

}
