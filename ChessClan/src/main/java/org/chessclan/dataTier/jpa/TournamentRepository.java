package org.chessclan.dataTier.jpa;

import java.sql.Date;
import org.chessclan.dataTier.models.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TournamentRepository extends CrudRepository<Tournament, Long>, JpaSpecificationExecutor<Tournament> {
    
    /**
	 * Returns a page of {@link Customer}s with the given lastname.
	 * 
	 * @param tournamentDate
	 * @param pageable
	 * @return
	 */
	Page<Tournament> findByLastname(Date tournamentDate, Pageable pageable);
}