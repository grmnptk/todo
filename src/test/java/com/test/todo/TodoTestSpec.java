package com.test.todo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.test.todo.controller.TodoController;
import com.test.todo.model.TodoDTO;
import com.test.todo.service.TodoService;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoTestSpec {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TodoService todoService;

	String exampleTodoJson = "{\"name\":\"TestTodo\",\"description\":\"This is a test todo\",\"completed\":\"false\"}";

	@Test
	public void testRetrieveAllTodos() throws Exception {
		MvcResult result = this.mvc.perform(
				MockMvcRequestBuilders.get("/todos").accept(
						MediaType.APPLICATION_JSON)).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testCreateTodo() throws Exception {
		TodoDTO todo = new TodoDTO("TestTodo", "This is a test todo", false);
		Mockito.when(todoService.createTodo(Mockito.any(TodoDTO.class)))
				.thenReturn(todo);
		MvcResult result = this.mvc.perform(
				MockMvcRequestBuilders.post("/todos").content(exampleTodoJson)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
}
