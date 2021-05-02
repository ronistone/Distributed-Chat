package br.com.ronistone.controller

import br.com.ronistone.model.jpa.User
import br.com.ronistone.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

@Controller(value = "/api/user")
class UserController(
    val userService: UserService
) {


    @Get(value = "{id}")
    fun getUser(@PathVariable id : Long): HttpResponse<User> {
        val user = userService.findUser(id)
        if(user.isPresent){
            return HttpResponse.ok(user.get())
        }
        return HttpResponse.notFound()
    }


    @Post
    fun createUser(@Body user: User): HttpResponse<User> {
        return HttpResponse.ok(userService.createUser(user))
    }


}