package com.clicktale.cec.cum.model

object KafkaMessages {

  final case class CecUnifiedVisitId(ts: Long,
                                     project: Long,
                                     visitor: Long,
                                     visit: Long,
                                     pageViews: Iterable[Long])

  final case class CoreCecUnifiedPageViewId(ts: Long,
                                            project: Long,
                                            visitor: Long,
                                            visit: Long,
                                            pageView: Long)
}
