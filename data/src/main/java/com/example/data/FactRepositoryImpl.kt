package com.example.data

import android.content.Context
import com.example.domain.FactRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FactRepository {
    override fun getFactRes() : String {
        return context.getString(FakeFactApiService.factList.random())
    }
}