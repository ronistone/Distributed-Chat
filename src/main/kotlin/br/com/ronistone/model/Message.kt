package br.com.ronistone.model

class Message(
    val receiver: String,
    var subject: String,
    val message: String,
    var messageType: MessageType = MessageType.MESSAGE
) {
    override fun toString(): String {
        return "MessageDTO(receiver='$receiver', subject='$subject', message='$message')"
    }


}