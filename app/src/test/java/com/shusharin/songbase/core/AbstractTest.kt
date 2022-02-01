package com.shusharin.songbase.core

import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception
import java.lang.IllegalStateException

class AbstractTest {

    @Test
    fun test_success() {
        val dataObject = TestDataObject.Success("a", "b")
        val domainObject = dataObject.map(DataMapper.Base())
        val expected = TestDomainObject.Success("a b")
        assertEquals(expected, domainObject)
    }
}

sealed class TestDataObject : Abstract.Object<TestDomainObject, DataMapper> {
    abstract override fun map(mapper: DataMapper): TestDomainObject

    class Success(
        private val textOne: String,
        private val textTwo: String,
    ) : TestDataObject() {
        override fun map(mapper: DataMapper): TestDomainObject = mapper.map(textOne, textTwo)
    }

    class Fail(
        private val e: Exception,
    ) : TestDataObject() {
        override fun map(mapper: DataMapper): TestDomainObject = mapper.map(e)
    }
}


sealed class TestDomainObject : Abstract.Object<TestUiObject, DomainToUiMapper> {
    abstract override fun map(mapper: DomainToUiMapper): TestUiObject

    data class Success(
        private val textCombined:String
    ) : TestDomainObject() {
        override fun map(mapper: DomainToUiMapper): TestUiObject {
            throw  IllegalStateException("not implemented yet")
        }
    }

    object Fail : TestDomainObject() {
        override fun map(mapper: DomainToUiMapper): TestUiObject {
            throw IllegalStateException("not implemented yet")
        }
    }

}

interface DataMapper : Abstract.Mapper {
    fun map(textOne: String, textTwo: String): TestDomainObject
    fun map(exception: Exception): TestDomainObject

    class Base : DataMapper {
        override fun map(textOne: String, textTwo: String): TestDomainObject =
            TestDomainObject.Success("$textOne $textTwo")

        override fun map(exception: Exception): TestDomainObject = TestDomainObject.Fail
    }
}

interface DomainToUiMapper : Abstract.Mapper
sealed class TestUiObject : Abstract.Object<Unit, Abstract.Mapper.Empty>