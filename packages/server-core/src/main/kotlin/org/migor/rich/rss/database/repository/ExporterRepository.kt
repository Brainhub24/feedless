package org.migor.rich.rss.database.repository

import org.migor.rich.rss.database.model.Exporter
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Stream

@Repository
@Profile("database")
interface ExporterRepository : CrudRepository<Exporter, String> {

  @Query(
    """select distinct e from ExporterEntity e
    inner join Subscription s
    on s.bucketId = e.bucketId
    inner join Feed f on s.feedId = f.id
    where (
        e.triggerRefreshOn='change'
        and (
            e.lastUpdatedAt is null
            or f.lastUpdatedAt > e.lastUpdatedAt
        )
      )
    or
     (
        e.triggerRefreshOn='scheduled'
        and (
            e.lastUpdatedAt is null
            or f.lastUpdatedAt > e.lastUpdatedAt
        )
        and (e.triggerScheduledNextAt is null or e.triggerScheduledNextAt < :now)
      )
    order by e.lastUpdatedAt asc """,
  )
  fun findDueToExporters(@Param("now") now: Date): Stream<Exporter>

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Modifying
  @Query("update ExporterEntity b set b.lastUpdatedAt = :lastUpdatedAt where b.id = :id")
  fun setLastUpdatedAt(@Param("id") exporterId: String, @Param("lastUpdatedAt") lastUpdatedAt: Date)

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Modifying
  @Query(
    "update ExporterEntity e " +
      "set e.triggerScheduledNextAt = :scheduledNextAt " +
      "where e.id = :id"
  )
  fun setScheduledNextAt(@Param("id") exporterId: String, @Param("scheduledNextAt") scheduledNextAt: Date)
}