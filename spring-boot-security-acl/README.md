# spring-boot-security-acl
Overview and Get Start of Spring Security ACL Futures.

# Dependency
When using `spring-boot-starter-parent`, we can add dependencies like below.
```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-acl</artifactId>
            <version>${spring-security.version}</version>
        </dependency>
        <!-- just for h2 console -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>net.sf.ehcache</groupId>
            <artifactId>ehcache</artifactId>
        </dependency>

    </dependencies>
```
Spring Security ACL requires cache to store ACL entries and identity object, so we need 
to add ehcache support.Besides, we use H2 database to store ACL required tables.
# Configuration
## ACL database
To use spring security, we need to create four mandatory tables in the database.

### Table **acl_class**
column|description
------|-----------
id    |primary key
class |the qualified class name of secured domain objects, for examples: `a.b.Foo`

### Table **acl_sid**
column|description
------|-----------
id   |primary key
sid  |which is username or role name. `sid` stands for Security Identity
principal | 0 or 1, indicate that the corresponding `sid` is principal (username, such as Mike etc)
or authority (role, such as ROLE_ADMIN, ROLE_USER etc)

### Table **acl_object_identity**
column|description
------|-----------
id    |primary key
object_id_class | define the class of domain class, *link to* `acl_class(id)`
object_id_identity | domain object can be stored in many tables depending on the class. Hence, this field store the target object primary key
parent_object | specific parent of this object identify within this table, *link to* `acl_object_identity(id)`
owner_sid | id of object owner, *link to* `acl_sid(id)`
entries_inheriting | whether acl entries of this object inherits from the parent object (acl entries defined in `acl_entry`)

### Table **acl_entry**
column|description
------|-----------
id    | primary key
acl_object_identity | specify the object identity, *link to* `acl_object_identity(id)`
ace_order | the order of current entry in ACLs of corresponding object identity
sid | the target sid which the permission is granted to or denied from, *link to* `acl_sid(id)`
mask| the integer bit mask that represents the actual permission be granted or denied (like using READ(`0001`) WRITE(`0010`))
granting | value 1 means granting, value 0 means denying
audit_success | for auditing
audit_failure | for auditing


# TODOs
- [ ] Be familiar with using **spring-jpa**
- [ ] Be familiar with using spring-boot-test and traditional spring-test, and try to point out the difference
- [ ] Be familiar with the important classes of ACL
- [ ] *More to do*