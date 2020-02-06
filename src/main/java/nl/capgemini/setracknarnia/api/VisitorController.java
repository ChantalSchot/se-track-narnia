package nl.capgemini.setracknarnia.api;

import nl.capgemini.setracknarnia.model.Visitor;
import nl.capgemini.setracknarnia.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
@RequestMapping ("api/visitors")
public class VisitorController {

	@Autowired
	private VisitorRepository visitorRepository;

	@GetMapping
	public Iterable<Visitor> getVisitorList() {
		return this.visitorRepository.findAll();
	}

	@GetMapping(value = "{id}", produces = "application/json")
	public ResponseEntity<Visitor> getVisitorById(@PathVariable long id) {
		Optional<Visitor> optionalVisitor = this.visitorRepository.findById(id);

		if (optionalVisitor.isPresent()) {
			Visitor visitor = optionalVisitor.get();
			return ResponseEntity.ok(visitor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("narnia")
	public List<Visitor> getVisitorsInNarnia() {
		return this.visitorRepository.findAllByInNarniaTrue();
	}

	@PostMapping(consumes = "application/json", produces="application/json")
	public ResponseEntity<Visitor> createVisitor(@RequestBody Visitor newVisitor) {
		Visitor visitor = this.visitorRepository.save(newVisitor);
		return ResponseEntity.ok(visitor);
	}

	@PutMapping(consumes = "application/json", produces="application/json")
	public ResponseEntity<Visitor> updateVisitor(@RequestBody Visitor updatedVisitor) {
		Visitor visitor = this.visitorRepository.save(updatedVisitor);
		return ResponseEntity.ok(visitor);
	}

	// Returning a Singleton will allow result to print (to string). Otherwise a callback function should have been made inside JQuery ajax.
	@PutMapping(value = "narnia/enter", consumes = "application/json", produces = "application/json")
	public Set enterCloset(@RequestBody Visitor enterVisitor) {
		Visitor visitor = this.visitorRepository.findById(enterVisitor.getId()).get();

		// Get list for Narnia visitors
		List<Visitor> currentVisitors = this.visitorRepository.findAllByInNarniaTrue();

		// Check whether visitor is already inside:
		if (visitor.isInNarnia()) {
			String message = visitor.getFirstName() + " is already inside.";
			return Collections.singleton(message);
		} else if (currentVisitors.size() >= 5) {

			String message = "Narnia is full! Please wait until current visitors have left...";
			return Collections.singleton(message);
		} else {
			visitor.setInNarnia(true);
			updateVisitor(visitor);

			String message = visitor.getFirstName() + " has entered Narnia - have fun!";
			return Collections.singleton(message);
		}
	}


	// Returning a Singleton will allow result to print (to string). Otherwise a callback function should have been made inside JQuery ajax.
	@PutMapping(value = "narnia/exit", consumes = "application/json", produces = "application/json")
	public Set exitCloset(@RequestBody Visitor exitVisitor) {
		Visitor visitor = this.visitorRepository.findById(exitVisitor.getId()).get();

		// Check whether visitor is inside Narnia before exiting:
		if (visitor.isInNarnia()) {
			visitor.setInNarnia(false);
			updateVisitor(visitor);
			String message = visitor.getFirstName() + " has left Narnia. I hope they had a good time!";
			return Collections.singleton(message);
		} else {
			String message = visitor.getFirstName() + " is not in Narnia right now, so he cannot leave...";
			return Collections.singleton(message);
		}
	}

	@DeleteMapping (consumes = "application/json")
	public void deleteVisitor(@RequestBody Visitor visitor) {
		this.visitorRepository.delete(visitor);
	}

	@PostConstruct
	public void init() {
		Visitor one = new Visitor();
		one.setFirstName("Piet");
		one.setLastName("Plezier");
		one.setAge(52);
		one.setCity("Amsterdam");
		this.visitorRepository.save(one);

		Visitor two = new Visitor();
		two.setFirstName("Klaas");
		two.setLastName("Vaak");
		two.setAge(92);
		two.setCity("De Maan");
		this.visitorRepository.save(two);

		Visitor three = new Visitor();
		three.setFirstName("Henk");
		three.setLastName("Schenk");
		three.setAge(18);
		three.setCity("Rotterdam");
		this.visitorRepository.save(three);

		Visitor four = new Visitor();
		four.setFirstName("Rita");
		four.setLastName("Skeeter");
		four.setAge(38);
		four.setCity("Little Whinging");
		this.visitorRepository.save(four);

		Visitor five = new Visitor();
		five.setFirstName("Klazien");
		five.setLastName("Aveen");
		five.setAge(60);
		five.setCity("Klazienaveen");
		this.visitorRepository.save(five);

		Visitor six = new Visitor();
		six.setFirstName("Ansje");
		six.setLastName("Tweedehansje");
		six.setAge(33);
		six.setCity("Broek op Langedijk");
		this.visitorRepository.save(six);

		Visitor seven = new Visitor();
		seven.setFirstName("Boudewijn");
		seven.setLastName("de Groot");
		seven.setAge(67);
		seven.setCity("Avond");
		this.visitorRepository.save(seven);
	}
}
