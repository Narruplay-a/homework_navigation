package ru.otus.webinar.data

private val towns = listOf(
    "Moscow",
    "Saint Petersburg",
    "Novosibirsk",
    "Yekaterinburg",
    "Nizhny Novgorod"
)

fun getHome(): String {
    return towns.random()
}