package com.shusharin.songbase.core

abstract class Abstract {

    interface Object<T, M : Mapper> {

        fun map(mapper: M): T
    }

    interface Mapper {
        class Empty : Mapper
    }
}