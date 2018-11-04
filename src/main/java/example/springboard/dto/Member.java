package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;

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



    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
