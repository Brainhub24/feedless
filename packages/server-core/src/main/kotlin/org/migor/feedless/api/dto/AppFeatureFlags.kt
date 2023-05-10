package org.migor.feedless.api.dto

data class AppFeatureFlags(
  val canPrerender: Boolean,
  val stateless: Boolean,
  val willExtractFulltext: Boolean,
  val canMail: Boolean,
  val canPush: Boolean,
)