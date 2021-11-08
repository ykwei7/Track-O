---
layout: page
title: Keng I's Project Portfolio Page
---

### Project: Track-O

Track-O is a desktop address book application used by private tutors to schedule lessons and track payments.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has 
about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added a `level` field to `Tutee`.
  * What it does: Tutors can now save the education level of `tutees` when adding them to Track-O. When the tutor 
  provides the abbreviation of the education level, Track-O parses it into the full title.<br>
  e.g `p4` parses into `Primary 4`<br>
  e.g `s2` parses into `Secondary 2`<br>
  e.g `j1` parses into `JC1`
  * Justification: We want tutors to be able to recall the current academic progress of a tutee just by
  looking at the tutee list. Having the information of the education level allows tutors to quickly identify the topics
  are relevant.<br>
  Instead of creating a completely new field for that, we decided to replace the existing `email` field with education
  level because email would not be relevant if tutors are teaching younger tutees in the primary level and do not use 
  email for communication purpose.
  * Highlights: This enhancement affects existing commands such as `add` and `edit` as the `email` field now contains 
  different information and parameter constraints.
  * Credits: *-*

* **New Feature**: Added the ability to delete lessons from a tutee.
  * What it does: the `DeleteLessonCommand`  identifies the `lesson` to be deleted and removes it from the user's schedule
  as well as the `lessonlist` of the `tutee`.
  * Justification: Track-O supports adding `lesson` to individual `tutee`. If the tutee decides to quit the tuition lesson 
  permanently, the user has the ability to free up his schedule and remove the lesson.
  * Credits: *-*

<div style="page-break-after: always;"></div>

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=KengXIII&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=zoom&zA=KengXIII&zR=AY2122S1-CS2103T-F12-3%2Ftp%5Bmaster%5D&zACS=137.52941176470588&zS=2021-09-17&zFS=KengXIII&zU=2021-11-08&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false)

* **Project management**:
  * Managed the deadline and closure of milestones v1.3 - v1.3b (2 milestones) on GitHub.
  * Set up the [About-Us](https://ay2122s1-cs2103t-f12-3.github.io/tp/AboutUs.html) page and took pictures 
used for the profile of each team member.

* **Enhancements to existing features**: Modified the `FindCommand` to work like a filter.
  * What it does: `FindCommand` supports keyword search for `name`, `level`, `subjects` and `overdue` status.
  * Justification: The previous `find` function returns matched `tutee` whose name fulfils any of the keyword supplied.
    However, we felt that the feature is not very helpful for tutors if we wanted to extend it to support `level`,
    `subject` and `overdue` status as well.<br>
    For example, if the tutor wants to search up all secondary 4 students taking math classes, using
    `find l/p4 subject/math` will return tutees who are **either** primary 4 or tutees taking math classes.
    The tutor would then have to sieve through the results once again to find the tutee of interest.<br>
    Hence, we modified the implementation to work just like a filter placed sequentially: a matched tutee has to pass all
    the filters to be returned as a matched tutee.
  * Highlights: This enhancement did not affect existing commands and commands to be added in future, but the old
    test cases using the old implementation had to be modified to align with our new design of the `FindCommand`.
  * Credits: *-*

* **Documentation**:
  * User Guide:
    * Added documentation for the features `deleteLesson` and `find`.
  * Developer Guide:
    * Added implementation details of the `find` feature.

* **Community**:
* PRs reviewed (with non-trivial review comments):
[\#39](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/39),
[\#74](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/74),
[\#155](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/155),
[\#159](https://github.com/AY2122S1-CS2103T-F12-3/tp/pull/159),

