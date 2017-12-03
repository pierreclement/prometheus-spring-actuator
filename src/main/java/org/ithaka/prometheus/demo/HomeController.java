package org.ithaka.prometheus.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public void home(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/metrics");
    }
}
