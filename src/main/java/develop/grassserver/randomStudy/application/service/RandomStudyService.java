package develop.grassserver.randomStudy.application.service;

import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.grass.application.service.MemberStudyInfoService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.application.exception.NotARandomStudyMemberException;
import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import develop.grassserver.randomStudy.domain.entity.RandomStudyMember;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyMemberRepository;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyRepository;
import develop.grassserver.randomStudy.presentation.dto.FindAllRandomStudyMembersResponse;
import develop.grassserver.randomStudy.presentation.dto.RandomStudyDetailResponse;
import develop.grassserver.randomStudy.presentation.dto.RandomStudyResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomStudyService {

    private final MemberStudyInfoService memberStudyInfoService;

    private final RandomStudyRepository randomStudyRepository;
    private final RandomStudyMemberRepository randomStudyMemberRepository;

    public RandomStudyResponse getRandomStudyDetail(Member member) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return randomStudyRepository.findRandomStudyByMemberId(
                        member.getId(), startOfDay, endOfDay)
                .map(this::mapToResponse)
                .orElseGet(RandomStudyResponse::empty);
    }

    private RandomStudyResponse mapToResponse(RandomStudy randomStudy) {
        RandomStudyDetailResponse detailResponse = RandomStudyDetailResponse.from(randomStudy);
        return RandomStudyResponse.of(detailResponse);
    }

    public FindAllRandomStudyMembersResponse getAllRandomStudyMembers(Member member, Long studyId) {
        validStudyMember(member, studyId);

        List<RandomStudyMember> studyMembers = randomStudyMemberRepository.findAllByRandomStudyId(studyId);
        List<Long> studyMemberIds = getStudyMemberIds(studyMembers);

        MemberStudyInfoDTO studyInfo = memberStudyInfoService.getMemberStudyInfo(studyMemberIds);

        return FindAllRandomStudyMembersResponse.from(studyInfo);
    }

    private void validStudyMember(Member member, Long studyId) {
        if (!randomStudyMemberRepository.existsByMemberIdAndRandomStudyId(member.getId(), studyId)) {
            throw new NotARandomStudyMemberException();
        }
    }

    private List<Long> getStudyMemberIds(List<RandomStudyMember> studyMembers) {
        return studyMembers.stream()
                .map(studyMember -> studyMember.getMember().getId())
                .toList();
    }
}
