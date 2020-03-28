# Sprint 4 Planning Meeting

**Sprint Goal**: Our goal for sprint 4 is to implement the remaining features of our application, connect the various activities to the database, and
finalize the GUI layout style so that our application is ready for its first release. 

*Summary:*

*User Stories to Complete for Sprint 4*:
- THEB-43: Database
- THEB-14/THEB-72: Notify of Deadlines
- THEB-74: Standardize Layouts
- THEB-10: Calculate and Log Body Fat Percentage
- THEB-7/THEB-56: Calendarize Deadlines
- THEB-8: Graph Transactions

*User Stories to Start But Not Complete for Sprint 4*:
- None

## Participants
- Michael De Lisio
- Brandon Joubran
- Alexei Blinov
- Shaf Nasir
- Xiaoyang/William Wu
- Yifei/Miko Gao

## Team Capacity
- Each team member has about 2 ideal days per sprint due to school conditions. Since there are 6 group members, the team capacity is 12 for a single sprint.

## User Stories

User Story Ticket Number on Jira: User Story Name

## THEB-10: Calculate and Log Body Fat Percentage
- Assigned to: Alexei Blinov
- Subtasks
	- *Input and Record Body Fat Percentage Entry*: Allow the user to input a body fat percentage value and have it recorded. Estimated time: 2h.
	- *Select Date for Body Fat Entries*: Allow the user to select a date for body fat entry. Estimated time: 30 min.
	- *Delete Body Fat Entry*: Allow the user to delete a body fat entry. Estimated time: 2h.
	- *Graph Body Fat Entries*: Allow the user to see a graphical representation of their change in body fat as a function of weight over time. Estimated time: 2h.
	- *Calculate Body Fat based on various measurements*: Allow the user to input various data and get a body fat % value. Estimated time: 3h.
	
## THEB-14: Notify of Deadlines
- Assigned to: Miko Gao
- Subtasks
	- *Create Notification Demo*: Have the notification channel created. The demo shows that the app is able to push notifications to the user's phone, and that the user is able to enable or disable the notification setting in Android settings. Estimated time: 5h.
	- *Send Daily Notification*: Send the user a daily notification of what's due today or what to do today. Estimated time: 3h.
	
## THEB-43: Database
- Assigned to: Brandon Joubran, Yifei Gao, Alexei Blinov
- Subtasks
    - *Save Sign Up Information*: Have the user's account information get saved to the database. Allow the user to input the required account information fields into the app, and have the data be sent to a database. This also verifies that the email used to log in is an email that exists. Assigned to: Brandon Joubran.
    - *Log In Using Database*: Allow the user to log in and have their login information verified using the database. Assigned to: Brandon Joubran.
	- *Send App Feedback to Database*: Allow the user to write their opinion about the app and have these feedbacks saved in the database. Assigned to: Miko Gao.
    - *Change password using Database*: Allow the user to change their account password. The program will verify their old password in the database and update to the new one so that they can only sign in with their new password next time they sign in to the application. Assigned to: Miko Gao.
	- *Log out using Database*: Save whether or not a user has logged out so that the user is only allowed to log in if they have logged out. Assigned to: Miko Gao.
	- *Save Goal*: Have the user's financials goals get saved to the database. Also have the information set from the previous use load whenever the page is closed or app is closed. Assigned to: Brandon Joubran.
	- *Log Body Fat Database*: Hook the model for the log body fat activity to the firebase database. Estimated time: 2h. Assigned to: Alexei Blinov.
	- *Log Body Weight Database*: Hook the log body weight activity to the database. Estimated time: 3h. Assigned to: Alexei Blinov.
	- *Log Calories Database*: Hook the log calories activity to the database. Estimated time: 2h. Assigned to: Alexei Blinov.
	
## THEB-56/ THEB-7: Calendarize Deadlines
- Assigned to: Shaf Nasir
- Subtasks
	- *Update Events*: Allow the user to update events in the calendar. Estimated time: 2h.
	- *Delete Events*: Allow the user to delete events in the calendar. Estimated time: 2h.
	
## THEB-74: Standardize Layouts
- Assigned to: Alexei Blinov, William Wu, Brandon Joubran, Michael De Lisio
- Subtasks
	- *Standardize Body Weight Logger*: Standardize the layout style of the body weight logger. Estimated time: 1h. Assigned to: Alexei Blinov.
	- *Standardize Calorie Logger*: Standardize the layout style of the Calorie Logger. Estimated time: 2h. Assigned to: Alexei Blinov.
	- *Standardize the Calendar*: adjust calendar UI to meet newly decided on style guidelines.
	- *Standardize the Financial Hub*: adjust FinancialHubActivity UI to meet newly decided on style guidelines.
	- *Standardize Health and Fitness*: adjust HealthAndFitness UI to meet newly decided on style guidelines.
	- *Standardize Item Goals*: adjust item goals of the Saving Goals activity UI to meet newly decided on style guidelines.

## THEB-8: Graph Transactions
- Assigned to: Michael De Lisio
- Subtasks
	- *Categorize Transaction*: Graphically display monthly transactions:
		- By transaction category in a bar chart (with the 5 most transacted categories display and all else as "other").
		- ie chart of card number transactions.
		- Transaction amount distribution, where transaction amount ranges are mapped to.
	- *Yearly Expenditure Goals*: Display the yearly expenditure chart with your yearly spending goals and yearly projections calculated and displayed.