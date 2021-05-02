package br.com.ronistone.service

import br.com.ronistone.model.Message
import io.micronaut.websocket.WebSocketSession

interface ChatService {
    fun addSession(username: String, session: WebSocketSession)
    fun deleteSession(username: String)
    fun sendMessage(message: Message)
    fun sendedMessage(message: Message, session: WebSocketSession?)
}