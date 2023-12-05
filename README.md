# HotelPol

Repository for Object-oriented technologies course at AGH UST.

## Topic of the project

Application for managing rooms and hotel reservations.

## Authors

 - Dominik Puz
 - Gracjan Filipek
 - Krzysztof Usnarski

## Technologies

 ### Frontend

  - JavaFX

 ### Backend

  - Java
  - Spring
  - Spring Boot
  - Spring Data

 ### Database
  - Microsoft SQL
  - H2 (development and testing purposes)

## Database Diagram

![database diagram](documentation/database_diagram.png)

## How to Run

To run the HotelPol application, follow these steps:

1. Download or clone the repository to your local machine.
2. Import the project into IntelliJ IDEA or your preferred IDE.
3. If asked, set the Java SDK to version 17 or newer in your project settings.
4. Build the application with Gradle.
5. Run the application with an appropriate [Spring profile](#spring-profile).

### Spring profile
HotelPol applications offers two Spring profiles to run:

#### Development - "dev"
Profile used for development purposes. Utilizes a local H2 database filled with sample data.

To run the application with the "dev" profile, follow these steps:
1. Create a Spring Boot Run Configuration in IntelliJ IDEA.
2. Configure the Run Configuration as shown below:
![development run configuration](documentation/dev_run_config.png)
3. Run the configuration.

#### Production -  "prod"
Profile used for production purposes. Connects to an existing Microsoft SQL Server database instance. To run the application in production mode, make sure to:
1. Have access to or provide an existing Microsoft SQL Server database instance.
2. Set up a database profile with a valid username and password, ensuring the user has appropriate permissions for the application.
3. Now, just like with the [development profile](#development---dev), you should create a run configuration as shown below:
![production run configuration](documentation/prod_run_config.png)
4. Finally, you can execute the application with the "prod" profile. It will connect to and use the specified Microsoft SQL Server database.

    