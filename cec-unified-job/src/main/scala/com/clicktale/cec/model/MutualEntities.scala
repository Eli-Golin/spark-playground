package com.clicktale.cec.model

object MutualEntities {
  final case class PageTag(id: Int, pageTagType: Int, value: String)
  final case class AbTest(vendor: Option[String], experienceName: Set[String])
  final case class PageAction(gesture: Int, category: Int, name: Option[String])
  final case class Cart(initialValue: Option[Double],
                        products: Option[Int],
                        added: Int,
                        removed: Int,
                        addedAmount: Double,
                        removedAmount: Double,
                        currency: String)
}
