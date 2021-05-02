package br.com.ronistone.service.impl

import br.com.ronistone.model.jpa.User
import br.com.ronistone.repository.UserRepository
import br.com.ronistone.service.UserService
import java.util.Optional
import javax.inject.Singleton

@Singleton
class UserServiceImpl(
    val userRepository: UserRepository
) : UserService {

    override fun findUser(id: Long): Optional<User> {
        return userRepository.findById(id)
    }

    override fun createUser(user: User): User {
        user.id = null
        return userRepository.save(user)
    }

}