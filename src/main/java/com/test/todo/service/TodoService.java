package com.test.todo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.test.todo.model.TodoDTO;

@Component
public class TodoService {

	private static Map<String, TodoDTO> mapOfTodos = new HashMap<>();

	static {
		// Initialize Data
		TodoDTO sampleTodo = new TodoDTO(
				"Taxes",
				"The taxes must be filed for both provincial and central government",
				true);
		TodoDTO anotherSampleTodo = new TodoDTO("Hydro",
				"The bill has to be paid every 2 months", false);

		mapOfTodos.put(sampleTodo.getTitle(), sampleTodo);
		mapOfTodos.put(anotherSampleTodo.getTitle(), anotherSampleTodo);
	}

	public Collection<TodoDTO> retrieveAllTodos() {
		if (!mapOfTodos.isEmpty()) {
			return mapOfTodos.values();
		}
		return null;
	}

	public TodoDTO createTodo(TodoDTO todo) {
		if (null != todo) {
			if (!todoAlreadyExists(todo.getTitle())) {
				mapOfTodos.put(todo.getTitle(), todo);
				return mapOfTodos.get(todo.getTitle());
			} else
				return null;
		} else
			return null;
	}

	private boolean todoAlreadyExists(String title) {
		if (mapOfTodos.containsKey(title))
			return true;
		else
			return false;
	}

	public TodoDTO updateTodo(TodoDTO todo) {
		if (todoAlreadyExists(todo.getTitle())) {
			mapOfTodos.replace(todo.getTitle(), todo);
			return mapOfTodos.get(todo.getTitle());
		} else {
			mapOfTodos.put(todo.getTitle(), todo);
			return mapOfTodos.get(todo.getTitle());
		}
	}

	public Collection<TodoDTO> deleteTodo(String title) {
		if (todoAlreadyExists(title)) {
			mapOfTodos.remove(title);
			return mapOfTodos.values();
		} else
			return null;
	}

	public TodoDTO retrieveTodoByTitle(String title) {
		if (todoAlreadyExists(title)) {
			TodoDTO selectedTodo = mapOfTodos.get(title);
			return selectedTodo;
		} else
			return null;
	}
}
