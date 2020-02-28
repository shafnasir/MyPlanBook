# Sprint 2 Planning Meeting

**Sprint Goal**: The goal is to begin implementing the primary tools and functionality for each planner. Also, we wish to gather a better understanding of the Firebase interface for Android applications.
This sprint should provide a framework for building the model classes and communication classes that will integrate the database and address persistent data storage that at the moment has not been implemented.
The main issues that this sprint will focus on are the calendarize deadlines interface, completion of the financial hub main view, the log body weight functionality in the health & fitness planner and the user profile page. Finally, we will continue a dialogue on the system design at a high level and the framework of the code base as a team.

*Summary:*

*User Stories to Complete for Sprint 2:*
- THEB-34: Financial Hub
- THEB-39: Calendarize Deadlines
- THEB-36: Log Body Weight
- THEB-41: User Profile Page

*User Stories to Start But Not Complete for Sprint 2:*
- THEB-4: Auto-Load Transactions
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

## THEB-34: Show Financial Hub
- Assigned to: Michael De Lisio.
- Subtasks
    - *Link to Financial Functions*: The hub should provide a way of navigation to the financial visualization page (User Story #8), the auto-load/manual-load monthly transactions page (User Story # 4) and the financial goal setter page (User Story #2)

## THEB-4: Auto-load Monthly Transactions
- Assigned to: Michael De Lisio.
- Subtasks
    - *Import CSV Bank Statements*: Create implementation to upload and read csv files and possibly other file extensions in the Upload Transactions activity.
    - *Upload Manager*:A UI component that allows for the user to navigate the local Android file system and upload their banking transaction file (.csv's only).

## THEB-41: User Profile Page
- Assigned to: Xiaoyang Wu
- A profile page which allows the user to see all the personal information including name, account number, email, phone number, and city.
- Properties:
    - *1*: Able to see all the basic information.
    - *2*: Can change those information at any time.
    - *3*: Able to uplaod a picture from the gallery.
