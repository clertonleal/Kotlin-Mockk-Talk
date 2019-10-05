object Parser {

    fun toUser(userRequestEntity: UserRequestEntity) = User(userRequestEntity.user_first_name, userRequestEntity.user_last_name)

    suspend fun toUserAsync(userRequestEntity: UserRequestEntity) = User(userRequestEntity.user_first_name, userRequestEntity.user_last_name)

}