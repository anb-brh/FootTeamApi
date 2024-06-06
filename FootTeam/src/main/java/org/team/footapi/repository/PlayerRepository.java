package org.team.footapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.footapi.model.Player;
/**
 * Repository to access the data of a Player
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
