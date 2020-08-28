package com.sylvain.chat.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sylvain.chat.system.representation.UserPrivateRepresentation;
import com.sylvain.chat.system.representation.UserPublicRepresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "user")
public class User extends AbstractAuditBase {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column
    @JsonIgnore
    private String email;

    @Column(columnDefinition = "bit default 1")
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

    public UserPublicRepresentation toUserPublicRepresentation(){
        return UserPublicRepresentation.builder()
                .name(this.name)
                .username(this.username)
                .enabled(this.enabled).build();
    }

    public UserPrivateRepresentation toUserPrivateRepresentation(){
        return UserPrivateRepresentation.builder()
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .createdAt(this.getCreatedAt())
                .enabled(this.enabled).build();
    }

}
