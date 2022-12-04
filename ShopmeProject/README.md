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

## Show Custom Error Pages

- To show user-friendly error messages instead of default technical ones
- Make UI of error pages match UI of the application
- Handle HTTP error codes: 403, 404, and 500
  403: Sorry, you don;t have permission to access this page
  404: Sorry, the requested page could not be found
  500: Sorry, the server has encountered an error while processing your request

- General custom error page
  Sorry, an unexpected error has occurred 

We don't have to write code or configure for handle the error URL, as it is convention defined by Spring: Just put the error pages in the right folder with the right names, it will work. So developers could focus on business logics, not wasting much time for configuration.