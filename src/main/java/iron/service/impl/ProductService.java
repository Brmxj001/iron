package iron.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iron.bean.product;
import iron.dao.ProductDAO;

@Service
public class ProductService {
	@Autowired
	ProductDAO productDAO;
	
	public List<product> getProducts(){
		return productDAO.findAll();
	}

}
