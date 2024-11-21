package develop.grassserver.study.application.service;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.study.domain.entity.Study;
import develop.grassserver.study.domain.entity.StudyRole;
import develop.grassserver.study.infrastructure.repository.StudyRepository;
import develop.grassserver.study.presentation.dto.CreateStudyRequest;
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
    public void createStudy(Member member, CreateStudyRequest request) {
        Study study = Study.builder()
                .name(request.name())
                .goalMessage(request.goalMessage())
                .goalTime(request.goalTime())
                .build();

        study.addMember(member, StudyRole.LEADER);
        studyRepository.save(study);
    }
}
