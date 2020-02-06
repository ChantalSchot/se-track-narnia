package nl.capgemini.setracknarnia.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MustacheController {

	@GetMapping
	public String getHome() {
		return "index";
	}
}
