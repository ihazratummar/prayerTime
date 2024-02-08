package com.hazrat.prayertimes.repository

import com.hazrat.prayertimes.data.MethodDao
import com.hazrat.prayertimes.data.MethodEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MethodRepository @Inject constructor(
    private val methodDao: MethodDao
) {


    fun getMethod(): Flow<List<MethodEntity>> = methodDao.getMethod()
    suspend fun insertMethod(methodEntity: MethodEntity) = methodDao.insertMethod(methodEntity)
    suspend fun updateMethod(methodEntity: MethodEntity) = methodDao.updateMethod(methodEntity)
    suspend fun deleteAllMethod() = methodDao.deleteAllMethod()
    suspend fun deleteMethod(methodEntity: MethodEntity) = methodDao.deleteMethod(methodEntity)


}