package org.example.flighttrackersimulator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimulationController {

    @GetMapping("/Render")
    public String Render() {
        return "Map";
    }

    @GetMapping("/Home")
    public String Home() {
        return "HomePage";
    }
}
