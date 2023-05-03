# M-Expenses-App

This project was created as a final project of my Mobile Application Development Module at the University of Greenwich.

COMP-1424 Mobile Application Development
Coursework: M-Expenses Application

Amir Farkhadov

Section 2
This coursework was prepared individually by Amir Farkhadov. My groupmates were not engaged in the process till the beginning of December when most of the functions were already implemented by me. Thus, I have decided to submit my work individually, as I consider it is difficult to bring valuable impact to this project in this short period of time. If all members of the group were engaged, this coursework could have been implemented fully and in less time.

Section 3
Application Overview
This application consist of two list fragments and two fragments for the addition of trips and expenses: 
Trips List Fragment – where a user can see available trips, edit details of this trip or delete all or particular trips.
Add Trip Fragment – where a user can create a new trip and enter details, like Name, Destination, Date (current date as default), Risk Assessment, and Description.
Expenses List Fragment – where a user can see available expenses to the particular trip along with the details of that expense (default date is the date of trip).
Add Expense Fragment – where a user can create a new expense and enter details of it.

![image](https://user-images.githubusercontent.com/57684315/235923148-fd1d07d4-0286-4641-b864-947777945401.png)

As it was not specified, this project could have been implemented by using activities. However, the practicality of this solution is questionable, because fragments can have the same functionality and simplify the adaptation process of the app for tablet (and foldable phones), and give more flexibility.

Each of these fragments has its RecyclerView for Trips and Expenses. It creates list elements dynamically and recycles unused views instead of destroying them, thus improving the performance and reducing power consumption of the app.  

The SQLite database was fully implemented. As this app should be able to store a lot of trips, where each  trip may have a number of expenses, it was decided to have two tables, one for Trips and one for Expenses, which will share Trip ID.

![image](https://user-images.githubusercontent.com/57684315/235923466-497e4142-4196-419a-8043-e3e40b4d4ad1.png)

Searching and Advanced Searching were fully implemented. The simple search searches by trip name. For advanced searching, the dialog window was added, where the user can enter the name of the trip, destination, and date, and the program provides the most suitable result.

As required, the user can upload expenses stored on the device to the URL provided using the Retrofit library. In case of successful upload or failure, the user will get a corresponding notification.

Human computer interaction
To achieve the best user experience, several UI elements were added:

The action bar - has a convenient title on every page, so the user will always be aware of what page he/she is on and what data he/she is about to enter.

<img width="288" alt="image" src="https://user-images.githubusercontent.com/57684315/235923556-f6e31a26-87fa-47b2-be64-d66f504a8a78.png">
<img width="288" alt="image" src="https://user-images.githubusercontent.com/57684315/235923618-84ba17a9-af39-48a6-9218-d3a147605cc0.png">


The Floating Action Button – is an intuitive UI element that was introduced by Google many years ago and is still widely used in many different apps. Thus, the user always knows what that button means.



Trip name for expenses page – when a user creates a new trip and wants to add new expenses, the name of the trip will be placed on the top of the expenses page. Thus, a user sees and understands to which trip expenses are added. 

 <img width="205" alt="image" src="https://user-images.githubusercontent.com/57684315/235923706-69c276e9-5cce-491b-876f-6fb072ab9129.png">
<img width="223" alt="image" src="https://user-images.githubusercontent.com/57684315/235923730-8541cdf2-ca43-481d-a082-fdff958f6f45.png">

 
 

Security
In this current stage, the app does not have any protection. The app can be more secure if the following protection mechanisms will be applied:
User authentication – User provides some information to the system for validation, like username and password.
Fingerprint/Face authentication or other biometric authentication.
Session Handling – the session should not continue working when a user has switched from the app
Data encryption – data will be only available to read for key owners.
Source Code Encryption – modify the code, thus increasing the complexity for people to read.

Ability to run app on a range of screens
The user interface of the M-Expense application was built on Fragments. Fragments are independent components that encapsulate functionality. They operate in the context of the activity but have their lifecycle and UI and can be widely reused in different layouts. Fragments have a Fragment manager that is responsible for adding, deleting, replacing, and adding to the back stack.

Fragments were initially introduced to simplify adapting the layout for tablet screens. For mobile applications, there might be one fragment on the screen. Whereas on a tablet, there might be two fragments displayed at once. Thus, the only thing required to make this app adjustable for different screens size is to create a new layout file for wide screens and insert needed fragments.

Change required for real-live deployment
If some company decide to use this application there are several functions that should be implemented:
User authentication – so the company knows which employee adds the data
Cross-platform – Application should be available on IOS, Android, and ideally as a Web Application, so every employee will have access to the app.
Encryption – to increase security 
More appealing UI/UX
Optimization and testing for bugs 
Section 4
Navigation Graph

![image](https://user-images.githubusercontent.com/57684315/235923844-7fd9a3d4-0743-446b-9433-b68d4fde853d.png)


Step one: User opens the app.  
Main screen displays “TripsListFragment” with all available trips.

<img width="253" alt="image" src="https://user-images.githubusercontent.com/57684315/235923873-6fac818f-4911-4c1f-a57e-aed3fd34cac9.png">


Step Two: User presses on “Add” button to create new trip. 

![image](https://user-images.githubusercontent.com/57684315/235923914-269835cf-7017-46e9-99b9-1e9edacf48d3.png)
![image](https://user-images.githubusercontent.com/57684315/235923941-9ad1d689-ffd0-44a6-8372-667f9baaa669.png)


Step Three: User enters trip details and saves
Created trip is displayed on the Main Screen (TripsListFragment).

![image](https://user-images.githubusercontent.com/57684315/235924031-86d79b6c-455f-46e3-854e-77c5d80561b1.png)
![image](https://user-images.githubusercontent.com/57684315/235924054-9381a318-f72d-45f4-b982-4fdf89a28c19.png)


Step Four: User clicks on trip and opens Expenses List.
 
 ![image](https://user-images.githubusercontent.com/57684315/235924088-19274d44-cc6b-44b7-9267-730b9bd48a2c.png)

Step Five: User presses “Add” button to add new expense.

![image](https://user-images.githubusercontent.com/57684315/235924125-a16f1b1c-d199-4794-b77f-34c38b22cc3a.png)
![image](https://user-images.githubusercontent.com/57684315/235924134-33329e0f-c1e9-455b-b8da-1e357bea0e30.png)



Step Six: User add expense details and saves
The added expense is displayed on Expenses Screen (Expenses Fragment).

![image](https://user-images.githubusercontent.com/57684315/235924169-67063ba2-6c93-41ee-8b15-e6be2ab2dca6.png)
![image](https://user-images.githubusercontent.com/57684315/235924188-4cd4a72b-4694-4c8a-af40-ee64a3737d09.png)


Features One: Search
The user clicks on the “Search” icon and the search line pops out. Search is done by Trip Name.
 
![image](https://user-images.githubusercontent.com/57684315/235924234-10df253c-073a-4d26-a880-1fe961e8ab86.png)
![image](https://user-images.githubusercontent.com/57684315/235924249-5ae1f660-c56f-40e0-8a71-e9a1500c7eea.png)
![image](https://user-images.githubusercontent.com/57684315/235924280-935c2c3c-f603-47ea-afd5-9127e649daec.png)



Feature Two: Advanced Searching
Searching done by Trip name, Destination and Date.
 
![image](https://user-images.githubusercontent.com/57684315/235924313-0a5e2b90-e779-45b8-9e1e-85bf5e2cc698.png)
![image](https://user-images.githubusercontent.com/57684315/235924331-59a798f2-747c-491a-a7d3-9276bb208385.png)
![image](https://user-images.githubusercontent.com/57684315/235924353-a8d8875e-923b-4463-b305-c1c094f9407a.png)
 

Feature Three: Clear – to delete all trips

![image](https://user-images.githubusercontent.com/57684315/235924386-f520d0c8-d994-4af8-aa38-020a54c8af7e.png)
![image](https://user-images.githubusercontent.com/57684315/235924404-efb71b34-41f0-4d72-86cc-0ce61231be73.png)



Feature Four: “Edit” and “Delete” buttons for Trips and Expenses

![image](https://user-images.githubusercontent.com/57684315/235924483-a8f47e4b-9469-4a6e-baa3-3d241dfbec90.png)
![image](https://user-images.githubusercontent.com/57684315/235924582-515b81e4-7206-4551-8bd7-af9dc52862e4.png)

 
Feature Five: Upload Expenses Data
The user can upload entered expenses. If the user doesn't have an internet connection, he/she will get an error.

![image](https://user-images.githubusercontent.com/57684315/235924615-cde1f066-29c1-4337-b954-f8b4b4941ed1.png)
![image](https://user-images.githubusercontent.com/57684315/235924633-93bd6a3a-f605-4af9-ac50-aecbd94c0cb0.png)
![image](https://user-images.githubusercontent.com/57684315/235924665-84420dac-b736-4afc-b7dd-be76670c5b36.png)


