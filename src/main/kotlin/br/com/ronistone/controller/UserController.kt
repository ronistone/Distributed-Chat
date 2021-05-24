package br.com.ronistone.controller

import br.com.ronistone.model.jpa.User
import br.com.ronistone.service.UserService
import io.micronaut.context.annotation.Value
import io.micronaut.context.env.Environment
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller(value = "/api/user")
class UserController(
    val userService: UserService
) {

    @Inject
    lateinit var env: Environment

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

    @Get("/oi")
    fun info(): HttpResponse<Map<String, String>> {
        return HttpResponse.ok(
            mapOf(
                Pair("hostname", this.env.getProperty("micronaut.application.instance.id", String::class.java).get())
            )
        )
    }


}