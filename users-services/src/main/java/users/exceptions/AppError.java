package users.exceptions;

public enum AppError {

    UNRECOGNIZED_EXCEPTION("Unrecognized exception!"),
    UNAUTHORIZED("Unauthorized request!"),
    JSON_PARSE_ERROR("Error occurred during attempts to parse JSON!"),

    ALREADY_EXIST("User already exist"),

    STUDENT_NOT_FOUND("Student not found"),

    TEACHER_NOT_FOUND("Teacher not found"),
    BAD_REQUEST("Bad data request!");

    private final String desc; // error description

    AppError(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return this.desc;
    }

}
