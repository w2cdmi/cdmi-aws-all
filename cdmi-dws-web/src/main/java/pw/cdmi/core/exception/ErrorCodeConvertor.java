package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public final class ErrorCodeConvertor {
    private ErrorCodeConvertor() {

    }

    public static HttpStatus convertToHttpStatus(ErrorCode errorCode) {
        HttpStatus status;
        switch (errorCode) {
            case INVALID_PARAMETER:
                status = HttpStatus.BAD_REQUEST;
                break;
            case FORBIDDEN_OPER:
            case SCANNED_FORBIDDEN:
            case SECURITY_MATRIX_FORBIDDEN:
                status = HttpStatus.FORBIDDEN;
                break;
            case NO_SUCH_FILE:
            case NO_SUCH_FOLDER:
            case NO_SUCH_LINK:
            case NO_SUCH_ITEM:
            case NO_SUCH_OPTION:
            case NO_SUCH_CLIENT:
            case NO_SUCH_PARENT:
            case NO_SUCH_RESOUCE:
            case NO_SUCH_VERSION:
            case NO_SUCH_USER:
                status = HttpStatus.NOT_FOUND;
                break;
            case FILES_CONFLICT:
            case SUB_FOLDER_CONFLICT:
            case LINK_CONFLICT:
                status = HttpStatus.CONFLICT;
                break;
            case EXCEED_SPACE_QUOTA:
            case EXCEED_MAX_NODE_NUM:
            case FILE_SCANNING:
                status = HttpStatus.PRECONDITION_FAILED;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        return status;
    }

}
