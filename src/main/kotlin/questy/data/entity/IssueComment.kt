package questy.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "issue_comment")
class IssueComment (
    @Column(name = "content")
    val content: String,

    @Column(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @Column(name = "updated_at")
    var updatedAt: Long = System.currentTimeMillis(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    val author : AppUser,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    val issue: Issue,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
)