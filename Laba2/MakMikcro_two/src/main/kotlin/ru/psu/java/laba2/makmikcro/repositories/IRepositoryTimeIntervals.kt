package ru.psu.java.laba2.makmikcro.repositories
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.psu.java.laba2.makmikcro.model.CTimeInterval
import java.util.*

@Repository
interface IRepositoryTimeIntervals : CrudRepository<CTimeInterval, UUID>{
    fun findAllById(id: UUID): List<CTimeInterval>//поиск по id
}