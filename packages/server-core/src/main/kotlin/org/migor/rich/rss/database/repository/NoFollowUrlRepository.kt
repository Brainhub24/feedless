package org.migor.rich.rss.database.repository

import org.migor.rich.rss.database.model.NoFollowUrl
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
@Profile("rich")
interface NoFollowUrlRepository : CrudRepository<NoFollowUrl, String> {

  fun existsByUrlStartingWith(url: String): Boolean
}
