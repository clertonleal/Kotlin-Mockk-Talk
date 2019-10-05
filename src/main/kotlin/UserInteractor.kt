class UserInteractor(private val userClient: UserClient) {

    suspend fun getAllUsers(): List<User> {
        return userClient.getAllUsers().map { Parser.toUserAsync(it) }
    }

    fun convertUsersObject(users: List<UserRequestEntity>) = users.map { Parser.toUser(it) }

    fun convertUsersTopLevel(users: List<UserRequestEntity>) = users.map { userRequestEntityToUser(it) }

    fun convertUsersExtension(users: List<UserRequestEntity>) = users.map { it.toUser() }

}