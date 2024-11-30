package develop.grassserver.study.application.service;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.study.application.exception.InvalidInviteCodeException;
import develop.grassserver.study.application.exception.NotAStudyMemberException;
import develop.grassserver.study.domain.entity.Study;
import develop.grassserver.study.domain.entity.StudyRole;
import develop.grassserver.study.infrastructure.repository.StudyMemberRepository;
import develop.grassserver.study.infrastructure.repository.StudyRepository;
import develop.grassserver.study.presentation.dto.CreateStudyRequest;
import develop.grassserver.study.presentation.dto.CreateStudyResponse;
import develop.grassserver.study.presentation.dto.FindAllStudyResponse;
import develop.grassserver.study.presentation.dto.StudyDetailResponse;
import develop.grassserver.study.presentation.dto.StudySummaryResponse;
import jakarta.persistence.EntityNotFoundException;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;

    private final int RANDOM_CODE_LENGTH = 6;

    public StudyDetailResponse getStudyDetail(Member member, Long studyId) {
        if (!studyMemberRepository.existsByMemberIdAndStudyId(member.getId(), studyId)) {
            throw new NotAStudyMemberException();
        }

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 스터디를 찾을 수 없습니다."));

        return StudyDetailResponse.from(study);
    }

    public FindAllStudyResponse getAllStudies(Member member) {
        List<Object[]> results = studyRepository.findStudiesWithMemberCountByMemberId(member.getId());

        List<StudySummaryResponse> regularStudies = results.stream()
                .map(StudySummaryResponse::from)
                .collect(Collectors.toUnmodifiableList());

        return new FindAllStudyResponse(regularStudies);
    }

    @Transactional
    public void enterStudyWithInviteCode(Member member, String inviteCode) {
        Study study = studyRepository.findStudyByInviteCode(inviteCode).orElseThrow(InvalidInviteCodeException::new);
        study.addMember(member, StudyRole.MEMBER);
        studyRepository.save(study);
    }

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
            code = generateRandomCode() + System.currentTimeMillis();
        } while (studyRepository.existsByInviteCode(code));
        return code;
    }

    private String generateRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(RANDOM_CODE_LENGTH);
        for (int i = 0; i < RANDOM_CODE_LENGTH; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
