package develop.grassserver.requestNotification.presentation.dto;

import develop.grassserver.requestNotification.domain.entity.FriendRequestNotification;
import java.time.LocalDateTime;
import java.util.List;

public record FindAllFriendRequestsResponse(List<FriendRequest> friendRequests) {

    public static FindAllFriendRequestsResponse from(List<FriendRequestNotification> notifications) {
        return new FindAllFriendRequestsResponse(
                notifications.stream()
                        .map(FindAllFriendRequestsResponse::toFriendRequest)
                        .toList()
        );
    }

    private static FriendRequest toFriendRequest(FriendRequestNotification notification) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime createdAt = notification.getCreatedAt();

        boolean isToday = isSameDay(today, createdAt);
        String time = formatTime(createdAt);
        String date = formatDate(createdAt);

        return new FriendRequest(
                notification.getId(),
                notification.getFriend().getMember1().getName(),
                isToday,
                time,
                date
        );
    }

    private static boolean isSameDay(LocalDateTime today, LocalDateTime createdAt) {
        return today.getYear() == createdAt.getYear()
                && today.getMonthValue() == createdAt.getMonthValue()
                && today.getDayOfMonth() == createdAt.getDayOfMonth();
    }

    private static String formatTime(LocalDateTime createdAt) {
        int hour = createdAt.getHour();
        int minute = createdAt.getMinute();

        if (hour > 12) {
            return String.format("오늘 오후 %02d:%02d", hour - 12, minute);
        } else if (hour == 12) {
            return String.format("오늘 오후 %02d:%02d", hour, minute);
        } else {
            return String.format("오늘 오전 %02d:%02d", hour, minute);
        }
    }

    private static String formatDate(LocalDateTime createdAt) {
        return String.format("%02d월 %02d일", createdAt.getMonthValue(), createdAt.getDayOfMonth());
    }

    public record FriendRequest(
            Long id,
            String memberName,
            boolean isToday,
            String time,
            String date
    ) {
    }
}
