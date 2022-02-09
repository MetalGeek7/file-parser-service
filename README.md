# File Parser WebApp
<table>
<tr>
<td>
  A webapp that parses & serves filtered file entries from an HTTP endpoint.
</td>
</tr>
</table>


## Instructions
1. Run build.sh. This will build the app jar and create a Docker image.
2. Copy sample test files in some local directory.
3. Run run.sh with the absolute path of the directory that has sample test files in it, as the argument.
    - Eg: ./run.sh /Users/abhishek/DevSpace/sample-files
4. Use Postman or curl to make a POST Request with body as follows:
    ```
    curl --header "Content-Type: application/json" \
      --request POST \
      --data '{"filename":"sample1.txt", "from":"2001-07-08T11:57:57Z", "to": "2001-07-15T17:14:40Z"}' \
      http://localhost:8279
    ```

## Testing Scenarios
1. Request with valid fileName and dateTime range.
2. Request with invalid formatted DateTime. eg "2001-07-01" or invalid date, eg: "2001-04-31T17:14:40Z". 
    This will return empty JSON array.
3. Request with time range spanning all the entries in a file.
4. Request that includes certain entries in the file that cannot be parsed due to invalid dateTime format. In this case 
    the application still returns all the other valid entries.
5. Request with invalid fileName in the sample files directory. This will return empty JSON array.

### Development
Want to contribute?

To fix a bug or enhance an existing module, follow these steps:

- Fork the repo
- Create a new branch (`git checkout -b feature-enhancement`)
- Make the appropriate changes in the files
- Add changes to reflect the changes made
- Commit your changes (`git commit -m 'Improve feature'`)
- Push to the branch (`git push origin feature-enhancement`)
- Create a Pull Request. Make sure to provide meaningful and concise title and description of the change. 

### Bug / Feature Request

If you find a bug (the app couldn't handle the query and / or gave undesired results), kindly open an issue [here](https://github.com/MetalGeek7/file-parser-service/issues) by including your search request and the expected result.

If you'd like to request a new function, feel free to do so by opening an issue [here](https://github.com/MetalGeek7/file-parser-service/issues).


## Built with 
- [Spring Boot](https://spring.io/projects/spring-boot) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

## To-do
- [ ] Add unit tests for Controller class & Validate request and response data-models. 
- [ ] Create one time index file when the first request arrives for serving large files. Subsequently this index file could
      potentially be used to do binary search since entries are ordered acc. to their timestamp. This could significantly
      decrease the response time.



