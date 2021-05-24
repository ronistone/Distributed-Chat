package br.com.ronistone.service

interface ClusterService {
    fun addClientToNode(username: String)
    fun removeClientToNode(username: String)
}