package develop.grassserver.member.presentation.dto;

import develop.grassserver.member.domain.entity.Major;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.domain.entity.StudyRecord;
import develop.grassserver.profile.domain.entity.Freeze;
import develop.grassserver.profile.domain.entity.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(title = "회원가입 요청 DTO")
public record MemberJoinRequest(

        @Schema(description = "회원가입 이메일", example = "example@pusan.ac.kr")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @Schema(description = "회원가입 비밀번호", example = "김김진진우우")
        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        String password,

        @Schema(description = "회원가입 이름", example = "김김진진우우")
        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        String college,
        String department
) {
        public Member toMemberEntity(MemberJoinRequest request, String password, Profile profile) {
                return Member.builder()
                        .name(request.name())
                        .email(request.email())
                        .major(new Major(request.college(), request.department()))
                        .profile(profile)
                        .password(password)
                        .studyRecord(StudyRecord.builder().build())
                        .build();
        }

        public Profile toProfileEntity() {
                return Profile.builder()
                        .image(Profile.DEFAULT_PROFILE_IMAGE)
                        .message(Profile.DEFAULT_PROFILE_MESSAGE)
                        .freeze(new Freeze())
                        .mainTitle(Profile.DEFAULT_TITLE_NAME)
                        .mainBanner(Profile.DEFAULT_BANNER_IMAGE)
                        .build();
        }
}
