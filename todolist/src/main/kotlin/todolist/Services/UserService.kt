package todolist.Services

import arrow.core.Either
import arrow.core.Option
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.serialization.Serializable
import org.eclipse.microprofile.config.inject.ConfigProperty
import todolist.Repositories.TokenLogoutRepository
import todolist.Repositories.UserRepository
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.time.OffsetDateTime
import java.util.UUID
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


@Serializable
data class UserCredential(
    var login: String,
    var password: String,
)

@Serializable
data class UserDTO(
    val id: String,
    val login: String,
    val token: String
)

//Set of functions to manage user authentication
@ApplicationScoped
class UserService {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var tokenLogoutRepository: TokenLogoutRepository

    @Inject
    @ConfigProperty(name = "jwt.key")
    lateinit var publicKey: String

    //Function to log in the user
    fun login(userCredential: UserCredential): UserDTO? {
        val user = Option.fromNullable(userRepository.find("login = ?1", userCredential.login).firstResult())
        println(user.map { Pair(it.password, it.salt) })
        return user.filter {
            val hashLogin = generateHash(userCredential.password, it.salt)
            hashLogin.contentEquals(it.password)
        }.map {
            UserDTO(
                id = it.id.toString(),
                login = it.login,
                token = generateToken(it.id.toString())
            )
        }.getOrNull()
    }

    private val ALGORITHM = "PBKDF2WithHmacSHA512"
    private val ITERATIONS = 120_000
    private val KEY_LENGTH = 256

    //Function to generate a hash password
    fun generateHash(password: String, salt: ByteArray): String {
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val key: SecretKey = factory.generateSecret(spec)
        val hash: ByteArray = key.encoded
        return hash.decodeToString()
    }

    //Function to generate a salt, necessary for hashing the password
    fun generateRandomSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    //Function to return a hash password and its salt
    fun generatePassword(password: String): Pair<String, ByteArray> {
        val salt = generateRandomSalt()
        return Pair(generateHash(password, salt), salt)
    }

    //Function to generate a user token thanks to the user id
    fun generateToken(id: String): String {
        val algorithm = Algorithm.HMAC512(publicKey)
        return JWT.create().withSubject(id).withExpiresAt(
            OffsetDateTime.now().plusHours(1).toInstant()
        ).withJWTId(UUID.randomUUID().toString()).sign(algorithm)
    }

    //Function to check the validity of the token
    fun authenticate(token: DecodedJWT, userId: String): Boolean {
        val algorithm = Algorithm.HMAC512(publicKey)
        val verifier = JWT.require(algorithm).withSubject(userId).build()

        return Either.catch { verifier.verify(token) }.isRight() && tokenLogoutRepository.count(
            "token = ?1",
            token.id
        ) == 0L
    }

}