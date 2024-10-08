package develop.grassserver.member.badge;

import lombok.Getter;

@Getter
public enum Badge {

    EARLY_ADOPTER(0, "얼리어답터", "베타 오픈 기간에 가입한 사람들에게 주어지는 뱃지"),
    KING_OF_ATTENDANCE(1, "출석의 왕", "새도 연속 출석 20회 이상 출석한 사용자에게 주어지는 뱃지"),
    MAYBE_THREE_DAYS(2, "작심삼일", "일일 스터디 3회 참여한 사용자에게 주어지는 뱃지"),
    KING_OF_SAEDO(3, "새도의 신", "3등 이내의 순위에 5번 이상 오른 사용자에게 주어지는 뱃지"),
    WITH_GRASS(4, "함께하게 되...", "고정 스터디에 참여한 사용자에게 주어지는 뱃지"),
    AGAIN_ATTENDANCE(5, "미워도 다시 한 번", "스트릭 30일 이상 유지하다가 끊긴 사용자에게 주어지는 뱃지");

    private final int fileName;
    private final String name;
    private final String description;

    Badge(int fileName, String name, String description) {
        this.fileName = fileName;
        this.name = name;
        this.description = description;
    }
}
