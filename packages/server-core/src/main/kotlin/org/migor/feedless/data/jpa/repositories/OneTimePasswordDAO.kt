package org.migor.feedless.data.jpa.repositories

import org.migor.feedless.data.jpa.models.OneTimePasswordEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OneTimePasswordDAO : JpaRepository<OneTimePasswordEntity, UUID> {
}