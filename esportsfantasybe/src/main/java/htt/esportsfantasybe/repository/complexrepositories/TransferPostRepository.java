package htt.esportsfantasybe.repository.complexrepositories;

import htt.esportsfantasybe.model.complexentities.TransferPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransferPostRepository extends JpaRepository<TransferPost, Integer> {

    public List<TransferPost> findAllByLeagueuuidOrderByDateDesc(UUID leagueuuid);

    public List<TransferPost> findAllByLeagueuuid(UUID leagueuuid);
}
