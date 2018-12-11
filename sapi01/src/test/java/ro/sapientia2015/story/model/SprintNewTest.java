package ro.sapientia2015.story.model;

import org.junit.Test;

import static junit.framework.Assert.*;

public class SprintNewTest {

	@Test
	public void testTitle(){
		Sprint sprint = Sprint.getBuilder("Teszt").build();
		assertEquals("Teszt", sprint.getTitle());
	}
	@Test
	public void testDescription(){
		Sprint sprint = new Sprint();
		sprint.setDescription("Desc");
		assertEquals("Desc", sprint.getDescription());
	}
	
}
