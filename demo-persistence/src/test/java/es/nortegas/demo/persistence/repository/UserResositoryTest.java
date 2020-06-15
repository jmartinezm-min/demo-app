package es.nortegas.demo.persistence.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import es.nortegas.demo.persistence.entity.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
public class UserResositoryTest {
    
    @Autowired
    private UserRepository noteDao;
    
    @Test
    @Transactional
    public void shouldCreateNote() {
        
        // given
        UserEntity entity = this.createDefaultUserEntity();
        
        // when
        final UserEntity note = this.noteDao.save(entity);
        
        // then
        assertTrue(note.getId() != null);
    }
    
    private UserEntity createDefaultUserEntity() {
        
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Pepe");
        userEntity.setSurname1("Saenz");
        userEntity.setSurname2("Perez");
        userEntity.setNif("123456789J");
        userEntity.setNickname("pepes");
        userEntity.setPassword("124");
        
        return userEntity;
    }
    
}
