package com.test.todo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.todo.service.TodoService;
import com.test.todo.util.TodoErrorType;
import com.test.todo.model.TodoDTO;

@RestController
public class TodoController {
	
	@Autowired
	TodoService todoService;

	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAllTodos() {
		Collection<TodoDTO> retrievedTodos = todoService.retrieveAllTodos();
		if (null == retrievedTodos)
			return new ResponseEntity<TodoErrorType>(new TodoErrorType(
					"No todos found"), HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Collection<TodoDTO>>(retrievedTodos,
					HttpStatus.OK);
	}

	@RequestMapping(value = "/todos/{title}", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveTodoByTitle(@PathVariable String title) {
		TodoDTO todo = todoService.retrieveTodoByTitle(title);
		if (null == todo)
			return new ResponseEntity<TodoErrorType>(new TodoErrorType(
					"Todo with title " + title + " not found"),
					HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
	}

	@RequestMapping(value = "/todos/{title}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTodo(@PathVariable String title) {
		Collection<TodoDTO> todo = todoService.deleteTodo(title);
		if (null == todo)
			return new ResponseEntity<TodoErrorType>(new TodoErrorType(
					"Todo with title " + title + " not found"),
					HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Collection<TodoDTO>>(todo, HttpStatus.OK);
	}

	@RequestMapping(value = "/todos", method = RequestMethod.POST)
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
		TodoDTO newTodo = todoService.createTodo(todo);
		if (null == newTodo)
			return new ResponseEntity<TodoErrorType>(
					new TodoErrorType("A Todo with title " + todo.getTitle()
							+ " already exists"), HttpStatus.CONFLICT);
		else
			return new ResponseEntity<TodoDTO>(newTodo, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/todos", method = RequestMethod.PUT)
	public TodoDTO updateTodo(@RequestBody TodoDTO todo) {
		return todoService.updateTodo(todo); // title cannot be null
	}
}
