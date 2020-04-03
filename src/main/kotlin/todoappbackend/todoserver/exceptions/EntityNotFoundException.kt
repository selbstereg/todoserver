package todoappbackend.todoserver.exceptions

class EntityNotFoundException(id: Long) : Exception("Entity with id $id not found")
