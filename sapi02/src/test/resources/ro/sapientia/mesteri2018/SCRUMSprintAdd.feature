Feature: Check if the scrum delete works
	As Sapientia scrum tool user I want to be able to delete a story

   Scenario: Add
   Given I open the sprint tool add page
   When I enter "First Sprint" in  the title textbox and I push the add button
   Then I should get result "First Sprint" in sprints list
   
   
   