package com.sylvain.chat.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class User extends AbstractAuditBase {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(nullable = false)
    @Max(value = 32, message = "{username.long}")
    @Min(value = 2, message = "{username.short}")
    private String username;

    @Column(nullable = false)
    @Max(value = 32, message = "{name.long}")
    private String name;

    @Column(nullable = false)
    @Pattern(regexp = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\\\W]+$)(?![A-Za-z\\\\W]+$)(?![A-Z0-9\\\\W]+$)[a-zA-Z0-9\\\\W]{8,}$",
    message = "{password.invalid}")
    @JsonIgnore
    private String password;

    @Column
    @JsonIgnore
    @Email(message = "{email.invalid}")
    private String email;

    @Column(columnDefinition = "tinyint(1) default 1")
    @JsonIgnore
    private Boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserRole> userRoles = new ArrayList<>();

    public List<SimpleGrantedAuthority> getRoles(){
        List<Role> roles = userRoles.stream().map(UserRole::getRole).collect(Collectors.toList());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName())));
        return authorities;
    }


}
