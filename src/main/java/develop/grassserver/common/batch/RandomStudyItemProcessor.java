package develop.grassserver.common.batch;

import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import develop.grassserver.randomStudy.domain.entity.RandomStudyApplication;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyMemberRepository;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyRepository;
import org.springframework.batch.item.ItemProcessor;

public class RandomStudyItemProcessor implements ItemProcessor<RandomStudyApplication, RandomStudy> {

    private final RandomStudyRepository randomStudyRepository;
    private final RandomStudyMemberRepository randomStudyMemberRepository;

    public RandomStudyItemProcessor(RandomStudyRepository randomStudyRepository,
                                    RandomStudyMemberRepository randomStudyMemberRepository) {
        this.randomStudyRepository = randomStudyRepository;
        this.randomStudyMemberRepository = randomStudyMemberRepository;
    }
}
