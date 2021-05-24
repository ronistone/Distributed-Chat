package br.com.ronistone.controller

import br.com.ronistone.model.Message
import br.com.ronistone.service.ChatService
import br.com.ronistone.service.ClusterService
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ServerWebSocket(value = "/chat/{username}")
class ChatWebSocketController(
    val chatService: ChatService,
    val clusterService: ClusterService
) {

    val logger: Logger = LoggerFactory.getLogger(ChatWebSocketController::class.java)

    @OnOpen
    fun onOpen(username: String, session: WebSocketSession) {
        chatService.addSession(username, session)
        clusterService.addClientToNode(username)
        logger.info("connection open $username!")
    }

    @OnClose
    fun onClose(username: String, session: WebSocketSession) {
        chatService.deleteSession(username)
        clusterService.removeClientToNode(username)
        logger.info("connection closed from $username!")
    }

    @OnMessage
    fun onMessage(username: String, message: Message, session: WebSocketSession) {
        message.subject = username
        chatService.sendMessage(message)
        logger.info("message received from $username to ${message.receiver}: $message")
    }


}