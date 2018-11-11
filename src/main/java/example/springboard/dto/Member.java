package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class Member {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<Permission> permission;
}
