package org.ciaranroche.kang.models.vinyl

interface VinylStore {
    fun create(vinyl: VinylModel)
    fun update(vinyl: VinylModel)
    fun delete(vinyl: VinylModel)
    fun findAll(): MutableList<VinylModel>
    fun seed()
}