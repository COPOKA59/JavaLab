package ru.psu.java.laba2.makmikcro.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.psu.java.laba2.makmikcro.model.CDate
import ru.psu.java.laba2.makmikcro.model.CTimeInterval
import ru.psu.java.laba2.makmikcro.repositories.IRepositoryTimeIntervals
import java.util.*

@Service
class CServiceTimeIntervals : IServiceTimeIntervals{
    @Autowired
    lateinit var repositoryTimeIntervals: IRepositoryTimeIntervals

    /************************************************************************************************
     * Создание временного интервала.                                                                                 *
     ***************************************************************************************************/
    override fun save(timeinterval: CTimeInterval) {
        try {
            repositoryTimeIntervals.save(timeinterval)
        }
        catch (e : Exception)
        {
            throw Exception("Не указаны обязательные данные для еды!", e)
        }
    }

    /****************************************************************************************************
     * Поиск еды по идентификатору.                                                                *
     * @param id - идентификатор еды для поиска в формате UUID.                                    *
     ***************************************************************************************************/
    override fun getAllByTimeIntervalId(id: UUID): List<CTimeInterval>
    {
        return repositoryTimeIntervals.findAllById(id)
    }

    override fun getAllTimeIntervals(): List<CTimeInterval> {
        val list = ArrayList<CTimeInterval>()
        list.addAll(repositoryTimeIntervals.findAll())
        return list
    }
}