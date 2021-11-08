---
layout: page
title: Brandon's Project Portfolio Page
---

### Project: Track-O

Track-O is a desktop address book application used by private tutors to schedule lessons and track payments. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 17 kLoC.

Given below are my contributions to the project.

* **Add lessons**: Added the ability to add lessons to a tutee.
  * What it does: allows the tutor to add lessons to any of their tutees and keep track of the details of their lessons. The details of each lesson include its subject, day of occurrence, start time and end time, as well as its hourly rate. 
  * Justification: This feature implements a core functionality for the application as tutors need not manually keep track of lessons using a calendar. Furthermore, commands such as `PaymentAddCommand` and `ScheduleCommand` depends on lessons being implemented.
  * Highlights: This enhancement requires lessons to be stored in the hard disk. It required an in-depth analysis of how the `Storage` package works. The implementation too was challenging. An initial approach of storing a nested JSON with lessons nested inside tutees failed due to errors in processing the JSON file on start-up. In the end, the lessons are stored as a JSON string under each tutee.
  * Credits: *https://www.baeldung.com/jackson-exception#2-the-solution*
  
* **Schedule**: Added the ability to display the tutor's lessons.
  * What it does: allows the tutor to keep track of all their lessons of the week at a glance. The lessons in the schedule are sorted based on the day of week, followed by the start time of the lesson.
  * Justification: This feature implements a core functionality for the application as it provides a glance of all the tutor's lessons with just the `schedule` command. It also helps to enforce the constraint that the tutor's lessons cannot have any clashes in timing.
  * Highlights: This enhancement requires adding each tutee's lessons into the schedule, as well as the name of the tutee involved in that lesson, using a `TreeMap<Lesson, String>` to ensure that the lessons are sorted.
  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=botr99&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=botr99&tabRepo=AY2122S1-CS2103T-F12-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Set up team repo on GitHub.

* **Enhancements to existing features**:
  * Updated the `help` command to include the usage commands of all commands in the result panel (Pull request [\#43](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/43))

* **Documentation**:
  * User Guide:
    * Added the `About` portion to include the explanation of complex terms and the explanation of the GUI layout (Pull requests [\#162](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/162), [\#183](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/183))
    * Added documentation for the `addlesson` and `schedule` commands (Pull requests [\#67](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/67), [\#91](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/91))
    * Updated existing documentation for the `add`, `edit` and `clear` commands (Pull request [\#98](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/98))
  * Developer Guide:
    * Added implementation details of the `Schedule` feature (Pull request [\#56](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/56))
    * Updated the UML diagrams in the Model component section with updated `Tutee` fields (Pull request [\#171](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/171))
    * Added implementation details of the `Lesson` feature and the `AddLessonCommand` (Pull request [\#191](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/191))
    * Added the use cases for adding and deleting lessons (Pull request [\#204](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/204))

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#44](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/44), [\#71](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/71), [\#74](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/74), [\#87](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/87)
  * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2122S1-CS2103T-W16-4/tp/issues/183), [2](https://github.com/AY2122S1-CS2103T-W16-4/tp/issues/186), [3](https://github.com/AY2122S1-CS2103T-W16-4/tp/issues/195))

* **Tools**:
  * Integrated Codecov to the team repo.
