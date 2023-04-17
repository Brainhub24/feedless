package org.migor.rich.rss.service

import org.migor.rich.rss.AppProfiles
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*

@Service
@Profile(AppProfiles.database)
class DefaultsService {
  fun forHarvestItems(value: Boolean?) = value ?: false
  fun forHarvestItemsWithPrerender(value: Boolean?) = value ?: false

}