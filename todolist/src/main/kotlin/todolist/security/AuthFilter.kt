package todolist.security

import arrow.core.Option
import arrow.core.raise.option
import com.auth0.jwt.JWT
import jakarta.annotation.Priority
import jakarta.inject.Inject
import jakarta.ws.rs.Priorities
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import todolist.Services.UserService

//This class aims to only let requests pass that have a valid user token corresponding to the user id.
//It is associated with the @Secured annotation so all paths with this annotation need the user token.
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
class AuthFilter : ContainerRequestFilter {

    @Inject
    lateinit var userService: UserService


    val PREFIX = "Token "
    override fun filter(requestContext: ContainerRequestContext?) {

        //Recovery of the token
        val header = Option.fromNullable(requestContext?.getHeaderString(HttpHeaders.AUTHORIZATION))
            .flatMap { Option.fromNullable(JWT.decode(it.removePrefix(PREFIX))) }

        //Recovery of the user id
        val userId = Option.fromNullable(requestContext?.uriInfo?.pathParameters?.getFirst("idUser"))

        //Checking the token from the user id
        val authResult = option { Pair(header.bind(), userId.bind()) }
            .filter { userService.authenticate(it.first, it.second) }

        authResult.onNone {
            requestContext?.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build()
            )
        }
    }
}