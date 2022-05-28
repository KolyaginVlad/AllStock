package ru.kolyagin.allstock.common

interface Mapper<K, M> {
    fun mapTo(from: K): M

    fun mapFrom(from: M): K
}