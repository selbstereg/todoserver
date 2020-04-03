package todoappbackend.todoserver.errorhandling

class EntityNotFoundException(id: Long) : Exception("Entity with id $id not found")
