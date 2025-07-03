package questy.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "project")
class Project(
    @Column(name = "project_name", unique = true)
    var name: String,
    @Column(name = "description", nullable = true)
    var description: String?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    val owner: AppUser ,
    @Column(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
)