package todolist.routes

import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import todolist.Entities.TokenLogoutEntity
import todolist.Services.UserCredential
import todolist.Services.UserService

//Path to do log in and log out
@Path("/auth")
class AuthRoute {

    @Inject
    lateinit var userService: UserService

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun login(userCredential: UserCredential): Response {
        val dto = userService.login(userCredential)
        return if(dto == null) {
            Response.status(Response.Status.FORBIDDEN).build()
        } else Response.ok(dto).build()
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    suspend fun logout(token: String){
        val entity = TokenLogoutEntity()
        entity.token = token
        entity.persist()
    }
}
