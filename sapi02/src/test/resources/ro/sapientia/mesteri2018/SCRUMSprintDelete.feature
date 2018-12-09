Feature: Check if the scrum delete works
	As Sapientia scrum tool user I want to be able to delete a story

   Scenario: Delete
   Given It is an element in the story list
   When I push the delete button
   Then The element should disapear from the list 
   
   