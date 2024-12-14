package todolist.Repositories

import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped
import todolist.Entities.UserEntity

@ApplicationScoped
class UserRepository: PanacheMongoRepository<UserEntity> {}