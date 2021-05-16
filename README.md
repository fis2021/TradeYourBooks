# Trade Your Books
A JavaFX application where the customers that use it can buy whichever football shirts they like, provided by the shop owner.

It uses the following technologies:
* Java 15
* JavaFX 16
* Maven
* Nitrite Java

## Developers
Golubov Deian-Andrei

## Setup and run

#### Clone the repository:

Using the command

```
https://github.com/fis2021/TradeYourBooks
```
#### Verify that the project builds locally:

Open the command line, cd into the project and then type:

```
mvn clean install
```
#### Open it in an IDE:

You can use IntelliJ and import the project as a Maven project.

#### Run the project using Maven

To start and run the project using Maven use the following command in the command line: 

```

mvn javafx:run
```

## Registration

You can register into this application as an user

### User

After he logs in, an user can see a personal library in which he can add/delete/edit books. He can see a Library with the books of all other users and offer them a trade or report a book.

### Admin
An admin can see all the books from all users and delete any of them. He can see the reports and decide if he wants to delete the report or delete de book reported.
