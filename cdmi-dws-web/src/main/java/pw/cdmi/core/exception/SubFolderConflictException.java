package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class SubFolderConflictException extends BaseRunException {
    private static final long serialVersionUID = 7976173906202442667L;

    public SubFolderConflictException() {
        super(HttpStatus.CONFLICT, ErrorCode.SUB_FOLDER_CONFLICT.getCode(), ErrorCode.SUB_FOLDER_CONFLICT.getMessage());
    }

    public SubFolderConflictException(String excepMessage) {
        super(HttpStatus.FORBIDDEN, ErrorCode.SUB_FOLDER_CONFLICT.getCode(), ErrorCode.SUB_FOLDER_CONFLICT.getMessage(), excepMessage);
    }

    public SubFolderConflictException(Throwable e) {
        super(e, HttpStatus.CONFLICT, ErrorCode.SUB_FOLDER_CONFLICT.getCode(), ErrorCode.SUB_FOLDER_CONFLICT.getMessage());
    }
}
