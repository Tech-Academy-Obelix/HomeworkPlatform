# Endpoints

### !!!Every endpoint except /login and /register require the user to be authenticated with credentials and JWT token!!!

## Authentication
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

## Admin
### User Management
- /admin/users (GET) - Displays all of the users. Returns a UserDto list. It contains id, username, firstname, lastname, role
- /admin/users/id (PUT) - Changes the role of that user. Expects a String (so no key and no quotation marks):
  ```
		role // can be either student, teacher, admin
  ```
- /admin/users/id (DELETE) - Deletes user with that id
  
### Invitation Codes
- /admin (GET) - The default url an authenticated admin is redirect to
- /admin/invite-code (GET) - Displays all invitation codes. Returns an InviteCode list. It contains id, email
- /admin/invite-code (POST) - Page for creating an invitation code, used for user creation
  Expects an invitation code dto:
  ```
	{
		"role": role, // can be "student", "teacher" or "admin"
		"email": email
	}
  ```
- admin/invite-code/id (DELETE) - Deletes invitation code with that id

### Subjects
- /admin/subjects (GET) - Displays all subjects. Returns a list of SubjectDto
- /admin/subjects/id (GET) - Get subject by id
- /admin/subjects (POST) - Create a new subject. Expects a String with the name (Again, no keys or quoation marks!!!):
  ```
  	name
  ```
- /admin/subjects/id (DELETE) - Deletes the subject by id

### Courses
- /admin/courses (GET) - Displays all of the courses. Returns list of CourseDto. Contains id, name, SubjectInCourseDto (contains id, name, userDto of its teacher)
  , list of UserDtos of the students inside the course and a list of
  HomeworkAssignmentCreateDtos (contains id, name, description, SubjectDto of the subject the homework is assigned to, the date assigned and the date due)

## Student:
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

## Teacher
- /teachers/assignments (POST) - Submitting assignments Expects an assignment dto:
  ```json
		{
			"id":UUID,
			"solution":solution
		}
  ```
- /teachers/assignments (GET) - Page for all assigned homework assignments
- /teachers/assignments/{id} (GET) - Returns only the assignment with the specified id
- /teachers/assignments/{id} (POST) - Assign an assignment to a course, expects CourseDto
- /teachers/submitted-assignments (GET) - Page for all submitted homework assignments
- /teachers/submitted-assignments/{id} (GET) - Returns only the submitted assignment with the specified id
- /teachers/submitted-assignments/{id} (POST) - Grade a submitted assignment, expects GradeDto
- /teachers/own-course (GET) - Get the teacher's own course
- /teachers/courses (GET) - Page for all courses
- /teachers/courses/{id} (GET) - Returns only the course with the specified id

# Project Structure

## src.main.java.com.obelix.homework.platform

## Application.java
- The Main class.

## config
- Package containing all configurations, not directly related to business logic. Includes custom exceptions, mapper configurations, and security settings.

  #### exception
	- Custom exceptions for handling specific errors.
		- `NoSuchRoleException.java`
		- `ResourceNotFoundException.java`
		- `UsernameExistsException.java`

  #### mapper
	- ModelMapper configuration and custom converters.
		- `DtoToHomeworkAssignmentConverter.java`
		- `ModelMapperConfig.java`

  #### security
	- All security-related configurations, such as JWT configuration, filter chains, password encoding, and granted authorities.
		- `JwtAuthFilter.java`
		- `JwtUtils.java`
		- `PasswordEncoderConfig.java`
		- `SecurityConfig.java`

  #### role
	- Contains the `Role` enum, responsible for granted authorities.
		- `Role.java`

## model
- The application's core data structures, including DTOs and entities.

  #### dto
	- Data Transfer Objects used for transferring data between layers of the application.
		- `CourseDto.java`
		- `HomeworkAssignmentDto.java`
		- `InviteCodeDto.java`
		- `RegisterDto.java`
		- `SubmittedHomeworkAssignmentDto.java`

  #### entity
	- User-related and domain-specific entities representing the primary data structure in the database.

	  ##### core
		- Entities used for administration.
			- `InviteCode.java`

	  ###### domain
		- Core business concepts of the application.
			- `Course.java`
			- `Grade.java`
			- `HomeworkAssignment.java`
			- `Subject.java`
			- `SubmittedHomeworkAssignment.java`

	  ###### user
		- User-specific entities.
			- `Admin.java`
			- `Student.java`
			- `Teacher.java`
			- `User.java`

## repo
- Repository interfaces for data access and interactions with the database.
	- `CourseRepo.java`
	- `GradeRepo.java`
	- `HomeworkAssignmentRepo.java`
	- `InviteCodeRepo.java`
	- `SubjectRepo.java`
	- `SubmittedHomeworkAssignmentRepo.java`
	- `TeacherRepository.java`
	- `UserDetailsRepo.java`

## web
- The controller and service layers, handling HTTP requests and business logic.

  #### controller
	- Handles HTTP requests and user interactions.
		- `AdminController.java`
		- `AuthenticationController.java`
		- `StudentController.java`
		- `TeacherController.java`
		- `UserController.java`

  #### service
	- Contains business logic and service layer for handling operations.
		- `InviteCodeService.java`
		- `StudentService.java`
		- `TeacherService.java`
		- `UserService.java`
