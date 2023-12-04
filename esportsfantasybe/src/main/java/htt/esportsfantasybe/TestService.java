package htt.esportsfantasybe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
   @Autowired
    private TestRepository testRepository;

   public Test insertarTest(int newid){
       Test test = new Test();
        test.setId(newid);
        return testRepository.save(test);
   }
}
