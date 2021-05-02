package br.com.ronistone.repository

import br.com.ronistone.model.jpa.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface UserRepository : CrudRepository<User, Long>