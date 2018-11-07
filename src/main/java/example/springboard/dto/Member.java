package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Member {
    @Setter
    @Getter
    private Long id;
    @Setter @Getter
    private String name;
    @Setter @Getter
    private String email;
    @Setter @Getter
    private String password;
}
