package com.broadcom.demo.vehicles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.broadcom.demo.vehicles.repository.*;

@Controller
public class MenuController {
    private final VehicleRepository vehicleRepo;
    private final SaleRepository saleRepo;
    public MenuController(VehicleRepository v, SaleRepository s){this.vehicleRepo=v;this.saleRepo=s;}

    @GetMapping({"/menu","/"})
    public String menu(Model model){
        model.addAttribute("vehicles", vehicleRepo.count());
        model.addAttribute("sales", saleRepo.count());
        model.addAttribute("sold", vehicleRepo.findAll().stream().filter(v->v.isSold()).count());
        return "menu";
    }
}
