package pw.cdmi.core.exception;

public enum ErrorCode {
    BAD_REQUEST("BadRequest", "The requested resource or the request parameter error."),
    CLIENT_UNAUTHORIZED("ClientUnauthorized", "Authentication failed, The terminal is disabled."),
    DB_ROLL_BACK_EXCEPTION("TransactionRollbackError", "Fail to rollback the db transaction."),
    DB_SUBMIT_EXCEPTION("TransactionCommitError", "Fail to commit the db transaction."),
    FILES_CONFLICT("Conflict", "A same name file or folder is already exsit."),
    FORBIDDEN_OPER("Forbidden", "The operation is prohibited."),
    FORBIDDEN_LINK_MAIL_OPER("DynamicMailForbidden", "The operation is prohibited."),
    FORBIDDEN_LINK_PHONE_OPER("DynamicPhoneForbidden", "The operation is prohibited."),
    USER_DISABLED("UserDisabled", "The user has been disabled"),
    INTERNAL_SERVER_ERROR("InternalServerError", "Server internal error, please try again later."),
    INVALID_SPACE_STATUS("InvalidSpaceStatus", "User space in a non normal state."),
    INVALID_PARAMETER("InvalidParameter", "The request parameter is invalid."),
    LINK_CONFLICT("Conflict", "This  folder or file is already set a link."),
    LINK_EXPIRED("LinkExpired", "This link is expired."),
    LINK_NOT_EFFECTIVE("LinkNotEffective", "This link does not effective."),
    LINK_APPROVING("LinkIsApproving", "This link is approving, please access it after approved."),
    LOGIN_UNAUTHORIZED("Unauthorized", "Authentication fails, the user name or password is incorrect."),
    METHOD_NOT_ALLOWED("MethodNotAllowed", "This method does not allow."),
    MISSING_PARAMETER("MissingParameter", "The request missing required parameters"),
    NetworkException("NetworkException", "Network exception, please try again this operation when the network is not so busy."),
    NO_SUCH_FILE("NoSuchFile", "This file does not exist."),
    NO_SUCH_FOLDER("NoSuchFolder", "This folder does not exist."),
    NO_SUCH_PARENT("NoSuchParent", "This parent does not exist."),
    NO_SUCH_SOURCE("NoSuchSource", "The source does not exist."),
    NO_SUCH_DEST("NoSuchDest", "The destination does not exist."),
    NO_SUCH_ITEM("NoSuchItem", "The node of request not found."),
    NO_SUCH_LINK("NoSuchLink", "This Link does not exist."),
    NO_SUCH_RESOUCE("NoFound", "The resource of request not found."),
    NO_SUCH_USER("NoSuchUser", "This user does not exist."),
    NO_SUCH_VERSION("NoSuchVersion", "This version does not exist."),
    NO_SUCH_OPTION("NoSuchOption", "The option does not exist."),
    SUB_FOLDER_CONFLICT("SubFolderConflict", "The target folder is sub folder for the src folder."),
    TOKEN_UNAUTHORIZED("Unauthorized", "Authentication fails, the token illegal or invalid."),
    TOO_MANY_REQUESTS("TooManyRequests", "Too many requests, please try again later."),
    USER_LOCKED("UserLocked", "forbidden, the user is locked."),
    SECURITY_FORBIDDEN("SecurityForbidden", "forbidden, the security matrix deny the request."),
    NO_SUCH_CLIENT("NoSuchClient", "This client is not exist."),
    SERVICE_BUSY("ServiceBusy", "The service is busy, please try again later."),
    EXCEED_MAX_TEAMSPACE_NUM("ExceedTeamSpaceMaxNum", "The sum of teamSpace exceed the maximum"),
    EXCEED_SPACE_QUOTA("ExceedQuota", "User space quota exceeded"),
    FILE_SCANNING("FileScanning", "The request file is not ready"),
    INVALID_SIZE("InvalidSize", "The size of image file is not allowed"),
    INVALID_SCALE("InvalidScale", "The scale of image file is not allowed."),
    INVALID_IMAGE("InvalidImage", "The file is not right image file"),
    SCANNED_FORBIDDEN("ScannedForbidden", "This file is not allowed to be downloaded"),
    SECURITY_MATRIX_FORBIDDEN("SecurityMatrixForbidden", "The operation is prohibited by security matrix."),
    EXCEED_MAX_NODE_NUM("ExceedUserMaxNodeNum", "The sum of user nodes exceed the maximum"),
    NEED_CHANGE_PASSWORD("NeedChangePassword", "You should reset your passwRod.");

    private String code;

    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
