class UserClient {

    suspend fun getAllUsers() = listOf(UserRequestEntity("José", "Silva"), UserRequestEntity("Maria", "Ramalho"))

}