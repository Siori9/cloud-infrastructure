package todolist.Entities

import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity

class TokenLogoutEntity : PanacheMongoEntity() {
    lateinit var token: String
}