# Endpoints

### !!!Every endpoint except /login and /register require the user to be authenticated with credentials and JWT token!!!

### Authentication
- /login (GET) - Page for login panel
- /login (POST)
  Expects username and password and returns a JWT token
  ```json
	{
		"roleName": enum("student", "teacher", "admin"),
		"associatedEmail": email
	}
  ```
- /register (GET) - Page for register panel
- /register (POST) - Page for user registration
  Expects user dto:
  ```json
	{
		"username":username,
  	"firstName":first-name,
		"lastName":last-name,
		"inviteCode":invitation-code
	}
  ```

### Admin
- /admin (GET) - The default url an authenticated admin is redirect to
- /admin/invite-code (POST) - Page for creating an invitation code, used for user creation
  Expects an invitation code dto:
  ```json
	{
		"roleName": enum("student", "teacher", "admin"),
		"associatedEmail": email
	}
  ```

### Student:
- /student (GET) - The default url an authenticated student is redirected to
- /student/assignments (GET) - Page for all assigned homework assignments
- /student/assignments/id (GET) - Returns only the assignment with the specified id
- /student/assignments (POST) - Submitting assignments
  Expects an assignment dto:
  ```json
		{
			"id":UUID,
			"solution":solution
		}
  ```
- /student/assignments/bulks (POST) - Submitting bulks assignments
  Expects list of assignment dto's
  ```json
		{
			{"id":UUID, "solution":solution},
			{"id":UUID, "solution":solution},
			{"id":UUID, "solution":solution},
		}
  ```
- /student/submitted-assignments (GET) - Page for all submitted homework assignments
- /student/submitted-assignments/id (GET) - Returns only the submitted assignment with the specified id
- /student/grades (GET) - Page for the grades of the student

# Packages and file structure
###├── src.main.java.com.obelix.homework.platform
│   │   │                   ├── Application.java - the Main class
###│   │   │                   ├── config - Package containing all configurations, not related directly to business logic. Here are the custom exceptions, mapper and security configs
####│   │   │                   │   ├── exception - Custom exceptions
│   │   │                   │   │   ├── NoSuchRoleException.java
│   │   │                   │   │   ├── ResourceNotFoundException.java
│   │   │                   │   │   └── UsernameExistsException.java
####│   │   │                   │   ├── mapper - Mapper configuration
│   │   │                   │   │   ├── converter - Converters, needed by the modelMapper
│   │   │                   │   │   │   └── DtoToHomeworkAssignmentConverter.java
│   │   │                   │   │   └── ModelMapperConfig.java
####│   │   │                   │   └── security - All security configurations. JWT config, filterChains, password encoding, granted authorities
│   │   │                   │       ├── JwtAuthFilter.java
│   │   │                   │       ├── JwtUtils.java
│   │   │                   │       ├── PasswordEncoderConfig.java
####│   │   │                   │       ├── role - Contains the Role enum, responsible for the granted authorities
│   │   │                   │       │   └── Role.java
│   │   │                   │       └── SecurityConfig.java
###│   │   │                   ├── model - The application's core data structures
####│   │   │                   │   ├── dto - Data Transfer Objects used for transferring data between layers of the application
│   │   │                   │   │   ├── CourseDto.java
│   │   │                   │   │   ├── HomeworkAssingmentDto.java
│   │   │                   │   │   ├── InviteCodeDto.java
│   │   │                   │   │   ├── RegisterDto.java
│   │   │                   │   │   └── SubmittedHomeworkAssignmentDto.java
####│   │   │                   │   └── entity - user-related and domain-specific entities that represent the primary data structure in the database
####│   │   │                   │       ├── core - Entities, used for administration
│   │   │                   │       │   └── InviteCode.java
####│   │   │                   │       ├── domain - The core business concepts of the application
│   │   │                   │       │   ├── Course.java
│   │   │                   │       │   ├── Grade.java
│   │   │                   │       │   ├── HomeworkAssignment.java
│   │   │                   │       │   ├── Subject.java
│   │   │                   │       │   └── SubmittedHomeworkAssignment.java
####│   │   │                   │       └── user - User specific entities
│   │   │                   │           ├── Admin.java
│   │   │                   │           ├── Student.java
│   │   │                   │           ├── Teacher.java
│   │   │                   │           └── User.java
###│   │   │                   ├── repo - Repository interfaces for data access and interactions with the database
│   │   │                   │   ├── CourseRepo.java
│   │   │                   │   ├── GradeRepo.java
│   │   │                   │   ├── HomeworkAssignmentRepo.java
│   │   │                   │   ├── InviteCodeRepo.java
│   │   │                   │   ├── SubjectRepo.java
│   │   │                   │   ├── SubmittedHomeworkAssignmentRepo.java
│   │   │                   │   ├── TeacherRepository.java
│   │   │                   │   └── UserDetailsRepo.java
###│   │   │                   └── web -The controller and service layers, handling HTTP requests and business logic for the application
│   │   │                       ├── controller
│   │   │                       │   ├── AdminController.java
│   │   │                       │   ├── AuthenticationController.java
│   │   │                       │   ├── StudentController.java
│   │   │                       │   ├── TeacherController.java
│   │   │                       │   └── UserController.java
│   │   │                       └── service
│   │   │                           ├── InviteCodeService.java
│   │   │                           ├── StudentService.java
│   │   │                           ├── TeacherService.java
│   │   │                           └── UserService.java
