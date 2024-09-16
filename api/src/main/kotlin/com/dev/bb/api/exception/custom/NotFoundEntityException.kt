package com.dev.bb.api.exception.custom

import jakarta.persistence.Entity
import java.lang.Exception

class NotFoundEntityException(message: String): Exception(message) {

}