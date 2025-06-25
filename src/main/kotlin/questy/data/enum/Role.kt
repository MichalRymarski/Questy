package questy.data.enum


/**
 * Represents user roles and their permissions:
 * - ADMIN: Add/Delete User, Add/Delete Task, View All Tasks, View All Users
 * - EDITOR: Add/Delete Task, View All Tasks
 * - VIEWER: View All Tasks, Change Task Status
 */
enum class Role {
    ADMIN,
    EDITOR,
    VIEWER
}