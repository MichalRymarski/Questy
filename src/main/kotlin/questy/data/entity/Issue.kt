package questy.data.entity

import jakarta.persistence.*
import questy.data.enum.IssueStatus
import questy.data.enum.IssueType

@Entity
@Table(name = "issue")
class Issue(
    @Column(name = "title")
    var title: String,
    @Column(name = "description")
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    val project: Project,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = true)
    var assignee: AppUser?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = true)
    var reporter: AppUser?,

    @Enumerated(EnumType.STRING)
    var status: IssueStatus = IssueStatus.TODO,
    @Enumerated(EnumType.STRING)
    var type: IssueType = IssueType.STORY,

    @Column(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @Column(name = "updated_at")
    var updatedAt: Long = System.currentTimeMillis(),

    @OneToMany(mappedBy = "issue", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    val comments: MutableList<IssueComment> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
)