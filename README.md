Instructions for running application

1. Run build.sh
2. Copy sample test files in a some local directory.
3. Run run.sh with arg as absolute path of directory where sample test files are stored.
4. Use Postman or curl to make a POST Request with body as follows:
   {"filename":"sample1.txt", "from":"2002-05-01T05:04:55Z", "to": "2002-05-01T05:15:18Z"}
   where from and to are DateTime ranges for filtering file entries
   
   
TODO:
a. Add unit tests for Controller class
b. Configure logging to write logs to a file   
