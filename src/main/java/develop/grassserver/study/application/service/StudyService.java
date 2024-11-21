package develop.grassserver.study.application.service;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.study.domain.entity.Study;
import develop.grassserver.study.domain.entity.StudyRole;
import develop.grassserver.study.infrastructure.repository.StudyRepository;
import develop.grassserver.study.presentation.dto.CreateStudyRequest;
import develop.grassserver.study.presentation.dto.CreateStudyResponse;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService {
    private final StudyRepository studyRepository;

    @Transactional
    public CreateStudyResponse createStudy(Member member, CreateStudyRequest request) {
        Study study = Study.builder()
                .name(request.name())
                .goalMessage(request.goalMessage())
                .goalTime(request.goalTime())
                .inviteCode(generateUniqueInviteCode())
                .build();

        study.addMember(member, StudyRole.LEADER);
        studyRepository.save(study);

        return new CreateStudyResponse(study.getInviteCode());
    }

    private String generateUniqueInviteCode() {
        String code;
        do {
            code = generateRandomCode(6) + System.currentTimeMillis();
        } while (studyRepository.existsByInviteCode(code));
        return code;
    }

    private String generateRandomCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
