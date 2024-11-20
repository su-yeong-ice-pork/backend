package develop.grassserver.friend.presentation.exception;

import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.friend.application.exception.AlreadyFriendRequestException;
import develop.grassserver.friend.application.exception.ExistFriendRelationException;
import develop.grassserver.friend.application.exception.NotExistFriendRelationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FriendExceptionHandler {

    @ExceptionHandler(ExistFriendRelationException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> existFriendRelationException(ExistFriendRelationException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(NotExistFriendRelationException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> notExistFriendRelationException(
            NotExistFriendRelationException exception
    ) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(AlreadyFriendRequestException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> alreadyFriendRequestException(
            AlreadyFriendRequestException exception
    ) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
