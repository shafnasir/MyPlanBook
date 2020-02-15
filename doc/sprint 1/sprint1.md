# Sprint 1 Planning Meeting

**Sprint Goal**: The goal is to start working on each planner in the multi-planner tool, set up a main menu to gain access to each planner, and get the features: *Log In* and *Sign Up* working to access the main menu screen via an account.
So, the user stories *Log In* and *Sign Up* will be completed for Sprint 1 in so far as the corresponding features allow a mock log in function and a mock sign up function while the app is running. However, this login data will not be stored permanently: it is temporary to the application session.
More permanent data storage which persists even after closing the app will be implemented as a user story in later sprints. 
The last user story to be completed in Sprint 1 is *Select Main Menu Items* which will allow the user to select their desired planner, select the settings page, and select the password change form.
Also, we will discuss the system design at a high level and the framework of the code base as a group.

*Summary:*
*User Stories to Complete for Sprint 1:*
- THEB-5: Log In
- THEB-15: Sign Up
- THEB-6: Select Main Menu Items

*User Stories to Start But Not Complete for Sprint 1:*
- THEB-17: Show Financial Hub
- THEB-11: Log Body Weight
- THEB-7: Calendarize Deadlines

## Participants
- Michael De Lisio
- Brandon Joubran
- Alexei Blinov
- Shaf Nasir
- Xiaoyang/William Wu
- Yifei/Miko Gao

## Team Capacity
- Each team member has about 2 ideal days per sprint due to school conditions. Since there are 6 group members, the team capacity is 12 for a single sprint.

User Story Ticket Number on Jira: User Story Name

## THEB-17: Show Financial Hub
- Assigned to: Michael De Lisio.
- Subtasks
    - *Preview Yearly Expenditure*: Financial hub page should show a preview of yearly expenditures through line chart with months on the x-axis and currency (dollars) on the y-axis.
    - *Link to Financial Functions*: The hub should provide a way of navigation to the financial visualization page (User Story #8), the auto-load/manual-load monthly transactions page (User Story # 4) and the financial goal setter page (User Story #2).

## THEB-11: Log Body Weight
- Assigned to: Alexei Blinov.
- Subtasks
	- *Input and Record Body Weight*: Allow user to input their body weight, choose the unit of measurement, and keep a record of that body weight after they enter it. Estimated Time: 4 hours.
	- *Select Date for Body Weight*: Allow the user to select a date for their body weight input. Estimated time: 2h.
	- *Graph Body Weights*: Allow the user to see a graphical representation of their change in body weight as a function of weight over time. Estimated time: 5h.
	
## THEB-5: Log In
- Assigned to: Brandon Joubran.
- Subtasks
	- *Show GUI*: Show the user the input fields for username and password, a log in button, and a sign-up button in case an account needs to be created.
	- *Implement Log In Function*: Allow the user to log in with an existing account.

## THEB-15: Sign Up
- Assigned to: Brandon Joubran.
- Subtasks
	- *Input Sign Up Information and Sign Up*: Allow the user to input their personal information such as first name, username, password, and others to then create a working account.
	
## THEB-6: Select Main Menu Items
- Assigned to: Miko Gao & William Wu.
- Subtasks
	- *Select Planner and Select Settings*: Allow the user to select the settings button to go to a settings page and select a planner to go to the selected planner's page. Assigned to: William Wu.
	- *Select Password Change*: Allow the user to select the change password link or button to go to a change password form window. Assigned to: Miko Gao.

## THEB-7: Calendarize Deadlines
- Assigned to: Shaf Nasir.
- Subtasks
    - *View Calendar*: The Calendar page should show a basic calendar view to see events created.
    - *Input Tasks*: The Calendar page should allow a user to input tasks into the calendar.
