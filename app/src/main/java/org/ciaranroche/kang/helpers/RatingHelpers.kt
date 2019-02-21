package org.ciaranroche.kang.helpers

import org.ciaranroche.kang.models.rating.RatingModel

fun addNewRating(newRating: Int, rating: RatingModel): RatingModel {
    when (newRating) {
        1 -> rating.onestar += 1
        2 -> rating.twostar += 1
        3 -> rating.threestar += 1
        4 -> rating.fourstar += 1
        5 -> rating.fivestar += 1
        else -> { // Note the block
            rating.nostar += 1
        }
    }
    return rating
}

fun returnRating(rating: RatingModel): Int {
    val totalRatings = rating.fivestar + rating.fourstar + rating.threestar + rating.twostar + rating.onestar
    var totalRating = 0
    if (totalRatings != 0) {
        totalRating = (5 * rating.fivestar + 4 * rating.fourstar + 3 * rating.threestar + 2 * rating.twostar + 1 * rating.onestar) / (totalRatings)
    }
    return totalRating
}