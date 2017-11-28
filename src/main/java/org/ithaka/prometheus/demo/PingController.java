package org.ithaka.prometheus.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String base() {
        return "hello";
    }
}
