package view.shared

interface ListItem {
    fun getTitle(): String;
    fun getSubtitle(): String;

    fun onClick(): String;
}