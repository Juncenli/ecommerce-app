# Application Architecture

Admin App: Admin Code; Admin Dependencies

Shopping App: Shopping Code; Shopping Dependencies

Common Part: Common Code; Common Dependencies

View(Thymeleaf, HTML) <----> Controller(MVC, REST) <----> Service(business class) <----> Repository(Entities & Interfaces) <----> Spring Data JPA <----> Hibernate framework <----> JDBC Driver <---> Database


## Code Refactor 1

- Refactor code in users.html(URLs in table header and pagination)

## Code Refactor 2
- Create Thymeleaf fragments for page's head, navigation menu and footer
- Move common Javascript code to common.js  

Reduce code duplication, increase code readability and maintainability 
