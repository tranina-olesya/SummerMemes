package ru.vsu.summermemes.utils.extensions

import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.models.meme.MemeInfo

fun List<MemeInfo>.convertToMemeEntities(): List<MemeEntity> {
    val result = mutableListOf<MemeEntity>()
    for (memeEntry in this) {
        val memeEntity = MemeEntity()
        memeEntity.meme = memeEntry
        result.add(memeEntity)
    }
    return result
}