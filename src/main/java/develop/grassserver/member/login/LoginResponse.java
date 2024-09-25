package develop.grassserver.member.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record LoginResponse(Long id, String name, String email) {
}
