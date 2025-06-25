package questy.data.entity

import jakarta.persistence.*
import questy.data.enum.Role

@Entity
@Table(name = "app_user")
class AppUser(
    @Column(name = "username")
    var username: String,
    @Column(name = "email", unique = true)
    val email: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: Role = Role.ADMIN,
    // TODO  : CREATE A CONVERTER ? Maybe not needed because front should handle this themselves ?
    @Column(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

)