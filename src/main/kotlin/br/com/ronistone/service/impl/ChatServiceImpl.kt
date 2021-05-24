package br.com.ronistone.service.impl

import br.com.ronistone.ChatClusterServiceGrpc
import br.com.ronistone.model.Message
import br.com.ronistone.model.MessageType
import br.com.ronistone.service.ChatService
import io.grpc.ManagedChannelBuilder
import io.lettuce.core.api.StatefulRedisConnection
import io.micronaut.context.annotation.Value
import io.micronaut.websocket.WebSocketSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ChatServiceImpl(
    val redisConnection: StatefulRedisConnection<String, String>,
    @Value(value = "\${chat.server.hostname}") val hostname: String
) : ChatService {

    private val sessions: MutableMap<String, WebSocketSession> = mutableMapOf()
    val logger: Logger = LoggerFactory.getLogger(ChatService::class.java)

    override fun addSession(username: String, session: WebSocketSession) {
        sessions.put(username, session)
    }

    override fun deleteSession(username: String) {
        sessions.remove(username)
    }

    override fun sendMessage(message: Message) {
        val sync = redisConnection.sync()
        if(sessions.containsKey(message.receiver) || sync.exists(message.receiver) != 0L){
            val session = sessions[message.receiver]
            session?.let {
                sendInternal(message, it)
            } ?: sendExternal(message, sync.get(message.receiver))
        } else {
            val session = sessions[message.subject]
            session?.sendAsync(Message(
                message = "${message.receiver} is not connected!",
                messageType = MessageType.ERROR,
                subject = message.subject,
                receiver = message.subject
            ))
            logger.info("${message.receiver} is not connected!")
        }
    }

    private fun sendExternal(message: Message, targetHost: String?) {
        logger.info("SendExternal ${targetHost}")
        val channel = ManagedChannelBuilder.forAddress(targetHost, 8081).usePlaintext().build()
        ChatClusterServiceGrpc.newFutureStub(channel).sendMessage(br.com.ronistone.Message.newBuilder()
            .setMessage(message.message)
            .setSubject(message.subject)
            .setReceiver(message.receiver)
            .setType(br.com.ronistone.Message.MessageType.valueOf(message.messageType.name))
            .build())
    }

    private fun sendInternal(message: Message, session: WebSocketSession) {
        session.sendAsync(message)
        sendedMessage(message, sessions[message.subject])
    }

    override fun sendedMessage(message: Message, session: WebSocketSession?) {
        session?.sendAsync(Message(
            message = "Message sended to ${message.receiver}!",
            messageType = MessageType.SENDED,
            subject = message.subject,
            receiver = message.receiver
        ))
    }

}