# Web Automation with Selenium

## What's in this repository?
This project is a web automation framework using Selenium WebDriver. It automates web interactions for testing purposes, ensuring the functionality of web applications.

## What's the purpose of this project?
The purpose of this project is to implement what I have learned in web automation testing using Selenium with Java programming language.

## Key Features
- Page Object Model (POM) for maintainability
- TestNG for test execution and reporting
- Maven for dependency management
- Configurable test execution
- Logging and reporting capabilities
- Data Driven Testing (DDT)
- Extent Reports for test reporting

## Technologies
- Java 21
- Apache Maven
- Selenium
- TestNG Framework
- ExtentReport

## Project Structure
```
src/
├── test/
│   ├── java/
│   │    └──── base/             # Page Object Model classes
│   │          ├── listeners/    # TestNG listeners for logging and reporting
│   │          ├── rough/        # Experimental test scripts
│   │          ├── testCases/    # Main test case implementations
│   │          └── utilities/    # Utility classes for test data and helpers
│   └── resources/
│       └── excel/               # Test data stored in Excel format
│   │       ├── properties/      # Configuration properties files
│   │       └── runner/
└── pom.xml                      # Maven configuration
```
### 1. Project URL
```https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login```

### 2. UI Test
The UI tests cover the functionality of the website, focusing on key features such as:
- Login
  - Bank Manager Login
      - Add Customer
      - Open Account
 
### 3. Viewwing the reports
The test reports can be found in:
```./reports```
The report will be generated upon execution and will include screenshots if any failures occur during the testing process.

## Installation
1. Clone this repository:<br />
   `git clone https://github.com/anneyoung27/selenium-web-automation.git`

2. Navigate to the project directory:<br />
   `cd selenium-web-automation`

3. Install dependencies using Maven:<br />
   `mvn clean install`

