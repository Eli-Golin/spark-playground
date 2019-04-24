package com.clicktale.cec.model

import com.clicktale.cec.model.MutualEntities._

final case class CecPageViewMessage(projectId:Int, visitorId:Long,visitId:Long,
                                    pageViewId:Long,timestamp:Long, dispatchTimestamp:Long,
                                    recorderMessageFlags:Int,verboseLog:Boolean ,timeOnPage:Option[Int],
                                    pageTags:Option[Set[PageTag]],cart:Option[Cart],
                                    pageActions:List[Option[PageAction]],abTest:Option[AbTest],
                                    engagementTime:Option[Int],jsErrorCount:Option[Int],
                                    numOfClicks:Option[Int],eventIds:Option[Array[Int]],attentionSplit:Option[Int],
                                    carelessScrolling:Option[Int],userInteractions:Option[Int],locationHeader:Option[String],
                                    locationBody:Option[String],locationParams:Option[String],referrerHeader:Option[String],
                                    referrerBody:Option[String],referrerParams:Option[String],domLoadTime:Option[Int],
                                    screenRotation:Option[Int],viewportHeight:Option[Int],viewportWidth:Option[Int],
                                    goalOriented:Option[Boolean],lackOfInterest:Option[Boolean],entityType:Byte,
                                    scrollReach:Option[BigDecimal]
                              )
