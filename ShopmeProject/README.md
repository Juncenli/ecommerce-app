# Application Architecture

Admin App: Admin Code; Admin Dependencies

Shopping App: Shopping Code; Shopping Dependencies

Common Part: Common Code; Common Dependencies

View(Thymeleaf, HTML) <----> Controller(MVC, REST) <----> Service(business class) <----> Repository(Entities & Interfaces) <----> Spring Data JPA <----> Hibernate framework <----> JDBC Driver <---> Database


## Code Refactor 1

- Refactor code in users.html(URLs in table header and pagination)

## User Authentication
Using correct email and password to login.

## Code Refactor 2
- Create Thymeleaf fragments for page's head, navigation menu and footer
- Move common Javascript code to common.js  

Reduce code duplication, increase code readability and maintainability 

## User Authorization
Different roles have different functionalities.

## User Authorization: Access Rights Table

![table](README_pics/authorities.jpeg)

```html
<li class="nav-item" sec:authorize="hasAuthority('Admin')">
    <a class="nav-link" th:href="@{/users}">Users</a>
</li>
```

## Code Refactor 3

- Organize some Java packages(user export and user controllers)
- Organize some HTML pages(user module)
- Move some common Javascript code to a sepatate JS file