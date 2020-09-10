package com.quantumman.randomgoals.di

//interface SomeInterface {
//    fun getSimpleDate(): String
//}
//
//class SomeClass(val date: Date): SomeInterface {
//    override fun getSimpleDate(): String = SimpleDateFormat.getInstance().format(date)
//}
//
//val testDateModule: Module = module {
//    single { Date() }     // create one instance to be used
//    factory<SomeClass>()     // then you can automatically resolve SomeClass
//    // or you can use a factory block to compose the service
//    factory {
//        val currentDate = Date()
//        return@factory SomeClass(currentDate)
//    }
//    // or you can resolve an implementation for an interface
//    factoryBy<SomeInterface, SomeClass>()
//    // or you can resolve an implementation for an interface with a block
//    factory<SomeInterface> {
//        val currentDate = Date()
//        return@factory SomeClass(currentDate)
//    }
//}