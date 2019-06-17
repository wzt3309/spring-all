@RestController
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return "hello, world";
    }
}
