package ru.kolyagin.allstock.presentation.events

sealed class Event {
    object ErrorEvent : Event()
}

sealed class EventIndicator {
    object EventIndicatorShow : EventIndicator()
}