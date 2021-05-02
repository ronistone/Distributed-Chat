package br.com.ronistone.service

import br.com.ronistone.model.jpa.User
import java.util.Optional

interface UserService {
    fun findUser(id: Long): Optional<User>
    fun createUser(user: User): User
}