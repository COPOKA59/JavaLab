package ru.psu.java.laba2.makmikcro.services

import ru.psu.java.laba2.makmikcro.model.CTimeInterval
import java.util.*

interface IServiceTimeIntervals {
    fun save(timeinterval : CTimeInterval)

    fun getAllByTimeIntervalId(id: UUID): List<CTimeInterval>

    fun getAllTimeIntervals(): List<CTimeInterval>
}