package no.hvl.dat250.rest.todos;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class TodoDAO {
    private HashMap<Long, Todo> todos;

    public TodoDAO() {
        todos = new HashMap<>();
    }

    public Todo create(Todo todo) {

        if(todo.getId() == null) {
            long id = new Random().nextInt(100);
            if(todos.containsKey(id)) {
                create(todo);
            }
            todo.setId(id);
        }

        todos.put(todo.getId(),todo);

        return todo;
    }

    public Todo read(Long id) {

        Todo todo = todos.get(id);

        return todo;
    }
    public Todo update(Todo todo, long id) {

        if(todos.containsKey(id)) {
            read(id).setSummary(todo.getSummary());
            read(id).setDescription(todo.getDescription());
            return read(id);
        }
        return null;
    }

    public void delete(Long id) {

        if(todos.containsKey(id)) {
            todos.remove(id);
        }
    }

    public Collection<Todo> all() {
        return todos.values();
    }

}
