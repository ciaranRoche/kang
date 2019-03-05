package org.ciaranroche.kang.models.user

interface UserStore {
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun delete(user: UserModel)
    fun find(user: UserModel)
}