package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;

public class BoardBody {
    @Setter
    @Getter
    private Long id;
    @Setter @Getter
    private String content;

    @Override
    public String toString() {
        return "BoardBody{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
