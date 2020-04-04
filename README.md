#  myPlanBook

## Content
1. Introduction
2. Installation
3. Financial Hub
4. Login pages

## Introduction

## Installation

## Financial Hub

### Load Transactions

This feature of the **Financial Hub** provides a few features which include: a auto-load feature, a manual load feature, and deletion features. The auto-load feature allows users to upload their banking transactions via a CSV file, provided by their bank. Most banks provide this functionality through their online account services. When you click on "Load CSV File", the user is presented with the Android local file explorer, from where they can import a banking .csv file. After clicking the csv of revelance, the app will report through a popup window either success of the operation or error. You can verify that your transactions were imported properly by navigating to the **Manage Finances** feature.


**Note**: the application expects the format of the csv file as follows: "<dd/mm/yyyy>, \<store name/category> \<debit amount\>, \<credit amount\>, \<credit card number\>). 

Load Transactions also allows users to mannually enter every transaction they wish after clicking on the "Manually Load" button. To commit a transaction to your account, first fill all text fields and the click on a date on the calendar at the top of the screen. Once you've entered all requistory information, click either "Debit" or "Credit" based on the type of transaction, to queue the transaction to be loaded (you can view the queue of transactions at the bottom of the screen). Once you are done queuing all transactions you wish, then click on the "Commit Collection" button. If you make a mistake or don't wish to commit all the queued transactions, then you can click "Clear Collection" to delete all queued transactions without commiting them.

To delete financial data already imported and saved to your account, you can either delete all banking data by clicking the "Delete All" button at the bottom of the screen in the **Load Transactions** main window or delete by a specific month by scrolling to the preferred month and clicking on the "Delete Month" button.

### Set Goals

This feature of the **Financial Hub** allows a way for users to set a goal for themselves, and a way to see how close they are to achieiving that goal. Most people have something that they want to buy and are saving for, and this function helps with that. A user can enter the name and price of whatever they want, and allows users to enter income and expenditures. Whenever something is entered, a progress bar is altered to show progress towards the goal amount, and below the bar tells you how much money you've gained/lost since tracking, and how close you are to the goal amount (in %).

The information is stored in the database, this way whenever you leave the app you're inputs and progress are saved. You are also able to switch goals. For example, if the price of your goal changes, then you can re-enter the changed amount, and the progress bar will reflect those changes. 

### Manage Finances

After loading transactions into the account either manually or automatically,
the **Manage Finances** feature graphically displays the transactions in a user friendly way. The first pie chart labeled "Transactions by Category" displays the top 5 transactions by amount over the given year. Clicking on a percentile of the pie chart produces a information box about that transaction category. 

The second pie chart, labeled "Monthly Transactions", charts the amount of transactions made each month as a portion of the pie chart. The bottom charts display debit and credit expenditures for each month as a line graph. The interactive graph responds to zooms and clicks on mapped data points.

## Login Pages

### Login

This is a standard login page, where you are able to enter an email address and password of your account. Using the Firebase API, if the information is linked to an account then the login will be successful, and if not a popup will notify you that the email/password is incorrect. If you do not have an account, then there is a button that takes you to a signup page.

### Signup 

This is another standard signup page, asking for your name, username, password and cell number. If you enter an invalid email, then the signup will fail, if not and all fields have input then the signup will succeed, and your account will be registered in Firebase.


