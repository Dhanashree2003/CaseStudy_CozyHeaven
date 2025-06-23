# CaseStudy_CozyHeaven

CozyHeaven - Hotel Booking Management System
CozyHeaven is a full-stack hotel booking management web application built using Spring Boot (Java) for the backend and Angular for the frontend.

It contains 3 core modules:

-> Guest Dashboard

-> Owner Dashboard

-> Admin Dashboard

✨ Features
👤 Guest Dashboard
-> Search hotels and rooms by location, check-in/check-out dates, and filters.

-> Book and cancel rooms.

-> Make secure payments.

-> View and edit personal profile.

🏨 Owner Dashboard
-> Confirm or reject bookings.

-> Manage existing bookings.

-> Add and manage rooms for their hotels.

-> Update owner profile.

👨‍💼 Admin Dashboard
-> Add and manage hotels on the platform.

-> Manage user and owner accounts.

-> View overall system data.

🚀 Tech Stack
 Layer     Technology           
 --------  ------------------
 Frontend  Angular              
 Backend   Spring Boot (Java)   
 Database  MySQL                
 Auth      JWT / Basic Auth     
 Tools     VS Code, Eclipse STS 


 🛠️ Setup Instructions
1. Clone the project

--> git clone https://github.com/yourusername/cozyheaven.git

2. Backend Setup (Spring Boot)
--> Open the /backend folder in Eclipse STS.

--> Make sure JDK 17 or above is installed.

--> Update application.properties with your MySQL username and password.

--> Start the Spring Boot project.

3. Frontend Setup (Angular)
--> Open the /myapp folder in VS Code.

Run the frontend using:
code:
--> ng serve

4. Access the App
-->Open your browser and go to: http://localhost:4200

🔐 Environment Configuration
Update the following config in application.properties (Spring Boot):

properties
code:
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD