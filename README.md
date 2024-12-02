SP_TroodTestProject

The project has been developed by Sergejs Ponomarenko as a backend test assignment for the Trood Company - a REST API for managing user profile data.

I. Project requirements and attainment thereof

The scope of implementation covers the required functionality (viewing and updating a user's Profile, and changing the Profile Avatar), as well as one extra function, which allows users to sign up (i.e. add new Profile entities). Requirements towards JSON bodies have been met through the use of DTOs
The required validation is carried out using both entity field annotations of appropriate formats and in-code validation methods.
The database technology is SQLite; data is saved gracefully both as database table records and as avatar images.

II. Extra features implemented in the course of work

1) A DataLoader class has been added to populate the database with 5 test entities upon first startup, in order to facilitate testing. If there are records in the database, population does not occur.
2) Access Control - updating a profile or avatar requires one to be authenticated as the respective user or as the Admin. The mechanism is rather crude, without proper usage of roles or authorities, and has only been introduced as a demonstration of capabilites. For this purpose, the Profile entity has been supplemented with login and password fields, the latter one being encrypted with BCrypt prior to being persisted to the database. Private profiles can only be viewed by their owners or the Admin; public Profiles remain available to everyone. 
3) XSS protection as an extra security feature, and CORS support for possible integration with an appropriate frontend component developed using React.
4) The project is also capable of producing a Docker container, but this feature has been disabled for the simplicity of testing. Removing the comment characters from the respective dependency entry in the project's pom.xml file will enable it back.

III. Prerequisites

To test the project locally, you will need the following software:

1) IntelliJ IDEA (or any other IDE suitable for Kotlin projects)
2) Maven (for building and managing the project)
3) SQLite (used for the database)
4) Docker (optional, for containerization)

IV. Installation

1) Clone the repository from https://github.com/SergejsP84/SP_TroodTestProject
2) Download the Postman request collection from https://files.inbox.lv/shared/file/efd4038fdfe49bfd85a8c3055c460de53e4d86a3
3) If you want the program to create a Docker container, uncomment lines 70-75 in the project's pom.xml file
4) Build the project
   5a) Run the program from the development environment, or
   5b) Start Docker and run the program after performing the actions described in paragraph 3 of this section
6) The program will run at http://localhost:8080. Use Postman requests to test the functionality, and alter the requests accordingly to test various scenarios.

V. User credentials

The program generates 5 user Profiles upon startup, including an Admin. Testing some functions will require the usage of the respective user's (or the Admin's) credentials. These are as follows, for the generated Profiles:

1) LOGIN: admin, PASSWORD: AdminPassword123
2) LOGIN: kenny, PASSWORD: CheatingDeath13
3) LOGIN: eric, PASSWORD: RespectMyAuth88
4) LOGIN: stan, PASSWORD: LoveWendy00
5) LOGIN: kyle, PASSWORD: GreenHat74

VI. Endpoints

A detailed description of the available endpoints will be available at http://localhost:8080/swagger-ui/index.html once the project is run. A brief description of the endpoints is as follows:

1) Signing up
   Endpoint: POST /profile/signup
   Sample request body:
   {
   "name": "John",
   "surname": "Doe",
   "jobTitle": "Software Developer",
   "phone": "+1234567890",
   "address": "123 Code St, Dev City",
   "interests": ["Coding", "Gaming", "Reading"],
   "isPublic": false,
   "profileLink": "http://johndoe.dev",
   "login": "johndoe123",
   "password": "SuperSecurePassword123!"
   }

2) Obtaining Profile data
   Endpoint: profile/{user_id}
   Sample response body:
   {
   "name": "Kenny",
   "surname": "McCormick",
   "jobTitle": "Team Lead",
   "phone": "+12223334445",
   "address": "123 South Park St.",
   "interests": [
   "Politics",
   "Sports",
   "Video Games",
   "Music"
   ],
   "isPublic": false,
   "profileLink": "https://en.wikipedia.org/wiki/Kenny_McCormick"
   }

3) Updating Profile data:
   Endpoint: /profile/{user_id}/update
   Sample request body:
   {
   "name": "Kyle",
   "surname": "Broflovski",
   "jobTitle": "Senior Full Stack Developer",
   "phone": "+16667778888",
   "address": "2020 New South Park St.",
   "interests": ["philosophy", "coding", "comedy"],
   "isPublic": true,
   "profileLink": "https://en.wikipedia.org/wiki/Kyle_Broflovski"
   }
   Sample response body:
   {
   "name": "Kyle",
   "surname": "Broflovski",
   "jobTitle": "Senior Full Stack Developer",
   "phone": "+16667778888",
   "address": "2020 New South Park St.",
   "interests": ["philosophy", "coding", "comedy"],
   "isPublic": true,
   "profileLink": "https://en.wikipedia.org/wiki/Kyle_Broflovski"
   }

4) Uploading an Avatar
   Endpoint: /upload-avatar/{user_id}
   Request body: A .jpg, .jpeg, or .png file (max 5MB)
   Sample response: avatars/avatar_2_1733154954701.png

VII. Contact details

Should any questions arise, please feel free to contact me at sergejs.ponomarenko@emendatus.lv, or contact our coordinator for my phone/Telegram numbers.


