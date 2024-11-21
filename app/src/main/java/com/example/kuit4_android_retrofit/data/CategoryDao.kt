package com.example.kuit4_android_retrofit.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: CategoryData)

    @Query("SELECT * FROM CategoryData")
    fun getAll(): List<CategoryData>
}
