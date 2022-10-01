package org.migor.rich.rss.database.models

import org.apache.commons.lang3.StringUtils
import org.migor.rich.rss.database.EntityWithUUID
import org.migor.rich.rss.database.enums.ArticleSource
import java.util.*
import javax.persistence.Basic
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "t_article")
open class ArticleEntity : EntityWithUUID() {
  fun getContentOfMime(mime: String): String? {
    return if (mime == this.contentRawMime) {
      StringUtils.trimToNull(this.contentRaw)
    } else {
      null
    }
  }

  companion object {
    const val LEN_TITLE = 256
    const val LEN_URL = 1000
  }

  @Basic
  @Column(name = "url", nullable = false, length = LEN_URL)
  open var url: String? = null

  @Basic
  @Column(name = "title", nullable = false, length = LEN_TITLE)
  open var title: String? = null
    set(value) {
      field = StringUtils.substring(value, 0, NativeFeedEntity.LEN_TITLE)
    }

  @Basic
  @Column(name = "content_raw_mime")
  open var contentRawMime: String? = null

  @Column(name = "content_raw", columnDefinition = "TEXT")
  @Basic(fetch = FetchType.LAZY)
  open var contentRaw: String? = null

  @Column(name = "content_text", nullable = false, columnDefinition = "TEXT")
  open var contentText: String? = null

  @Basic
  @Column(name = "has_fulltext", nullable = false)
  open var hasFulltext: Boolean = false

  @Basic
  @Column(name = "has_video", nullable = false)
  open var hasVideo: Boolean = false

  @Basic
  @Column(name = "has_audio", nullable = false)
  open var hasAutio: Boolean = false

  @Basic
  @Column(name = "has_event", nullable = false)
  open var hasEvent: Boolean = false

  @Basic
  @Column(name = "main_image_url", length = LEN_URL)
  open var mainImageUrl: String? = null

  @NotNull
  @Column(name = "source_used")
  @Enumerated(EnumType.STRING)
  open var contentSource: ArticleSource = ArticleSource.FEED

  @Basic
  @Column(name = "updatedAt", nullable = false)
  open var updatedAt: Date? = null

  @Basic
  @Column(name = "date_published", nullable = false)
  open var publishedAt: Date? = null

  @Basic
  @Column(name = "score", nullable = false)
  open var score: Int = 0

  @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
  @JoinTable(
    name = "map_article_to_tag",
    joinColumns = [
      JoinColumn(
        name = "article_id", referencedColumnName = "id",
        nullable = false, updatable = false
      )],
    inverseJoinColumns = [
      JoinColumn(
        name = "tag_id", referencedColumnName = "id",
        nullable = false, updatable = false
      )
    ]
  )
  open var tags: List<TagEntity>? = null


  @OneToMany(fetch = FetchType.LAZY,
    cascade = [CascadeType.ALL],
    mappedBy = "articleId",
    orphanRemoval = true,
    targetEntity = AttachmentEntity::class)
  open var attachments: List<AttachmentEntity>? = null
}
