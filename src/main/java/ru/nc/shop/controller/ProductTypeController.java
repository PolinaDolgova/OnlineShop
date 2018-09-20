package ru.nc.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.thymeleaf.util.ArrayUtils;
import ru.nc.shop.model.*;
import ru.nc.shop.repository.*;
import ru.nc.shop.service.ProductTypeService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.*;

@Controller
public class ProductTypeController {
    @Autowired
    private ProductTypeService service;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private UnitOfMeasurementRepository unitOfMeasurementRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;


    String nameProductTypeModification;

    @ModelAttribute("unitList")
    public List<UnitOfMeasurement> getUnits() {
        return unitOfMeasurementRepository.findAll();
    }

    @ModelAttribute("zoneList")
    public List<Warehouse> getZones() {
        return warehouseRepository.findAll();
    }


    @ModelAttribute("productTypeByName")
    public ProductType getProductTypeByName(String name) {
        return productTypeRepository.findByName(name);
    }

    @ModelAttribute("allProductTypes")
    public List<ProductType> getAllProductTypes() {
        List<ProductType> list = productTypeRepository.findAll();
        for (ProductType productType : list) {
            Collections.reverse(productType.getValues());
        }
        return list;
    }

    @RequestMapping(value = "/listProductType", method = RequestMethod.GET)
    public String showAllProductTypes(Model model) {
        return "/listProductType";
    }

    @RequestMapping(value = "/listProductType", method = RequestMethod.POST)
    public String actionsWithProductTypes(String operation, Long productTypeId, Model model) {
        if (operation.equals("Create new")) {
            return "redirect:/productTypeCreate";
        } else if (productTypeId == null) {
            model.addAttribute("error", "Check product");
            return "/listProductType";
        } else {
            if (operation.equals("Delete")) {
                service.deleteProductType(productTypeId);
                return "redirect:/listProductType";
            } else {
                nameProductTypeModification = productTypeRepository.findProductTypeById(productTypeId).getName();
                return "redirect:/productTypeModify";
            }
        }
    }

    @RequestMapping(value = "/productTypeCreate", method = RequestMethod.GET)
    public String productType(Model model) {
        model.addAttribute("productTypeName", "");
        return "/productTypeCreate";
    }

    @RequestMapping(value = "/productTypeCreate", method = RequestMethod.POST)
    public String createProductType(String name, String categories, String producer, String price,
                                    String zone_id, Long unit_id, String[] attributes, String[] values,
                                    @RequestParam("fileOfProduct") MultipartFile file, Model model) {
        String image = saveFile(file);
        Integer result = 0;
        result = service.createProductType(name, categories, producer, price, zone_id, unit_id, attributes, values, image);
        if (result == 1) {
            return "redirect:/listProductType";
        } else {
            model.addAttribute("error", "Product type is already exists");
            return "/productTypeCreate";
        }
    }

    @RequestMapping(value = "/productTypeModify", method = RequestMethod.GET)
    public String showProductTypeModify(Model model) {
        ProductType product = productTypeRepository.findByName(nameProductTypeModification);
        model.addAttribute("productType", product);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < product.getCategories().size(); i++) {
            builder.append(product.getCategories().get(i).getName() + ",");
        }
        model.addAttribute("category", builder.toString().substring(0, builder.length() - 1));
        return "/productTypeModify";
    }

    @RequestMapping(value = "/productTypeModify", method = RequestMethod.POST)
    public String modifyProductType(String categories, String price, String zone_id, Long unit_id,
                                    String[] attributes, String[] values,
                                    @RequestParam("fileOfProduct") MultipartFile file, Model model) {
        String image = saveFile(file);
        service.modifyProductType(categories, price, zone_id, unit_id, attributes, values, productTypeRepository.findByName(nameProductTypeModification), image);
        return "redirect:/listProductType";
    }

    private String getRandomName(){
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 10; i++){
            char sym = (char)((Math.abs(random.nextInt()) % 26) +'a');
            s.append(sym);
        }
        s.append(".png");
        return s.toString();
    }

    private String saveFile(MultipartFile file){
        String image = "";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String rootPath = "src/main/webapp/resources/images";
                File dir = new File(rootPath);
                if (!dir.exists())
                    dir.mkdirs();

                String fileName = getRandomName();
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + fileName);
                image = fileName;
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

            } catch (Exception e) {
                image = "";
                e.printStackTrace();
            }
        }
        return image;
    }
}

