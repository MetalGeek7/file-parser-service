# ![WebApp](https://iharsh234.github.io/WebApp/images/demo/demo_landing.JPG)
# WebApp
<table>
<tr>
<td>
  A webapp that process and serves filtered file entries from an HTTP endpoint.
</td>
</tr>
</table>


## Instructions
1. Run build.sh. This will build the app jar and create a Dockerfile with it.
2. Copy sample test files in some local directory.
3. Run run.sh with the absolute path of directory that has sample test files in it, as the argument.
    - Eg. ./run.sh /Users/abhishek/DevSpace/sample-files
4. Use Postman or curl to make a POST Request with body as follows:
    ```
    curl --header "Content-Type: application/json" \
      --request POST \
      --data '{"filename":"sample1.txt", "from":"2001-07-08T11:57:57Z", "to": "2001-07-15T17:14:40Z"}' \
      http://localhost:8279
    ```

## Testing Scenarios
1. Parse entire file
2. POST request with invalid formatted DateTime. eg "2001-07-01" or invalid date, eg: "2001-04-31T17:14:40Z"
3. 

### Charts
![](https://iharsh234.github.io/WebApp/images/demo/demo_chart1.JPG)


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

If you find a bug (the app couldn't handle the query and / or gave undesired results), kindly open an issue [here](https://github.com/iharsh234/WebApp/issues/new) by including your search query and the expected result.

If you'd like to request a new function, feel free to do so by opening an issue [here](https://github.com/iharsh234/WebApp/issues/new). Please include sample queries and their corresponding results.


## Built with 
- [Spring Boot](https://spring.io/projects/spring-boot) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

## To-do
- [ ] Add unit tests for Controller class 
- [ ] Create one time index file for parsing and serving large files. Currently serving a large range of entries from a huge file takes considerable amount of time.
- [ ] Try to  



