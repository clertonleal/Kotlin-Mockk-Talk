import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UserInteractorTest {

    private lateinit var userInteractor: UserInteractor
    private lateinit var userClient: UserClient

    @BeforeEach
    internal fun setUp() {
        userClient = mockk()
        userInteractor = UserInteractor(userClient)
    }

    @Test
    internal fun `large test with iteractor and parser`() {
        val users = listOf(
            UserRequestEntity("José", "Silva"),
            UserRequestEntity("Maria", "Das Graças")
        )

        val convertedUsers = userInteractor.convertUsersObject(users)

        val expectedUsers = listOf(
            User("José", "Silva"),
            User("Maria", "Das Graças")
        )

        assertEquals(expectedUsers, convertedUsers)
    }

    @Test
    internal fun `mock parse object`() {
        mockkObject(Parser)

        val users = listOf(
            UserRequestEntity("José", "Silva"),
            UserRequestEntity("Maria", "Das Graças")
        )

        userInteractor.convertUsersObject(users)

        verify { Parser.toUser(users[0]) }
        verify { Parser.toUser(users[1]) }
    }

    @Test
    internal fun `mock extension function to parse objects`() {
        mockkStatic("ExtensionsKt")

        val user1 = mockk<UserRequestEntity> {
            every { toUser() } returns mockk()
        }

        val user2 = mockk<UserRequestEntity> {
            every { toUser() } returns mockk()
        }

        val users = listOf(user1, user2)

        userInteractor.convertUsersExtension(users)

        verify { user1.toUser() }
        verify { user2.toUser() }
    }

    @Test
    internal fun `mock top level function to parse objects`() {
        mockkStatic("FunctionsKt")

        val users = listOf(
            UserRequestEntity("José", "Silva"),
            UserRequestEntity("Maria", "Das Graças")
        )

        userInteractor.convertUsersTopLevel(users)

        verify { userRequestEntityToUser(users[0]) }
        verify { userRequestEntityToUser(users[1]) }
    }

    @Test
    internal fun `mock iteractor with coroutine`() {
        mockkObject(Parser)

        val users = listOf(
            UserRequestEntity("José", "Silva"),
            UserRequestEntity("Maria", "Das Graças")
        )

        coEvery { userClient.getAllUsers() } returns users

        runBlocking {
            userInteractor.getAllUsers()

            coVerify { Parser.toUserAsync(users[0]) }
            coVerify { Parser.toUserAsync(users[1]) }
        }
    }

    @AfterEach
    internal fun tearDown() {
        unmockkAll()
    }
}