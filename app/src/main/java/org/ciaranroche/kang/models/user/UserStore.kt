package org.ciaranroche.kang.models.user

interface UserStore {
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun delete(user: UserModel)
    fun findUser(email: String): UserModel
    fun findAll(): MutableList<UserModel>
}