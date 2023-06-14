package com.example.practice_3.controller;

import com.example.practice_3.model.Cart;
import com.example.practice_3.model.Product;
import com.example.practice_3.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@SessionAttributes("cart")
public class ProductController {
    @Autowired
    private IProductService productService;

    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }
    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView=new ModelAndView("/create");
        modelAndView.addObject("product",new Product());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute("product") Product product){
        productService.save(product);
        ModelAndView modelAndView=new ModelAndView("/create");
        modelAndView.addObject("product",new Product());
        return modelAndView;
    }

    @GetMapping("/shop")
    public ModelAndView showShop() {
        ModelAndView modelAndView = new ModelAndView("/shop");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, @ModelAttribute Cart cart, @RequestParam("action") String action) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return "/error.404";
        }
        if (action.equals("show")) {
            cart.addProduct(productOptional.get());
            return "redirect:/shopping-cart";
        }
        cart.addProduct(productOptional.get());
        return "redirect:/shop";
    }
}