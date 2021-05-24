package br.com.ronistone.service.impl

import br.com.ronistone.service.ClusterService
import io.lettuce.core.api.StatefulRedisConnection
import io.micronaut.context.annotation.Value
import javax.inject.Singleton

@Singleton
class ClusterServiceImpl(
    @Value("\${chat.server.hostname}") val hostname: String,
    val redisConnection: StatefulRedisConnection<String, String>
) : ClusterService  {

    override fun addClientToNode(username: String) {
        val sync = redisConnection.sync()
        sync.set(username, hostname)
//        sync.expire(username, TimeUnit.MINUTES)
    }

    override fun removeClientToNode(username: String) {
        val sync = redisConnection.sync()
        sync.del(username)
    }

}