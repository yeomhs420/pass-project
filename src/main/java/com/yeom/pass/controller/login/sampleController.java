package com.yeom.pass.controller.login;

import com.yeom.pass.repository.user.UserDto;
import com.yeom.pass.repository.user.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class sampleController {

    @PostMapping("/test")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> test1(@Valid UserDto userDto, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "victolee");
        map.put("age", 26);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
