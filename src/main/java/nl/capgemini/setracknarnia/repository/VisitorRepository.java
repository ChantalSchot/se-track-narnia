package nl.capgemini.setracknarnia.repository;

import nl.capgemini.setracknarnia.model.Visitor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends CrudRepository<Visitor, Long> {

	public List<Visitor> findAllByInNarniaTrue();

}
