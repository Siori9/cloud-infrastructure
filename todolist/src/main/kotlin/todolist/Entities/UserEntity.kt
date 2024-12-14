package todolist.Entities

import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import org.bson.types.ObjectId

class UserEntity : PanacheMongoEntity() {
    lateinit var login: String
    lateinit var password: String
    lateinit var salt: ByteArray
}