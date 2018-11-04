package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberPermission {
    @Setter
    @Getter
    private Long memberId;
    @Setter @Getter
    private Long permissionId;
}
