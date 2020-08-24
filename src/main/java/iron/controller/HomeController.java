package iron.controller;

import iron.bean.home;
import iron.bean.HomeAttr;
import iron.dao.HomeDAO;
import iron.dao.HomeAttrDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangxiaobo
 */
@Slf4j
@RestController
public class HomeController {
    @Autowired
    HomeAttrDAO homeAttrDAO;
    @Autowired
    HomeDAO homeDAO;


    @PostMapping("/back/homeimg/get")
    public List<HomeAttr> doHomeImgGet() {
        return homeAttrDAO.findAll();
    }

    @PostMapping("/back/homeimg/delete")
    public void doHomeImgDelete(Integer id) {
        homeAttrDAO.deleteById(id);
    }

    @PostMapping("/back/home/edit")
    public void homeEdit(home h) {
        homeDAO.save(h);
    }

    @PostMapping("/back/home/Carousel/delete")
    public void deleteHomeCarousel(Integer id) {
        homeAttrDAO.deleteById(id);
    }

    @PostMapping("/back/home/Carousel/get")
    public List<HomeAttr> getHomeCarousel() {
        return homeAttrDAO.findAll();
    }

    @PostMapping("/back/addHome")
    public void addHome(home home) {
        homeDAO.save(home);
    }

    @PostMapping("/back/deleteHome")
    public void deleteHome(Integer id) {
        homeDAO.deleteById(id);
    }

    @PostMapping("/back/editHomeAttr")
    public void editHomeAttr(HomeAttr homeAttr){
        System.out.println(homeAttr);
        homeAttrDAO.save(homeAttr);
    }
}
