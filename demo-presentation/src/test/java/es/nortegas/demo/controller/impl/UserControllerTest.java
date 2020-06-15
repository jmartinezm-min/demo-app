package es.nortegas.demo.controller.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import es.nortegas.demo.service.UserService;
import es.nortegas.demo.service.dto.AccreditedUserDTO;
import es.nortegas.demo.service.dto.UserDTO;

@RunWith(SpringRunner.class)
public class UserControllerTest {
    
    private UserService userService;
    private UserControllerImpl userControllerImpl;
    
    @Before
    public void setUp() throws Exception {
        userService = mock(UserService.class);
        userControllerImpl = new UserControllerImpl(userService);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }
    
    @Test
    public void shouldCreateContact() {
        
        // given
        AccreditedUserDTO dto = new AccreditedUserDTO();
        dto.setId(1L);
        doReturn(dto).when(userService).create(Mockito.any());
        
        // when
        ResponseEntity<UserDTO> response = userControllerImpl.create(dto);
        
        // then
        assertTrue(response.getHeaders().get(HttpHeaders.LOCATION).get(0)
                .equals("http://localhost/users/1"));
        assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
        
        verify(userService, times(1)).create(Mockito.any());
    }
    
    @Test
    public void shouldFindById() {
        
        // when
        ResponseEntity<UserDTO> response = userControllerImpl.findById(1L);
        
        // then
        assertTrue(response != null);
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    }
    
    @Test
    public void shouldSearchNotes() {
        
        //given
        UserDTO dto = new UserDTO();
        dto.setId(1L);
        
        List<UserDTO> listUsersDTO = new ArrayList<UserDTO>();
        listUsersDTO.add(dto);
        
        final Page<UserDTO> pageUserDTO = new PageImpl<UserDTO>(listUsersDTO);
        
        doReturn(pageUserDTO).when(userService).search(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.isNull());
        
        // when
        ResponseEntity<Page<UserDTO>> response = userControllerImpl.findAll("", "", "", "",
                "ACTIVO", null);
        
        // then
        verify(userService, times(1)).search(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.isNull());
        
        assertTrue(response != null && response.getBody().getContent().get(0).getId() == 1L);
        
    }
    
    @Test
    public void shouldUpdate() {
        
        // given        
        AccreditedUserDTO requestDto = new AccreditedUserDTO();
        requestDto.setName("nombre1");
        
        doNothing().when(this.userService).update(Mockito.anyLong(), Mockito.any());
        
        // when
        ResponseEntity<UserDTO> response = this.userControllerImpl.update(1L, requestDto);
        
        // then
        assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
        
        verify(userService, times(1)).update(Mockito.anyLong(), Mockito.any());
    }
    
    @Test
    public void shouldDelete() {
        
        // given
        AccreditedUserDTO requestDto = new AccreditedUserDTO();
        requestDto.setName("nombre1");
        
        doNothing().when(this.userService).delete(Mockito.anyLong());
        
        // when
        ResponseEntity<UserDTO> response = this.userControllerImpl.delete(1L);
        
        // then
        assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
        
        verify(userService, times(1)).delete(Mockito.anyLong());
        
    }
    
}
