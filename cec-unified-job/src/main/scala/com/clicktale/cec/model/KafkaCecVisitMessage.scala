package com.clicktale.cec.model

import com.clicktale.cec.model.MutualEntities._

final case class KafkaCecVisitMessage(entityType:Byte,projectId:Int,visitorId:Long,visitId:Long, timestamp:Long,
                                      dispatchTimestamp:Long,pageViewIds:Array[Long],browser:Option[String],
                                      browserVersion:Option[String],screenHeight:Option[Int],screenWidth:Option[Int],
                                      durationSinceLastVisit:Option[Int],pageTags:Option[Set[PageTag]],deviceType:Option[String],
                                      operatingSystem:Option[String],deviceVendor:Option[String],deviceModel:Option[String],
                                      trafficSource:Option[String],eventIds:Option[Set[Int]],abTests:Option[List[AbTest]],
                                      aggregatedPageActions:Option[List[PageAction]],cart:Option[Cart],
                                      betweenLoopingPages:Option[Seq[Long]],loopingPages:Option[Seq[Long]],
                                      country:Option[String],regionName:Option[String],adobeLegacyId:Option[String],
                                      adobeMarketingCloudId:Option[String],googleAnalyticsClientId:Option[String],
                                      campaignName:Option[String],campaignContent:Option[String],
                                      marketingChannel:Option[String])