package develop.grassserver.study.application.service;

import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.study.application.exception.NotAStudyMemberException;
import develop.grassserver.study.domain.entity.Study;
import develop.grassserver.study.infrastructure.repository.StudyMemberRepository;
import develop.grassserver.study.infrastructure.repository.StudyRepository;
import develop.grassserver.study.presentation.dto.FindAllStudyResponse;
import develop.grassserver.study.presentation.dto.StudyDetailResponse;
import develop.grassserver.study.presentation.dto.StudySummaryResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyQueryService {

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
}
