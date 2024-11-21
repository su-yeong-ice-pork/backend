package develop.grassserver.study.application.service;

import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.study.application.service.exception.NotAStudyMemberException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService {
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;

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
                .map(result -> {
                    Study study = (Study) result[0];
                    Long memberCount = (Long) result[1];
                    return new StudySummaryResponse(
                            study.getId(),
                            study.getName(),
                            memberCount.intValue(),
                            DurationUtils.formatHourDuration(study.getTotalStudyTime())
                    );
                })
                .collect(Collectors.toList());

        return new FindAllStudyResponse(regularStudies);
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
