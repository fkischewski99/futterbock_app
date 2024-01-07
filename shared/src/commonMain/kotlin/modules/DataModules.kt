package modules

import data.Querys
import org.koin.dsl.module

val dataModules = module {
    single { Querys() }
}