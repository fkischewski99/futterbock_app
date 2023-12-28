package view.shared

import cafe.adriel.voyager.core.screen.Screen

interface ListItem {
    fun getTitle(): String;
    fun getSubtitle(): String;

    fun navigateTo(): Screen
}