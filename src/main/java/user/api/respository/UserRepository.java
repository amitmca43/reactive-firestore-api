package user.api.respository;

import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

import user.api.model.User;


@Repository
public interface UserRepository extends FirestoreReactiveRepository<User> {

}
