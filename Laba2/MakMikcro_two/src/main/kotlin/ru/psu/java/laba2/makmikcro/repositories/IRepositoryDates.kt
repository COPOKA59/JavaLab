package ru.psu.java.laba2.makmikcro.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.psu.java.laba2.makmikcro.model.CDate
import java.util.*

@Repository
interface IRepositoryDates : CrudRepository<CDate, UUID>{
    fun findAllById(id: UUID): List<CDate>//поиск по id
}