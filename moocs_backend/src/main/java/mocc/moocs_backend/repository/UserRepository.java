package mocc.moocs_backend.repository;


import mocc.moocs_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
        User findByUserName(String username);
}
