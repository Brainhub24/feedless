package org.migor.rich.rss.database2.repositories

import org.migor.rich.rss.database2.models.GenericFeedEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GenericFeedDAO : CrudRepository<GenericFeedEntity, UUID>