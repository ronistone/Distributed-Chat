package br.com.ronistone.model.jpa

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table( name = "CHAT_USER" )
class User(
    @Id
    @GeneratedValue
    @Column(name = "ID")
    var id: Long? = null,

    @Column(name = "USERNAME")
    var username: String? = null,
)