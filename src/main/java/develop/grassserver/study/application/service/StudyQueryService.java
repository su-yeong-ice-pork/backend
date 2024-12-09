package develop.grassserver.study.application.service;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        List<Study> studies = studyRepository.findStudiesByMemberId(member.getId());

        List<StudySummaryResponse> regularStudies = studies.stream()
                .map(study -> {
                    Long memberCount = studyRepository.countMembersByStudyId(study.getId());
                    return StudySummaryResponse.from(study, memberCount);
                })
                .collect(Collectors.toUnmodifiableList());

        return new FindAllStudyResponse(regularStudies);
    }


    public FindAllStudyMembersResponse getAllStudyMembers(Member member, Long studyId) {
        validStudyMember(member, studyId);

        List<StudyMember> studyMembers = studyMemberRepository.findAllByStudyId(studyId);
        Pair<List<Long>, Map<Long, StudyRole>> idsAndRoles = getStudyMemberIdsAndRoles(studyMembers);
        MemberStudyInfoDTO studyInfo = memberStudyInfoService.getMemberStudyInfo(idsAndRoles.getFirst());

        return FindAllStudyMembersResponse.from(studyInfo, idsAndRoles.getSecond());
    }

    private Pair<List<Long>, Map<Long, StudyRole>> getStudyMemberIdsAndRoles(List<StudyMember> studyMembers) {
        List<Long> studyMemberIds = new ArrayList<>();
        Map<Long, StudyRole> roles = new HashMap<>();

        studyMembers.forEach(studyMember -> {
            Long id = studyMember.getMember().getId();
            studyMemberIds.add(id);
            roles.put(id, studyMember.getRole());
        });

        return Pair.of(studyMemberIds, roles);
    }
}
