package view.shared

import cafe.adriel.voyager.core.screen.Screen

interface ListItem<T> {
    fun getTitle(): String;
    fun getSubtitle(): String;

    fun navigateTo(): Screen

    fun getItem(): T
}