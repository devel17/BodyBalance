package com.dev.bb.service

import com.dev.bb.model.Food


abstract class AFoodCombineService {
    abstract fun combine(profileId: Long): Map<Number, List<Food>>
}