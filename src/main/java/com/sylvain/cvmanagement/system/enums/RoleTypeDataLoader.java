package com.sylvain.cvmanagement.system.enums;

import com.sylvain.cvmanagement.system.entity.Role;
import com.sylvain.cvmanagement.system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Wenzhuo Zhao on 27/10/2021.
 */
@Component
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class RoleTypeDataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleRepository.save(new Role(new Long("1"), RoleType.USER.getName(), RoleType.USER.getDescription()));
        roleRepository.save(new Role(new Long("2"), RoleType.TEMP_USER.getName(), RoleType.TEMP_USER.getDescription()));
        roleRepository.save(new Role(new Long("3"), RoleType.ADMIN.getName(), RoleType.ADMIN.getDescription()));
    }
}
