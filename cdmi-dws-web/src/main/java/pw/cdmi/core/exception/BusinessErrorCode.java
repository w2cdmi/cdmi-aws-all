package pw.cdmi.core.exception;

public enum BusinessErrorCode {
    AlreadyExistException(409), BadRequestException(400), INTERNAL_SERVER_ERROR(500), MissingParameterException(
            400), NotAcceptableException(406), NotFoundException(404),

    PreconditionFailedException(412);

    private int code;

    private BusinessErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
