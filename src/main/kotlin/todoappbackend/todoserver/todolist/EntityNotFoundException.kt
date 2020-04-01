package todoappbackend.todoserver.todolist

class EntityNotFoundException(id: Long) : Exception("Entity with id $id not found")
