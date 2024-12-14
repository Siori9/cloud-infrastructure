package todolist.routes

import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import todolist.Entities.TodoEntity
import todolist.Repositories.TodoRepository
import todolist.security.Secured

@Serializable
data class TodoRequest(
    var value: String,
)

@Serializable
data class TodoRequest2(
    var value: String,
    var done: Boolean
)

@Serializable
data class TodoResponse(
    val id: String,
    val user: String,
    val value: String,
    val done: Boolean
)

fun TodoEntity.toResponse(): TodoResponse = TodoResponse(
    this.id.toString(), this.user.toString(), this.value, this.done
)

//Path to manage the todo
@Secured
@Path("/users/{idUser}/todos")
class TodoRoute {
    @Inject
    lateinit var todoRepository: TodoRepository

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun list(idUser: ObjectId): List<TodoResponse> =
        todoRepository.find("user = ?1",idUser).list().map { it.toResponse() }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    suspend fun create(idUser: ObjectId, todoRequest: TodoRequest): TodoResponse {
        val entity = TodoEntity()
        entity.user = idUser
        entity.value = todoRequest.value
        entity.persist()
        return entity.toResponse()
    }

    @GET
    @Path("/{todoId}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getById(todoId: ObjectId): TodoResponse? =
        todoRepository.findById(todoId)?.toResponse()

    @DELETE
    @Path("/{todoId}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun delete(todoId: ObjectId): TodoResponse? {
        val todo = todoRepository.findById(todoId)
        todo?.delete()
        return todo?.toResponse()
    }

    @PUT
    @Path("/{todoId}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun put(idUser: ObjectId, todoId: ObjectId, todoRequest: TodoRequest2): TodoResponse? {
        val entity = TodoEntity()
        entity.user = idUser
        entity.value = todoRequest.value
        entity.id = todoId
        entity.done = todoRequest.done
        entity.update()
        return entity.toResponse()
    }
}


