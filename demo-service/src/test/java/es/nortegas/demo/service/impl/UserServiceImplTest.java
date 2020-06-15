package es.nortegas.demo.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import es.nortegas.demo.exception.ContentNotFoundException;
import es.nortegas.demo.persistence.entity.UserEntity;
import es.nortegas.demo.persistence.enums.EnumEntityStatus;
import es.nortegas.demo.persistence.repository.UserRepository;
import es.nortegas.demo.service.BcPasswordEncoderService;
import es.nortegas.demo.service.dto.AccreditedUserDTO;
import es.nortegas.demo.service.dto.UserDTO;
import es.nortegas.demo.service.enums.EnumStatus;

public class UserServiceImplTest {
    
    private UserRepository userRepository;
    private Mapper mapper;
    private BcPasswordEncoderService bcPasswordEncoderService;
    
    private UserServiceImpl userServiceImpl;
    
    private static final Long DEFAULT_ID = 1L;
    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.now();
    
    @Before
    public void setUp() throws Exception {
        this.userRepository = mock(UserRepository.class);
        this.mapper = mock(Mapper.class);
        this.bcPasswordEncoderService = mock(BcPasswordEncoderService.class);
        
        this.userServiceImpl = new UserServiceImpl(userRepository, mapper,
                bcPasswordEncoderService);
    }
    
    private UserEntity createDefaultUserEntity() {
        
        UserEntity userEntity = new UserEntity();
        userEntity.setId(DEFAULT_ID);
        userEntity.setName("Pepe");
        userEntity.setSurname1("Saenz");
        userEntity.setSurname2("Perez");
        userEntity.setNif("123456789J");
        userEntity.setNickname("pepes");
        userEntity.setPassword("124");
        userEntity.setStatus(EnumEntityStatus.ACTIVO);
        
        return userEntity;
    }
    
    private AccreditedUserDTO createDefaultUserDTO() {
        
        AccreditedUserDTO userDAO = new AccreditedUserDTO();
        userDAO.setId(DEFAULT_ID);
        userDAO.setName("Pepe");
        userDAO.setSurname1("Saenz");
        userDAO.setSurname2("Perez");
        userDAO.setNif("123456789J");
        userDAO.setNickname("pepes");
        userDAO.setPassword("124");
        userDAO.setStatus(EnumStatus.ACTIVO);
        
        return userDAO;
    }
    
    @Test
    public void shouldCreateUser() {
        
        String password = "passCodec";
        
        //given
        doReturn(createDefaultUserEntity()).when(this.mapper).map(createDefaultUserDTO(),
                UserEntity.class);
        doReturn(createDefaultUserEntity()).when(this.userRepository).save(Mockito.any());
        doReturn(password).when(this.bcPasswordEncoderService).encode(Mockito.anyString());
        
        //when
        this.userServiceImpl.create(createDefaultUserDTO());
        
        //then
        verify(this.mapper, times(1)).map(createDefaultUserDTO(), UserEntity.class);
        verify(this.userRepository, times(1)).save(Mockito.any());
        verify(this.bcPasswordEncoderService, times(1)).encode(Mockito.anyString());
        
    }
    
    @Test
    public void shouldFindById() {
        
        UserEntity userEntity = createDefaultUserEntity();
        
        //given
        doReturn(Optional.of(userEntity)).when(this.userRepository).findById(Mockito.anyLong());
        
        //when
        this.userServiceImpl.findById(1L);
        
        //then
        verify(this.userRepository, times(1)).findById(Mockito.anyLong());
        
    }
    
    @Test(expected = ContentNotFoundException.class)
    public void shouldFindById2() {
        
        //given
        doReturn(Optional.empty()).when(this.userRepository).findById(Mockito.anyLong());
        
        //when
        this.userServiceImpl.findById(1L);
    }
    
    @Test
    public void shouldSearchUser() {
        
        //given
        UserEntity userEntity = createDefaultUserEntity();
        List<UserEntity> listUsers = new ArrayList<UserEntity>();
        listUsers.add(userEntity);
        Page<UserEntity> pageUsers = new PageImpl<UserEntity>(listUsers);
        
        doReturn(pageUsers).when(this.userRepository).searchUsers(Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.isNull());
        
        //when
        Page<UserDTO> pageReturned = this.userServiceImpl.search("", "", "", "", "ACTIVO", null);
        
        //then
        verify(this.userRepository, times(1)).searchUsers(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.isNull());
        
        assertTrue(pageReturned.getContent().size() == 1);
        
    }
    
    @Test(expected = ContentNotFoundException.class)
    public void shouldNotUpdate() {
        
        //given
        doReturn(Optional.empty()).when(this.userRepository).findById(Mockito.anyLong());
        
        //when
        this.userServiceImpl.update(1L, new AccreditedUserDTO());
        
    }
    
    @Test
    public void shouldupdate() {
        
        //given
        AccreditedUserDTO dto = this.createDefaultUserDTO();
        dto.setId(DEFAULT_ID);
        
        UserEntity userEntityBD = createDefaultUserEntity();
        doReturn(Optional.of(userEntityBD)).when(this.userRepository).findById(Mockito.anyLong());
        
        UserEntity newUserEntity = createDefaultUserEntity();
        newUserEntity.setNickname("nuevo");
        newUserEntity.setPassword("newPass");
        doReturn(newUserEntity).when(this.mapper).map(createDefaultUserDTO(), UserEntity.class);
        
        doReturn(newUserEntity).when(this.userRepository).save(Mockito.any());
        
        //when
        this.userServiceImpl.update(DEFAULT_ID, dto);
        
        //verify
        verify(this.userRepository, times(1)).findById(Mockito.anyLong());
    }
    
    @Test(expected = ContentNotFoundException.class)
    public void shouldNotDelete() {
        
        //given
        doReturn(Optional.empty()).when(this.userRepository).findById(Mockito.anyLong());
        
        //when
        this.userServiceImpl.delete(1L);
        
    }
    
    @Test
    public void shouldDelete() {
        
        //given
        UserDTO dto = this.createDefaultUserDTO();
        dto.setId(DEFAULT_ID);
        
        UserEntity userEntityBD = createDefaultUserEntity();
        doReturn(Optional.of(userEntityBD)).when(this.userRepository).findById(Mockito.anyLong());
        
        UserEntity newUserEntity = createDefaultUserEntity();
        newUserEntity.setDtCancel(DEFAULT_DATE);
        newUserEntity.setDtEntry(DEFAULT_DATE);
        newUserEntity.setStatus(EnumEntityStatus.INACTIVO);
        doReturn(newUserEntity).when(this.userRepository).save(Mockito.any());
        
        //when
        this.userServiceImpl.delete(DEFAULT_ID);
        
        //verify
        verify(this.userRepository, times(1)).findById(Mockito.anyLong());
    }
    
}
