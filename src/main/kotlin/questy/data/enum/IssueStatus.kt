package questy.data.enum

//TODO : Validate this enum when changing the status of an issue, so that it can only be changed to a valid next status.

enum class IssueStatus {
    TODO,
    IN_PROGRESS,
    READY_FOR_TESTING,
    REVIEW,
    DONE
}