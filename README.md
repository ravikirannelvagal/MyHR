# MyHR
This project can be pulled into your local repository and be built. Use Maven to build the project by executing "mvn clean package" from the root folder of the project. This will ensure all tests are run and the project is built as a jar file in the target folder.

To run the project, go to the target folder and execute "java -jar <jar file name>"
You will see a bunch of logs that suggests the project is deployed on an embedded Tomcat server.
Go to http://localhost:8080 to view the home page of this project.
The Database can be accessed at http://localhost:8080/h2/

The functional endpoints of the project from the root are as below
/appManager: The end point which tells about the possible actions one can take on an application. The actions are as below
/appManager/newAppln: Create a new application
/appManager/applns: view all created applications
/appManager/applns/{id}: view a particular application
/appManager/applns/{id}/{I/R/H}: update the application status accordingly to I: Invited or R:Rejected or H:Hired
/appManager/viewApplnsForJob/{jobOfferId}: view all applications for a particular job id
/appManager/applyToJob/{jobId}: apply to a particular job directly
/appManager/applnHome: go to applications home

/jobManager: The end point which tells about the possible actions one can take on a job offer. The actions are as below.
/jobManager/newJob: Create a new job
/jobManager/jobs: view all created jobs
/jobManager/jobs/{id}: view a particular job
/jobManager/jobHome: go to jobs home
