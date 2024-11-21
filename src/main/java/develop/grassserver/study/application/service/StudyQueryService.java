package develop.grassserver.study.application.service;

import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.grass.application.service.MemberStudyInfoService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.study.application.exception.NotAStudyMemberException;
import develop.grassserver.study.domain.entity.Study;
import develop.grassserver.study.domain.entity.StudyMember;
import develop.grassserver.study.domain.entity.StudyRole;
import develop.grassserver.study.infrastructure.repository.StudyMemberRepository;
import develop.grassserver.study.infrastructure.repository.StudyRepository;
import develop.grassserver.study.presentation.dto.FindAllStudyMembersResponse;
import develop.grassserver.study.presentation.dto.FindAllStudyResponse;
import develop.grassserver.study.presentation.dto.StudyDetailResponse;
import develop.grassserver.study.presentation.dto.StudySummaryResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyQueryService {

    private final MemberStudyInfoService memberStudyInfoService;

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;

    public StudyDetailResponse getStudyDetail(Member member, Long studyId) {
        validStudyMember(member, studyId);

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 스터디를 찾을 수 없습니다."));

        return StudyDetailResponse.from(study);
    }

    private void validStudyMember(Member member, Long studyId) {
        if (!studyMemberRepository.existsByMemberIdAndStudyId(member.getId(), studyId)) {
            throw new NotAStudyMemberException();
        }
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

    public FindAllStudyMembersResponse getAllStudyMember(Member member, Long studyId) {
        validStudyMember(member, studyId);

        List<StudyMember> studyMembers = studyMemberRepository.findAllByStudyId(studyId);

        List<Long> studyMemberIds = new ArrayList<>();
        Map<Long, StudyRole> roles = new HashMap<>();

        studyMembers.forEach(studyMember -> {
            Long id = studyMember.getMember().getId();
            studyMemberIds.add(id);
            roles.put(id, studyMember.getRole());
        });

        MemberStudyInfoDTO studyInfo = memberStudyInfoService.getMemberStudyInfo(studyMemberIds);

        return FindAllStudyMembersResponse.from(studyInfo, roles);
    }
}
