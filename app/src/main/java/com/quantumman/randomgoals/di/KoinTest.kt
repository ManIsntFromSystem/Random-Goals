package com.quantumman.randomgoals.di

//object KoinService {
//    var temp: Any? = null
//    fun <T> register(factory: () -> T) { temp = factory.invoke()  }
//    inline fun <reified T> resolve(): T  = if (temp != null) temp as T else throw IllegalStateException("Does not defined type or argument")
//}
//
//interface Engine
//class F1DefaultEngine : Engine
//class F1MersEngine : Engine
//class F1BolidMers(val engine: Engine)
//fun main() {
//    KoinService.register <Engine> { F1MersEngine() }
//    val bolidMers = F1BolidMers(KoinService.resolve()).also {
//        println("Bolid ${it::class.simpleName} with engine ${it.engine::class.simpleName}")
//    }
//}

//How it works in Koin impl
//fun carModule() = module {
//    factory <Engine> { F1DefaultEngine() }
//    factory { F1BolidMers(get<Engine>()) }
//}
//
//fun main() {
//    val koin = startKoin {
//        modules(carModule())
//    }.koin
//
//    val bolid = koin.get<Engine>().also { println("$it") }
//}


// With approach DI
//Car не знает, откуда приходит реализация Engine, при этом заменить эту самую реализацию легко, достаточно передать её в конструктор.
//interface Engine
//class F1DefaultEngine : Engine
//class F1Bolid (val engine: Engine)
//fun main() { val bolidDI = F1Bolid(F1DefaultEngine()).also { println("F1 bolid with ${it.engine} cylinder engine") } }


//Without DI
//interface Engine
//class F1DefaultEngine : Engine { val cylinders: Int = 6 }
//class F1Bolid { private val engine: Engine = F1DefaultEngine() }
//fun main() { val bolid = F1Bolid() }