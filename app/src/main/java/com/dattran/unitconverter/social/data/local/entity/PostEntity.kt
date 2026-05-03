package com.dattran.unitconverter.social.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "nameUser")
    val nameUser: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "userViews")
    val userViews: Int,

    @ColumnInfo(name = "createAt")
    val createAt: String,
)