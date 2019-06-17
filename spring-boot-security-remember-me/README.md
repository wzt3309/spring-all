# spring-boot-security-remember-me

This project using default `loginPage` as login form.

`formLogin.successForwardUrl("")` should get your attention, beacause when you login with the form, the http method is **POST**, and after you are successfully authenticated then the `ForwardAuthenticationSuccessHandler` will forward the url you set and **still with the http method POST**, so it may cause `HttpRequestMethodNotSupportedException`.

but instead, you can use `defaultSuccessUrl`, it will redirect the request, so it will not keep the same **POST method**
`defaultSuccessUrl(url, false/default)` will not redirect the request if you visited a secure page prior to authenticating

# The mechanism
The mechanism will create an additional cookie – the “remember-me”(u can change the cookie name by call `rememberMeCookieName`) cookie – when the user logs in.

The **Remember Me cookie** contains the following data:

- **username** – to identify the logged in principal
- **expirationTime** – to expire the cookie; default is 2 weeks
- **MD5 hash** – of the previous 2 values – username and expirationTime, plus the password and the predefined key(u can specific the key by call `key()`)

First thing to notice here is that both the username and the password are part of the cookie – this means that, if either is changed, the cookie is no longer valid. Also, the username can be read from the cookie.

Additionally, it is important to understand that this mechanism is potentially vulnerable if the remember me cookie is captured. The cookie will be valid and usable until it expires or the credentials are changed.

# The practice
To easily see the remember me mechanism working, you can:

- log in with remember me active
- wait for the session to expire (or remove the JSESSIONID cookie in the browser)
- refresh the page

Without remember me active, after the session expires(JESSIONID cookie is expire) the user should be **redirected back to the login page**. With remember me, the user now stays logged in with the help of the new token/cookie.
