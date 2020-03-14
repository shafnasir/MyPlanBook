# Sprint 3 Planning Meeting

**Sprint Goal**: Our goal for sprint 3 is to implement the last key features of our application based on priority. The features detailed below
including fully implementing all classes with firebase dependencies and updating all UI layouts for cross platform compatibility. A major issue addressed at this sprint planning meeting is the need for a consistent UI styling guideline and theme for all components of the application; this is a major goal we wish to achieve this sprint 3. 

*Summary:*

*User Stories to Complete for Sprint 3:
- THEB-2: Set Savings Goals
- THEB-4: Auto-load Monthly Transactions
- THEB-12: Log Calories
- THEB-56: Calendarize Deadlines

*User Stories to Start But Not Complete for Sprint 3:*
- THEB-43: Database

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

## THEB-2: Set Savings Goals
- Assigned to: Brandon Joubran
- Subtasks
	- *GUI*: Have the page set up and connecting it to the financial hub activity.
	- *Adding a goal*: Allow's the user to enter a product that they want to save for. The user can enter the name of the product and the price of it.
	- *Goal Progression*: Allow the user to enter income and expenditures. This adjusts the progress bar accordingly, while updating the user how close they are to their goal.

## THEB-4: Auto-load Monthly Transactions
- Assigned to: Michael De Lisio
- Subtasks
	- *Import CSV Bank Statements*: Create implementation to upload and read csv files and possibly other file extensions in the Upload Transactions activity.
	- *Upload Manager*: A UI component that allows for the user to navigate the local Android file system and upload their banking transaction file (.csv's only).
	- *Manual Entry*: Allow for manual entry of monthly transactions in lieu of a csv sheet.
	
## THEB-12: Log Calories
- Assigned to: Alexei Blinov
- Subtasks
	- *Input and Record Food and Calories*: Allow the user to input a food item and the corresponding calories: see it recorded in a scroll-able list. Estimated time: 2h.
	- *Select Date for Calorie Entries*: Be able to select a date for calorie entries and see the calories consumed or entered for that day. Estimated time: 2h.
	- *Delete Calorie Entry*:  Be able to delete a calorie entry. Estimated time 1 h.
	- *Display Total Calories*: Display the Total Calorie amount for a given date. Estimated time 1h.

## THEB-43: Database
- Assigned to: Brandon Joubran, Yifei Gao
- Subtasks
    - *Save Sign Up Information*: Have the user's account information get saved to the database. Allow the user to input the required account information fields into the app, and have the data be sent to a database. This also verifies that the email used to log in is an email that exists. Assigned to: Brandon Joubran.
    - *Log In Using Database*: Allow the user to log in and have their login information verified using the database. Assigned to: Brandon Joubran.
	- *Send App Feedback to Database*: Allow the user to write their opinion about the app and have these feedbacks saved in the database. Assigned to: Miko Gao.
    - *Change password using Database*: Allow the user to change their account password. The program will verify their old password in the database and update to the new one so that they can only sign in with their new password next time they sign in to the application. Assigned to: Miko Gao.
	- *Log out using Database*: Save whether or not a user has logged out so that the user is only allowed to log in if they have logged out. Assigned to: Miko Gao.
	- *Save Goal*: Have the user's financials goals get saved to the database. Also have the information set from the previous use load whenever the page is closed or app is closed.

## THEB-56: Calendarize Deadlines
- Assigned to: Shaf Nasir
- Subtasks
	- *Update Events*: Allow the user to update events in the calendar. Estimated time: 2h.
	- *Delete Events*: Allow the user to delete events in the calendar. Estimated time: 2h.