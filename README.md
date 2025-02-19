# Endpoints
### Authentication
- login (GET) - Page for login panel
- login (POST) - Expects form-data containig username and password
- register (GET) - Page for register panel
- register (POST) - Page for user registration
	Expects user dto:
		{
			"username":username,
			"firstName":first-name,
			"lastName":last-name,
			"inviteCode":invitation-code
		}

### Admin
- /admin (GET) - The default url an authenticated admin is redirect to
- /admin/invite-code (POST) - Page for creating an invitation code, used for user creation
  Expects an invitation code dto:
'''		{
			"roleName": enum("student", "teacher", "admin"),
			"associatedEmail": email
		}'''

### Student:
- /student (GET) - The default url an authenticated student is redirected to
- /student/assignments (GET) - Page for all assigned homework assignments
- /student/assignments/id (GET) - Returns only the assignment with the specified id
- /student/assignments (POST) - Submitting assignments
	Expects an assignment dto:
		{
			"id":UUID,
			"solution":solution
		}
- /student/assignments/bulks (POST) - Submitting bulks assignments
	Expects list of assignment dto's
		{
			{"id":UUID, "solution":solution},
			{"id":UUID, "solution":solution},
			{"id":UUID, "solution":solution},
		}
- /student/submitted-assignments (GET) - Page for all submitted homework assignments
- /student/submitted-assignments/id (GET) - Returns only the submitted assignment with the specified id
- /student/grades (GET) - Page for the grades of the student
