package todolist.Repositories

import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped
import todolist.Entities.TodoEntity

@ApplicationScoped
class TodoRepository: PanacheMongoRepository<TodoEntity> {}