package br.com.ronistone.service.impl

import br.com.ronistone.ChatClusterServiceGrpc
import br.com.ronistone.Message
import br.com.ronistone.model.MessageType
import br.com.ronistone.service.ChatService
import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
class ChatClusterServiceImpl(
    val chatService: ChatService
) : ChatClusterServiceGrpc.ChatClusterServiceImplBase() {

    override fun sendMessage(request: Message?, responseObserver: StreamObserver<Empty>?) {
        request?.let {
            chatService.sendMessage(br.com.ronistone.model.Message(
                receiver = it.receiver,
                subject = it.subject,
                message = it.message,
                messageType = MessageType.valueOf(it.type.toString())
            ))
        }
    }
}