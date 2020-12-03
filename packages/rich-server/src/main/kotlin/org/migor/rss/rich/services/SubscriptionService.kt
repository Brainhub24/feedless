package org.migor.rss.rich.services

import org.migor.rss.rich.dtos.SubscriptionDto
import org.migor.rss.rich.models.Subscription
import org.migor.rss.rich.repositories.SubscriptionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SubscriptionService {
  @Autowired
  lateinit var repository: SubscriptionRepository

  fun list(): Page<SubscriptionDto> {
    return repository.findAll(PageRequest.of(0, 10))
      .map { s: Subscription? -> s?.toDto()}
  }

}
