package todolist.routes

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import todolist.Entities.UserEntity
import todolist.Repositories.TodoRepository
import todolist.Repositories.UserRepository
import todolist.Services.UserService


@Serializable
data class UserRequest(
    var login: String,
    var password: String,
)

@Serializable
data class UserResponse(
    val id: String,
    val login: String,
    val password: String,
)

fun UserEntity.toResponse(): UserResponse = UserResponse(
    this.id.toString(), this.login, this.password
)

//Path to manage user
@Path("/users")
class UserRoute {
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var todoRepository: TodoRepository
    @Inject
    lateinit var userService: UserService

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun list(): List<UserResponse> =
        userRepository.listAll().map { it.toResponse() }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    suspend fun create(userRequest: UserRequest): Response {
        val user = userRepository.count("login = ?1", userRequest.login)

        return if(user == 0L) {
            val generated = userService.generatePassword(userRequest.password)
            val entity = UserEntity()
            entity.login = userRequest.login
            entity.password = generated.first
            entity.salt = generated.second
            entity.persist()
            Response.status(Response.Status.CREATED).entity(entity.toResponse()).build()
        } else {
            Response.status(Response.Status.CONFLICT).build()
        }
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getById(userId: ObjectId): UserResponse? =
        userRepository.findById(userId)?.toResponse()

    @DELETE
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun delete(userId: ObjectId): UserResponse? {
        todoRepository.find("user = ?1",userId).list().forEach {
            it.delete()
        }
        val user = userRepository.findById(userId)
        user?.delete()
        return user?.toResponse()
    }
}