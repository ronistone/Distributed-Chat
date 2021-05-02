package br.com.ronistone.service.impl

import br.com.ronistone.model.Message
import br.com.ronistone.model.MessageType
import br.com.ronistone.service.ChatService
import io.micronaut.websocket.WebSocketSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ChatServiceImpl : ChatService {

    private val sessions: MutableMap<String, WebSocketSession> = mutableMapOf()
    val logger: Logger = LoggerFactory.getLogger(ChatService::class.java)

    override fun addSession(username: String, session: WebSocketSession) {
        sessions.put(username, session)
    }

    override fun deleteSession(username: String) {
        sessions.remove(username)
    }

    override fun sendMessage(message: Message) {
        if(sessions.containsKey(message.receiver)){
            val session = sessions[message.receiver]
            session?.sendAsync(message)
            sendedMessage(message, sessions[message.subject])
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

    override fun sendedMessage(message: Message, session: WebSocketSession?) {
        session?.sendAsync(Message(
            message = "Message sended to ${message.receiver}!",
            messageType = MessageType.SENDED,
            subject = message.subject,
            receiver = message.receiver
        ))
    }

}