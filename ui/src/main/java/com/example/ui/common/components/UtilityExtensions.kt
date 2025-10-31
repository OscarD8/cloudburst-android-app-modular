package com.example.ui.common.components

/**
 * Helper method to toggle an element existing in a set.
 */
fun Set<Long>.toggle(id: Long): Set<Long> {
    return if (id in this) this - id else this + id
}

/**
 * Cafes returns Cafe
 */
fun String.removeLast(): String {
    return this.substring(startIndex= 0, endIndex = this.length-1)
}