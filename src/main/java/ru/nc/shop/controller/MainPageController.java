package ru.nc.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.nc.shop.model.Order;
import ru.nc.shop.model.Product;
import ru.nc.shop.model.ProductType;
import ru.nc.shop.repository.UserRepository;
import ru.nc.shop.service.OrderService;
import ru.nc.shop.service.ProductService;
import ru.nc.shop.service.ProductTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainPageController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    private List<Product> getProducts(String[] ids, HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        List<Product> products = new ArrayList<>();
        if (order.getProducts() != null) {
            products.addAll(order.getProducts());
        }
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                Product product = productService.getUnsoldProductByIdOfProductType(Long.parseLong(ids[i]));
                products.add(product);
            }
        }
        return products;
    }

    private Double getTotal(List<Product> orderList) {
        Double total = new Double(0);
        for (Product product : orderList) {
            total += product.getProductType().getPrice();
        }
        return total;
    }

    private List<ProductType> getOrderProductTypeList(List<Product> orderList) {
        List<ProductType> productTypeList = new ArrayList<>();
        for (Product product : orderList) {
            productTypeList.add(product.getProductType());
        }
        return productTypeList;
    }

    private List<Product> deleteProducts(List<Product> products, String[] deleteProduct) {
        List<Product> newProductList = products;
        for (int i = 0; i < deleteProduct.length; i++) {
            for (Product product : products) {
                if (product.getProductType().getId().equals(Long.parseLong(deleteProduct[i]))) {
                    newProductList.remove(product);
                    break;
                }
            }
        }
        return newProductList;
    }

    private void addPageAttributes(Model model, List<Product> productList) {
        model.addAttribute("orderProductList", getOrderProductTypeList(productList));
        model.addAttribute("total", getTotal(productList));
    }


    @ModelAttribute("startCurrentWeek")
    public String getStartCurrentWeekForPage() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }

    @ModelAttribute("allProductTypes")
    public List<ProductType> getAllProductTypes() {
        List<ProductType> list = productTypeService.getUnsoldProductTypes();
        for (ProductType productType : list) {
            Collections.reverse(productType.getValues());
        }
        return list;
    }

    @RequestMapping(value = {"/", "/shop"}, method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("order") == null) {
            session.setAttribute("order", new Order());
        }
        return "/shop";
    }

    @RequestMapping(value = {"/shop"}, method = RequestMethod.POST)
    public String placeAnOrder(String[] product, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        session.setMaxInactiveInterval(6000);
        order.setProducts(getProducts(product, session));
        return "redirect:/cart";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String cartPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (request.getUserPrincipal() != null) {
            model.addAttribute("currentUser", userRepository.findByUser_email(request.getUserPrincipal().getName().toString()));
        }
        Order order = (Order) session.getAttribute("order");
        model.addAttribute("orderProductList", getOrderProductTypeList(order.getProducts()));
        model.addAttribute("total", getTotal(order.getProducts()));
        return "/cart";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.POST)
    public String cartPagePost(@Valid @ModelAttribute("orderForm") Order order, BindingResult result, Model model,
                               HttpServletRequest request, String operation, String[] deleteProduct, String dateTime) {
        HttpSession session = request.getSession();
        if (operation.equals("Delete products")) {
            if (deleteProduct != null) {
                Order oldorder = (Order) session.getAttribute("order");
                order.setProducts(deleteProducts(oldorder.getProducts(), deleteProduct));
                session.removeAttribute("order");
                session.setAttribute("order", order);
                addPageAttributes(model, order.getProducts());
                return "/cart";
            }
        } else if (operation.equals("Submit")) {
            if (result.hasErrors() || Objects.equals(dateTime, "")) {
                if (request.getUserPrincipal() != null) {
                    model.addAttribute("currentUser", userRepository.findByUser_email(request.getUserPrincipal().getName().toString()));
                }
                Order oldorder = (Order) session.getAttribute("order");
                order.setProducts(oldorder.getProducts());
                addPageAttributes(model, order.getProducts());
                if (Objects.equals(dateTime, "")) {
                    result.addError(new FieldError("orderForm", "arrivalTime", "Enter arrival time"));
                }
                return "/cart";
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date date = null;
            try {
                date = format.parse(dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date.before(new Date())) {
                if (request.getUserPrincipal() != null) {
                    model.addAttribute("currentUser", userRepository.findByUser_email(request.getUserPrincipal().getName().toString()));
                }
                Order oldorder = (Order) session.getAttribute("order");
                order.setProducts(oldorder.getProducts());
                addPageAttributes(model, order.getProducts());
                model.addAttribute("error", "Incorrect time");
                return "/cart";
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.MILLISECOND, 0);
            order.setArrivalTime(date);
            calendar.add(Calendar.HOUR, -1);
            order.setTimeOfDispatch(calendar.getTime());
            Order oldorder = (Order) session.getAttribute("order");
            order.setProducts(oldorder.getProducts());
            int res = orderService.newOrder(order);
            if (res == 5) {
                if (request.getUserPrincipal() != null) {
                    model.addAttribute("currentUser", userRepository.findByUser_email(request.getUserPrincipal().getName().toString()));
                }
                order.setProducts(oldorder.getProducts());
                addPageAttributes(model, order.getProducts());
                model.addAttribute("error", "At this time, there are no available couriers");
                return "/cart";
            } else if (res == 6) {
                if (request.getUserPrincipal() != null) {
                    model.addAttribute("currentUser", userRepository.findByUser_email(request.getUserPrincipal().getName().toString()));
                }
                order.setProducts(oldorder.getProducts());
                addPageAttributes(model, order.getProducts());
                model.addAttribute("error", "Your list of products empty or just sells");
                return "/cart";
            } else if (res == 0) {
                session.removeAttribute("order");
                SimpleMailMessage mailMessage = new SimpleMailMessage(templateMessage);
                mailMessage.setSubject("Information about your order");
                mailMessage.setTo(order.getEmail());
                String orderLink = "order-" + order.getId() + "-" + order.getCustomerName() + "-" + order.getCustomerSurname();
                String requestURI = request.getRequestURI();
                String urlOfSite = request.getRequestURL().substring(0, request.getRequestURL().indexOf(requestURI));
                mailMessage.setText("You can check information about the order by:\n " + urlOfSite + "/" + orderLink);
                try {
                    mailSender.send(mailMessage);
                } catch (MailException mailException) {
                    mailException.printStackTrace();
                }
                return "redirect:/" + orderLink;
            }
        }
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/order-{Id}-{name}-{surname}"}, method = RequestMethod.GET)
    public String orderPage(@PathVariable("Id") Long id, @PathVariable("name") String name,
                            @PathVariable("surname") String surname,
                            Model model) {
        Order order = orderService.findById(id);
        if (order != null || (checkName(order, name) && checkSurname(order, surname))) {
            Double total = new Double(0);
            List<ProductType> orderList = new ArrayList<>();
            for (Product product : order.getProducts()) {
                orderList.add(product.getProductType());
                total += product.getProductType().getPrice();
            }
            model.addAttribute("orderProductList", orderList);
            model.addAttribute("total", total);
            model.addAttribute("orderForm", order);
            return "/order";
        }
        return "redirect:/shop";
    }

    private boolean checkSurname(Order order, String surname) {
        return order.getCustomerSurname().equals(surname);
    }

    private boolean checkName(Order order, String name) {
        return order.getCustomerName().equals(name);
    }
}
