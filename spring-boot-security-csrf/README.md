# spring-boot-security-csrf

## The mechanism of csrf
### Disable csrf
1. Run the application with the profile "disablecsrf" active or use IDE
```bash
# if you are using maven
mvn spring-boot:run -Dspring-boot.run.profiles=disablecsrf -DskipTests
``` 
2. Run hack site by
```bash
# the default listen port is 9090
 ./.bin/server -path target/classes/templates
```
> In the directory .bin there are three versions of http server (server for Mac OS, server-linux-amd64 for linux,
and server-windows-amd64 for windows)
3. Login to the [origin web site](http://localhost:8080)
4. Visit the Hacker's site [Hack page](http://localhost:9090/csrfHack.html)
5. click the submit button in hack web site, you can see *your money has been transferred Ops!*
6. Open the browser's console, e.g. Chrome, open the network or application tab, and you can find the two site
has the same `Cookie: JESSIONID` in http **POST** request(transfer money) Header
> The browser send the same Cookie when submit the form because of the Same-origin strategy

### Enable csrf
> Default spring-security enable the csrf
1. Run the application with no profile active
```bash
mvn spring-boot:run -DskipTests
```
2. do the same as [Disable csrf](###Disable-csrf) 2～5 does
3. But at this time, the **POST** request will not successful and return http status 403
> csrf enable need you to put `_csrf=${_csrf_token}` in your **POST** request body
although with the same cookie

> Note. the Get url in csrfHack.html for transferring money is allowed

## TODOs
- [x] thymeleaf + spring-security to implement csrf
- [x] use thymeleaf-extras-springsecurity5 to set visible area for authenticated users
- [x] use DelegatingPasswordEncoder and InMemoryAuthentication to implement login function
- [x] findout how to use remember-me
- [x] check if the csrf work by using example of transfer money

## further more
- [ ] implement the UserDetail, UserService etc by myself
- [ ] find out how spring-security works
- [ ] implement the csrf service by myself

## Relative Project
[Spring security Remember-me](https://github.com/wzt3309/spring-all/tree/master/spring-boot-security-remember-me)