package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberPermission {
    private Long memberId;
    private Long permissionId;
}
