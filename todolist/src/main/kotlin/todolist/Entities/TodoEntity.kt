package todolist.Entities

import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import org.bson.types.ObjectId

class TodoEntity : PanacheMongoEntity() {
    lateinit var value: String
    lateinit var user: ObjectId
    var done: Boolean = false
}