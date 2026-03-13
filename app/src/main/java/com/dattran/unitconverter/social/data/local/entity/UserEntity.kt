package com.dattran.unitconverter.social.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_info",
    indices = [Index(value = ["email"], unique = true)]
)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "avatar", defaultValue = "")
    val avatar: String?,

    @ColumnInfo(name = "bio", defaultValue = "")
    val bio: String?,

    @ColumnInfo(name = "website", defaultValue = "")
    val website: String?,

    @ColumnInfo(name = "date_of_birth", defaultValue = "")
    val dateOfBirth: String?,

    @ColumnInfo(name = "created_at", defaultValue = "")
    val createdAt: String?,

    @ColumnInfo(name = "updated_at", defaultValue = "")
    val updatedAt: String?,

    @ColumnInfo(name = "verify", defaultValue = "0")
    val verify: Int,

    @ColumnInfo(name = "location", defaultValue = "")
    val location: String?,

    @ColumnInfo(name = "accessToken", defaultValue = "")
    val accessToken: String?,

    @ColumnInfo(name = "refreshToken", defaultValue = "")
    val refreshToken: String?,
)