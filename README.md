
### 2022.11.20.
### MOOd
### vassannamaria01@gmail.com 

## Introduction

This is a mood tracker app, in which it is possible to write an entry every day about the user's current mood, experiences, activities, etc. In addition, different goals can be recorded and tracked, how much the user has contributed to the given goal on which days. <br />
The aim of the app is to provide an easy way to reflect on the day's events, positives and negatives, and also a motivation to achieve our goals and ourselves. The target audience of the app is mainly the younger generation who want to collect their thoughts at the end of the day, so that they can be looked back on and revisited later.

## Main functionalities

1. In the application, it is possible to add new daily entries, in which the user has to enter different information about their day, e.g. using textboxes and buttons. The data is stored in a database. 
2. Daily entries will appear in a list, click on them to see a more detailed view of that day's data.
3. Entries can be deleted from the database or edited afterwards
4. Under the goals tab, new goals can be added, which are also saved with the help of the database, and the daily entries also show the goals, how much we have contributed to the given goal on the given days.
5. Under the calendar tab, you can see in the calendar view what the mood of the user was on which day based on the data entered so far.

### Other functionalities
1. Add a picture to the daily entries
2. Statistics tab which analyses and graphically displays data in different ways, e.g. ratio of happy/sad days.
3. Notification each day at the specified hour to make an entry
4. Animations

## Chosen technologies

- UI - Activities, Fragments, Splash screen
- Usage of Styles and Themes
- RecyclerView - for displaying the lists
- Persistent Data storage - database

# Documentation

## User Documentation

The app allows you to enter daily diary-like entries to track your daily mood, as well as the goals you set and how much you contributed to achieving them each day.

When the app is opened, the Splash screen is immediately followed by the list of daily entries. At the bottom, the 4 buttons can be used to navigate between the 4 main screens of the app, or even to switch between them by swiping. The 4 screens:

 #### **1. Daily Entries screen**<br>
&nbsp;&nbsp;&nbsp;&nbsp;Here the user can see the daily entries entered so far, the date and the corresponding picture next to it, which is determined by the mood of the day.

&nbsp;&nbsp;&nbsp;&nbsp;You can add a new entry by clicking the button in the bottom right corner, which opens a new window where you can enter the details of the daily entry. Here you can also tick which of the goals you have contributed to that day. If you change your mind, you can go back to the previous page by pressing the *cancel* button, and when you are done entering the data, you can save it by pressing the *save* button.

&nbsp;&nbsp;&nbsp;&nbsp;If the user clicks on an item, a detailed view of that entry is displayed in a new window with all the data entered. By clicking the *cancel* button it is possible to exit this view. If the user clicks on the *options* button in the right corner, two options will appear, the *click* icon to delete the entry, and the *pencil icon* to open the entry editor window where you can edit the details of the entry (this works in a similar way to adding a new entry)

#### **2. Calendar screen**<br>
&nbsp;&nbsp;&nbsp;&nbsp;By navigating to this page, the user will see a calendar where, by selecting a day, the moods of that day will appear under the calendar. If there were no entries on the selected day, the list is empty.

#### **3. Charts screen**<br>
&nbsp;&nbsp;&nbsp;&nbsp;On this page the user can see a pie chart based on the data entered so far, based on the proportion of daily moods. 

#### **4. Goals screen**<br>
&nbsp;&nbsp;&nbsp;&nbsp;Here is a list of the goals you have added so far. The image next to the goals depends on the category. If you click on a goal, you will see a description of the goal and a *click icon* to delete the goal. If you delete a goal, it will also be deleted from the daily entries.

&nbsp;&nbsp;&nbsp;&nbsp;The *plus button* in the right-hand corner adds a new goal, for which you need to enter its details and select the category it belongs to. If you add a new goal, it will appear in the daily entries.

## Overview of the program structure

&nbsp;&nbsp;&nbsp;&nbsp;In the *MainActivity* there is a *ViewPager*, which allows you to switch between the 4 main *fragments* by swiping, and it is linked to the *BottomNavigationBar*, which also allows you to switch between views using the buttons. I store the goals, daily entries and thus the data for the application persistently using the *Room ORM* library. In addition to storing the two entities, I also have a table to store the relationship between the goals and the daily entries, so I can store which goals belong to which entries. The database is updated every time new entry is added, and e.g. the pie chart also changes when a new entry is added, so each screen gets fresh data.

&nbsp;&nbsp;&nbsp;&nbsp;Adding a daily entry is done in a new *activity*, and an *activity* is also created to view a detailed view of an entry. I start these with *Intents*, through which I also pass data to them. As *Partialized data*, I pass attributes I create, such as Goal(Goal) or DailyEntry(daily entry).

&nbsp;&nbsp;&nbsp;&nbsp;To add a new goal I use *AlertDialog* and *DialogFragment*.

&nbsp;&nbsp;&nbsp;&nbsp;The pie chart on the statistics screen was created using the *MPAndroidChart* external library.

## Demonstrating extra functionality
&nbsp;&nbsp;&nbsp;&nbsp;One of the extra functionalities is the PieChart in the Chart tab, which can graphically display the proportion of moods based on the data entered so far.

&nbsp;&nbsp;&nbsp;&nbsp;On the detailed screen of daily entries, the edit and delete buttons are animated, contributing to a more sophisticated user interface.

&nbsp;&nbsp;&nbsp;&nbsp;When selecting the daily mood, instead of the normal *RadioButtons*, images are displayed to select the mood.

&nbsp;&nbsp;&nbsp;&nbsp;A *Toolbar* adds colour to the app's user interface, and different images, a *splash screen* and a font add to the user experience.

## Technologies used:
- Activity
- Fragment
- ViewPager
- BottomNavBar
- ToolBar
- Intent
- Room ORM
- RecyclerView
- Animation
- Styles, Themes
